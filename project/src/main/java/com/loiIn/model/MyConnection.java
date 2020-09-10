package com.loiIn.model;

import com.loiIn.common.AppConfig;
import java.sql.*;

public class MyConnection {
    /**
     * Các bước để kết nối đến database:
     * Bước 1: kiểm tra driver jdbc với hàm driverTest
     * Bước 2: thực hiện kết nối với db bằng hàm connectDB
     * Bước 3: các hàm prepare và prepareUpdate dùng để lấy ra đối tượng dùng để thực thi các lệnh jquery trên db
     * Bước 4: đóng kết nối bằng hàm closeConnection
     */

    public static Connection connection = null;

    public void driverTest() throws ClassNotFoundException{
        try {
            Class.forName(AppConfig.DRIVER);
        }catch (ClassNotFoundException ex){
            throw new ClassNotFoundException("JDBC Driver not found " + ex.getMessage());
        }
    }

    public Connection connectDB() throws ClassNotFoundException, SQLException{
        if (connection == null){
            driverTest();
            try {
                connection = DriverManager.getConnection(AppConfig.URL_DATABASE, AppConfig.USERNAME, AppConfig.PASSWORD);
                if(connection != null) System.out.println("ConnectDB successfully");
            }catch (Exception e){
                throw new SQLException("Connect DB fail " + e.getMessage());
            }
        }
        return  connection;
    }

    /**
     * connection.prepareStatement: trả về đối tượng prepareStatement dùng để thực hiện queryString sql
     * ResultSet.TYPE_SCROLL_SENSITIVE: cho phép ResultSet chạy từ đầu bản ghi đến cuối
     * ResultSet.CONCUR_UPDATABLE: tạo ra một đối tượng ResultSet có thể được cập nhật
     */
    public PreparedStatement prepare(String sql){
        try {
            System.out.println(">>" + sql);
            return connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //Statement.RETURN_GENERATED_KEYS: trả về id của bản ghi vừa mới update thành công
    public PreparedStatement prepareUpdate(String sql){
        try {
            System.out.println(">> "+sql);
            return connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void closeConnection() throws SQLException{
        if(connection != null){
            connection.close();
            System.out.println("Connection is closed");
        }
    }


}
