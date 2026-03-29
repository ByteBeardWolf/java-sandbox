package banking;

import java.util.Scanner;

public class UserMenu {
    private int option;
    private int menuLevel;
    private boolean close;
    private Account userAccount;
    private static SimpleBankingSystemDB db;
    private final Scanner input = new Scanner(System.in);

    public UserMenu(SimpleBankingSystemDB db) {
        this.option = 0;
        this.menuLevel = 1;
        this.close = false;
        this.userAccount = null;
        UserMenu.db = db;
    }

    private void showMenu() {
        switch (menuLevel) {
            case 1 -> {
                System.out.println("1. Create an account");
                System.out.println("2. Log into account");
                System.out.println("0. Exit");
            }
            case 2 -> {
                System.out.println("1. Balance");
                System.out.println("2. Add income");
                System.out.println("3. Do transfer");
                System.out.println("4. Close account");
                System.out.println("5. Log out");
                System.out.println("0. Exit");
            }
        }
    }

    public void setOption(int option) {
        this.option = option;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    public void run() {
        showMenu();
        setOption(input.nextInt());

        switch (menuLevel*10+option) {
            case 10, 20 -> setClose(true);
            case 11 -> createAccount();
            case 12 -> logIn();
            case 21 -> showBalance();
            case 22 -> addIncome();
            case 23 -> doTransfer();
            case 24 -> closeAccount();
            case 25 -> logOut();
        }
    }

    private void closeAccount() {
        db.deleteAccount(userAccount);
        userAccount = null;
        menuLevel = 1;
        System.out.println("The account has been closed!");
    }

    private void doTransfer() {
        System.out.println("Enter card number");
        Long cardNumber = input.nextLong();

        if(!Account.isCorrectCardNumber(cardNumber)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        } else {
            Account transferAccount = db.getAccount(cardNumber);
            if (transferAccount != null) {
                if(transferAccount.getCardNumber() == userAccount.getCardNumber()) {
                    System.out.println("You can't transfer money to the same account!");
                } else {
                    System.out.println("Enter how much money you want to transfer:");
                    int money = input.nextInt();
                    if (money > userAccount.getBalance()) {
                        System.out.println("Not enough money!");
                    } else {
                        userAccount.setBalance(userAccount.getBalance() - money);
                        transferAccount.setBalance(transferAccount.getBalance() + money);
                        db.updateAccount(userAccount);
                        db.updateAccount(transferAccount);
                        System.out.println("Success!");
                    }
                }
            } else {
                System.out.println("Such a card does not exist.");
            }
        }

    }

    private void addIncome() {
        System.out.println("Enter income:");
        int income = input.nextInt();
        userAccount.setBalance(userAccount.getBalance() + income);
        db.updateAccount(userAccount);
        System.out.println("Income was added!");
    }

    private void logOut() {
        userAccount = null;
        menuLevel = 1;
        System.out.println("You have successfully logged out!");
    }

    private void showBalance() {
        System.out.println("Balance: " + userAccount.getBalance());
    }

    private void logIn() {
        System.out.println("Enter your card number:");
        long cardNumber = input.nextLong();
        System.out.println("Enter your PIN:");
        String pin = input.next();

        Account account = db.getAccount(cardNumber);
        if (account != null && account.getPin().equals(pin)) {
            userAccount = account;
            menuLevel = 2;
            System.out.println("You have successfully logged in!");
        } else {
            System.out.println("Wrong card number or PIN!");
        }
    }

    private void createAccount() {
        Account account = new Account();
        account.setCardNumber((long) (Math.random() * 1_000_000_000L + 400000000000000L));
        account.setPin(String.format("%04d", ((int) (Math.random() * 10000))));
        db.addAccount(account);
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(account.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(account.getPin());
    }
}
