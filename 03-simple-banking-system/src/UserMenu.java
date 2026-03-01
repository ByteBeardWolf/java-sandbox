package banking;

import java.util.Scanner;

public class UserMenu {
    private int option;
    private int menuLevel;
    private boolean close;
    private boolean loggedIn;
    private Account userAccount;
    private static SimpleBankingSystemDB db;
    private Scanner input = new Scanner(System.in);

    public UserMenu(SimpleBankingSystemDB db) {
        this.option = 0;
        this.menuLevel = 1;
        this.close = false;
        this.loggedIn = false;
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
                System.out.println("2. Log out");
                System.out.println("0. Exit");
            }
        }
    }

    public int getOption() {
        return option;
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
            case 22 -> logOut();
        }
    }

    private void logOut() {
        loggedIn = false;
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
            loggedIn = true;
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
