package me.yoyo.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCOriginUtils {
	/**
	 * jdbc四大配置参数： driverClassName: com.mysql.jdbc.Driver url:
	 * jdbc:mysql://localhost:3306/mydb username: root password: root
	 * 
	 * 
	 * ClassNotFoundException: >没有导入驱动包
	 * 
	 * SQLException: >检查三个参数:url、username、password >检查是否开启了mysql服务器
	 */
	private static Properties props = null;
	private static Connection con = null;
	private static String driverClassName = null;
	private static String url = null;
	private static String username = null;
	private static String password = null;

	static {

		
		
	}

	public static void init()
	{
		try {
			InputStream in = JDBCOriginUtils.class.getClassLoader()
					.getResourceAsStream("dbconfig.properties");
			props = new Properties();
			props.load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		driverClassName = props.getProperty("driverClassName");
		url = props.getProperty("url");
		username = props.getProperty("username");
		password = props.getProperty("password");
	}
	
	/**
	 * 配置四大参数
	 * @param _driverClassName
	 * @param _url
	 * @param _username
	 * @param _password
	 */
	public static void init(String _driverClassName,String _url,String _username,String _password)
	{
		driverClassName = _driverClassName;
		url = _url;
		username = _username;
		password = _password;
		
	}
	
	/**
	 * 加载JDBC驱动类
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static boolean loadDriverClass() throws ClassNotFoundException
	{
		if(driverClassName != null)
		{
			Class.forName(driverClassName);
			return true;
		}else
		{
			return false;
		}
	}
	/**
	 * 返回连接对象
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		if(driverClassName == null || url ==  null || username == null || password == null)
		{
			return null;
		}else
		{
			return con = DriverManager.getConnection(url,
					username, password);
		}
		
	}
	
	/**
	 * 释放连接对象
	 * @throws SQLException
	 */
	public static void release() throws SQLException
	{
		if(con != null) con.close();
	}
}
