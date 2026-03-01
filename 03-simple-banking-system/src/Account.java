package banking;

public class Account {
    private long cardNumber;
    private String pin;
    private int balance;

    public Account() {
        this.cardNumber = 0L;
        this.pin = "";
        this.balance = 0;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = addChecksum(cardNumber);
    }

    private long addChecksum(long cardNumber) {
        String cardNumberStr = String.valueOf(cardNumber);
        int sum = 0;
        for (int i = 0; i < 15; i++) {
            int digit = Character.getNumericValue(cardNumberStr.charAt(i));
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        int checksum = (10 - (sum % 10)) % 10;
        return cardNumber * 10 + checksum;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
