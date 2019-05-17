/*
 *  OnJoin - Your Server Join Plugin
 *          With this plugin, joins are
 *          unique on your server​
 *
 *  Maintained by Tigerpanzer_02
 */

package at.tigerpanzer.onjoin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
    private String Host;
    private String Database;
    private String Username;
    private String Password;
    private Integer Port;
    private Connection connection;
    private String messageprefix = "[OnJoin | MySQL] ";

    /**
     * @param host     The ip or domain of the MySQL Server
     * @param database The database you would like to use
     * @param username The login username
     * @param password The login password
     */
    public MySQL(String host, String database, String username, String password, Integer port) {
        Host = host;
        Database = database;
        Username = username;
        Password = password;
        Port = port;
        Connect();
    }

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            Utils.debugmessage(messageprefix + "Error 01");
            e1.printStackTrace();
        }
        String url = "jdbc:mysql://" + Host + ":" + Port + "/" + Database + "?verifyServerCertificate=false&useSSL=true&autoReConnect=true";
        // 		String url = "jdbc:mysql://" + Host + ":3306/" + Database + "?autoReConnect=true";

        try {
            connection = DriverManager.getConnection(url, Username, Password);
            createTable();
            Utils.debugmessage(messageprefix + "MySQL Connection successful");
        } catch (SQLException e2) {
            Utils.debugmessage(messageprefix + "Error 02");
            e2.printStackTrace();
        }
    }

    public void Disconnect() {
        try {
            if (!connection.isClosed() && connection != null) {
                connection.close();
                Utils.debugmessage(messageprefix + "MySQL Connection disconnect!");
            } else {
                Utils.debugmessage(messageprefix + "The Connection is already not available!");
            }
        } catch (SQLException e3) {
            Utils.debugmessage(messageprefix + "Error 03");
            e3.printStackTrace();
        }
    }

    public boolean isConnected() {
        try {
            if (connection.isClosed()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e2) {
            Utils.debugmessage(messageprefix + "Error 04");
            e2.printStackTrace();
        }
        return false;
    }

    /**
     * @param command The query you would like to execute
     */
    public ResultSet getResultSet(String command) {
        try {
            if (connection.isClosed()) {
                Connect();
            }

            Statement st = connection.createStatement();
            st.executeQuery(command);
            ResultSet rs = st.getResultSet();
            return rs;

        } catch (SQLException e4) {
            Utils.debugmessage(messageprefix + "Error 05");
            e4.printStackTrace();
        }
        return null;
    }

    /**
     * @param command The query you would like to execute
     */
    public void ExecuteCommand(String command) {
        try {
            if (connection.isClosed()) {
                Connect();
            }
            Statement st = connection.createStatement();
            st.executeUpdate(command);
        } catch (SQLException e4) {
            Utils.debugmessage(messageprefix + "Error 06");
            e4.printStackTrace();
        }

    }


    public void createTable() {
        if (isConnected()) {
            try {
                connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS FirstJoin (id int(30) NOT NULL AUTO_INCREMENT PRIMARY KEY, PlayerName VARCHAR(100), UUID VARCHAR(100), FirstJoin VARCHAR(10), Date VARCHAR(100))");
            } catch (SQLException e) {
                Utils.debugmessage(messageprefix + "Error 07");
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void Reconnect() {
        Disconnect();
        Connect();
    }
}
