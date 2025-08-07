package com.example.zoo.database; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


@Component
public class AivenConnection {

    @Autowired
    private MyDBConnection mydb;

    public void testQueryUserTable() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = mydb.getOnlyConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM user"); // Cẩn thận: bảng này phải tồn tại trong DB

            System.out.println("Danh sách user từ database:");
            while (rs.next()) {
                String userID = rs.getString("userId");
                String username = rs.getString("username");
                String address = rs.getString("address");
                System.out.println(userID + " | " + username + " | " + address);
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi truy vấn Aiven DB: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                System.out.println("Lỗi khi đóng kết nối: " + ex.getMessage());
            }
        }
    }
}
