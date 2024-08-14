package Dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    public Connection connection;

    // DBContext constructor
    public DBContext() {
        try {
            // Change the username, password, and URL to connect to your own database
            String username = "sa";
            String password = "123";            
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QPS4.0";

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to connect to the database.");
        }
    }

    // test the connection
    public static void main(String[] args) {
        DBContext dbContext = new DBContext();
       if (dbContext.connection != null) {
           System.out.println("Database connection is established.");
       } else {
           System.out.println("Failed to establish database connection.");
        }
   }
}
