package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

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
            String query = "SELECT * FROM card WHERE number = ?";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setLong(1, accountNumber);
                try(ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Account account = new Account();
                        account.setCardNumberDirect(resultSet.getLong("number"));
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
            String insert = "INSERT INTO card (number, pin, balance) VALUES (?, ?, ?)";
            try (PreparedStatement statement = con.prepareStatement(insert)) {
                statement.setLong(1, account.getCardNumber());
                statement.setString(2, account.getPin());
                statement.setInt(3, account.getBalance());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAccount(Account userAccount) {
        try (Connection con = dataSource.getConnection()) {
            String update = "UPDATE card SET balance = ? WHERE number = ?";
            try (PreparedStatement statement = con.prepareStatement(update)) {
                statement.setInt(1, userAccount.getBalance());
                statement.setLong(2, userAccount.getCardNumber());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(Account userAccount) {
        try (Connection con = dataSource.getConnection()) {
            String delete = "DELETE FROM card WHERE number = ?";
            try (PreparedStatement statement = con.prepareStatement(delete)) {
                statement.setLong(1, userAccount.getCardNumber());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
