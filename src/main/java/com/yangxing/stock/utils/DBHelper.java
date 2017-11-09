package com.yangxing.stock.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {

	public static final String url = "jdbc:mysql://127.0.0.1/yangxing";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "yangxing";

	public PreparedStatement pst = null;

	// 构造器
	public DBHelper() {
		try {

			Class.forName(name);// 指定连接类型

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 获取连接
	public Connection getConnection() throws SQLException {

		return DriverManager.getConnection(url, user, password);
	}
}
