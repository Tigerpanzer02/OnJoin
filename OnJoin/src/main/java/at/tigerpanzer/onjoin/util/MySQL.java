package at.tigerpanzer.onjoin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {
    public static String username;
    public static String password;
    public static String database;
    public static String host;
    public static String port;
    private static Connection con;

    public static void connect() {
        if(!isConnected()) {
            try {
                MySQL.con = DriverManager.getConnection("jdbc:mysql://" + MySQL.host + ":" + MySQL.port + "/" + MySQL.database + "?autoReconnect=true", MySQL.username, MySQL.password);
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if(isConnected()) {
            try {
                MySQL.con.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void reconnect() {
        disconnect();
        connect();
    }

    private static boolean isConnected() {
        return MySQL.con != null;
    }

    public static void createTable() {
        if(isConnected()) {
            try {
                MySQL.con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS FirstJoin (id int(30) NOT NULL AUTO_INCREMENT PRIMARY KEY, PlayerName VARCHAR(100), UUID VARCHAR(100), FirstJoin VARCHAR(10), Date VARCHAR(100))");
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public static void updateprepare(final String qry) {
        if(isConnected()) {
            try {
                PreparedStatement ps = con.prepareStatement(qry);
                ps.executeUpdate();
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void update(final String qry) {
        if(isConnected()) {
            try {
                MySQL.con.createStatement().executeUpdate(qry);
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static ResultSet getResultSet(final String qry) {
        if(isConnected()) {
            try {
                return MySQL.con.createStatement().executeQuery(qry);
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

}
