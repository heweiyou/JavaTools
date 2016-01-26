package me.yoyo.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import me.yoyo.jdbc.JDBCOriginUtils;

import org.junit.Test;

public class JDBCOriginUtilsTest {
	
	@Test
	public void originTest() throws ClassNotFoundException, SQLException
	{
		//准备四大参数
		String driverClassName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/mydb";
		String username = "root";
		String password = "root";
		
		//加载驱动类
		Class.forName(driverClassName);//ClassNotFoundException
		
		//得到Connection
		Connection con = DriverManager.getConnection(url, username, password);//SQLException
		
		System.out.println(con);
		con.close();
	}
	
	@Test
	public void utilsTest() throws SQLException, ClassNotFoundException
	{
	/*
		//准备四大参数
		String driverClassName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/mydb";
		String username = "root";
		String password = "root";
		JDBCOriginUtils.init(driverClassName, url, username, password);*/
		JDBCOriginUtils.init();
		JDBCOriginUtils.loadDriverClass();
		Connection con = JDBCOriginUtils.getConnection();
		System.out.println(con);
		JDBCOriginUtils.release();
		
		
	}

}
