package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleBankingSystemDB {
    private final SQLiteDataSource dataSource = new SQLiteDataSource();

    public SimpleBankingSystemDB(String url) {
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "number TEXT," +
                        "pin TEXT," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Account getAccount(long accountNumber) {
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try(ResultSet resultSet = statement.executeQuery("SELECT * FROM card WHERE number = " + accountNumber)) {
                    if (resultSet.next()) {
                        Account account = new Account();
                        account.setCardNumber(resultSet.getLong("number"));
                        account.setPin(resultSet.getString("pin"));
                        account.setBalance(resultSet.getInt("balance"));
                        return account;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addAccount(Account account) {
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("INSERT INTO card (number, pin, balance) VALUES(\"" +
                        account.getCardNumber() + "\",\"" + account.getPin() + "\"," + account.getBalance() + ")");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
