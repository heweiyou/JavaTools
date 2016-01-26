package me.yoyo.jdbc;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCC3P0Utils {
	//连接池
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	//事务专用连接
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	/**
	 * 配置四大参数，若有配置文件可不执行此步
	 * @param driverClass
	 * @param JdbcUrl
	 * @param user
	 * @param password
	 * @throws PropertyVetoException
	 */
	public static void init(String driverClass,String JdbcUrl,String user,String password) throws PropertyVetoException
	{
		dataSource.setDriverClass(driverClass);//PropertyVetoException
		dataSource.setJdbcUrl(JdbcUrl);
		dataSource.setUser(user);
		dataSource.setPassword(password);
	}
	/**
	 * 使用连接池返回一个连接对象
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException
	{
		Connection con = tl.get();
		
		if(con != null) return con;
		else return dataSource.getConnection();
	}
	
	/**
	 * 返回连接池对象
	 * @return
	 */
	public static DataSource getDataSource()
	{
		return dataSource;
	}
	
	
	/**
	 * 开启事务
	 * 1.获取一个Connection,设置为手动提交
	 * 2.把该Connection给dao用
	 * 3.commitTransaction或者rollbackTransaction可以获取到
	 * @throws SQLException
	 */
	public static void beginTransaction() throws SQLException
	{
		//判断事务是否已经开启
		Connection con = tl.get();
		if(con != null) throw new SQLException("事务已开启，请勿重启！");
		
		//获取连接对象
		con = getConnection();
		
		//设置连接对象为手动提交
		con.setAutoCommit(false);
		
		//保存当前线程连接
		tl.set(con);	
	}
	
	/**
	 * 提交事务
	 * 1.获取beginTransaction提供的Connection
	 * 2.调用Connection的commit方法
	 * @throws SQLException
	 */
	public static void commitTransaction() throws SQLException
	{
		//获取当前线程专用连接
		Connection con = tl.get();
		if(con == null) throw new SQLException("事务未开启，禁止提交！");
		//直接使用Connection::commit()提交
		con.commit();
		//关闭连接
		con.close();
		//从tl中移除专用连接
		tl.remove();
	}
	
	/**
	 * 回滚事务
	 * 1.获取beginTransaction提供的Connection
	 * 2.调用Connection::rollback
	 * @throws SQLException
	 */
	public static void rollbackTransaction() throws SQLException
	{
		//获取当前线程专用连接
		Connection con = tl.get();
		if(con == null) throw new SQLException("事务未开启，禁止回滚");
		
		//直接使用Connection::rollback()回滚
		con.rollback();
		//关闭连接
		con.close();
		//从tl中移除专用连接
		tl.remove();
	}
	
	
	/**
	 * 释放连接
	 * @param connection
	 * @throws SQLException
	 */
	public static void releaseConnection(Connection connection) throws SQLException
	{
		Connection con = tl.get();
		//判断是否是事务连接，如果不是事务连接就要关闭(事务连接在提交或回滚是即设置关闭)
		if(con == null || con != connection) connection.close();
		
	}
}
