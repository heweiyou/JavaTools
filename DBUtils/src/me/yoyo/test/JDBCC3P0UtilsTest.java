package me.yoyo.test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import me.yoyo.jdbc.JDBCC3P0Utils;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCC3P0UtilsTest {
	
	@Test
	public void C3P0Test() throws PropertyVetoException, SQLException
	{
		String driverClassName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/mydb";
		String username = "root";
		String password = "root";
		
		//创建连接池对象
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		//配置连接池四大参数
		dataSource.setDriverClass(driverClassName);//PropertyVetoException
		dataSource.setJdbcUrl(url);
		dataSource.setUser(username);
		dataSource.setPassword(password);
		
		//对连接池进行配置
		dataSource.setAcquireIncrement(5);//增量
		dataSource.setInitialPoolSize(10);//初始连接数
		dataSource.setMinPoolSize(5);//最少连接数
		dataSource.setMaxPoolSize(20);//最大连接数
		dataSource.setIdleConnectionTestPeriod(0);//连接最长时间
		
		Connection con = dataSource.getConnection();
		System.out.println(con);
		con.close();
	}
	
	@Test
	public void utilsTest() throws SQLException, PropertyVetoException
	{
		/*String driverClassName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/mydb";
		String username = "root";
		String password = "root";
		JDBCC3P0Utils.init(driverClassName, url, username, password);*/
		Connection con = JDBCC3P0Utils.getConnection();
		JDBCC3P0Utils.beginTransaction();
		System.out.println(con+" is operating database...");
		JDBCC3P0Utils.commitTransaction();
		System.out.println(con);
	}
}
