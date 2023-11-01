package com.lpb.service.sql.configuration;
import java.sql.Connection;
import java.sql.DriverManager;

public class DynamicDB {

    /**
     * CONNECTION DYNAMIC DATABASE NOT POOL
     *
     * @param url
     * @param userName
     * @param passWord
     * @return
     */
    public static Connection openConnectionDB(String url, String userName, String passWord) {
        Connection conn = null;
        try {
            // Utilities.writeLog("Connection Dynamic DB");
            DriverManager.setLoginTimeout(10);
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(url, userName, passWord);
            // Utilities.writeLog("Connection Dynamic Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            // Utilities.writeLog("Exception DynamicDB: " + ex.getMessage());
        }
        return conn;
    }

    public static Connection openConnectionMYSQLDB(String url, String userName, String passWord) {
        Connection conn = null;
        try {
            // Utilities.writeLog("Connection Dynamic DB");
            DriverManager.setLoginTimeout(10);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, passWord);
            // Utilities.writeLog("Connection Dynamic Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            // Utilities.writeLog("Exception DynamicDB: " + ex.getMessage());
        }
        return conn;
    }
}
