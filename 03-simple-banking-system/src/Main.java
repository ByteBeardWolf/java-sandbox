package banking;

public class Main {
    public static void main(String[] args) {
        //default database file name
        String dbFileName = "jdbc:sqlite:sbs.s3db";

        // Parse command line arguments for -fileName flag
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-fileName") && i + 1 < args.length) {
                dbFileName = "jdbc:sqlite:" + args[i + 1];
                break;
            }
        }

        // dbFileName is now set based on command line arguments or defaults to sbs.s3db
        SimpleBankingSystemDB dbSBS = new SimpleBankingSystemDB(dbFileName);
        UserMenu userMenu = new UserMenu(dbSBS);
        while (!userMenu.isClose()) {
            userMenu.run();
        }
    }
}