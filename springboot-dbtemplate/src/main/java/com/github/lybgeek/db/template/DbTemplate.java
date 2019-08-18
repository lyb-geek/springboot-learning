package com.github.lybgeek.db.template;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

@Slf4j
@Getter
@Setter
public class DbTemplate {


  private QueryRunner run;
  private DataSource ds;

  // 只放进行事务的 Connection
  private static ThreadLocal<Connection> conn = new ThreadLocal<>();

  public DbTemplate() {

  }


  public DbTemplate(QueryRunner run, DataSource ds) {

    this.run = run;
    this.ds = ds;
  }



  /**
   * 通过 DruidDataSource 得到 Connection
   *
   * @return Connection 对象
   */
  public  Connection getConnection() throws SQLException {
    // 得到 ThreadLocal 中的 Connection
    Connection con = conn.get();
    //如果开启了事务，则con不为空，应该直接返回con
    if (null == con || con.isClosed()) {
      con = ds.getConnection();
      conn.set(con);
    }
    return con;
  }

  // ---------------------------对事物操作的封装-------------------------------------

  /**
   * 开启事务
   */
  public  void beginTransaction() throws SQLException {
    //得到ThreadLocal中的connection
    Connection con = conn.get();
    //设置事务提交为手动
    con.setAutoCommit(false);
    //把当前开启的事务放入ThreadLocal中
    conn.set(con);
  }

  /**
   * 提交事务
   */
  public  void commitTransaction() throws SQLException {
    //得到ThreadLocal中的connection
    Connection con = conn.get();
    //判断con是否为空，如果为空，则说明没有开启事务
    if (con == null) {
      throw new SQLException("没有开启事务,不能提交事务");
    }
    // 如果 con 不为空,提交事务
    con.commit();
    // 事务提交后，关闭连接
    con.close();
    // ThreadLocal中移出连接
    conn.remove();
  }

  /**
   * 回滚事务
   */
  public  void rollbackTransaction() {

    try {
      //得到 ThreadLocal 中的 connection
      Connection con = conn.get();
      // 判断con是否为空，如果为空，则说明没有开启事务，不能回滚事务
      if (con == null) {
        throw new SQLException("没有开启事务,不能回滚事务");
      }
      // 事务回滚
      con.rollback();
      // 关闭连接
      con.close();
      // 将连接移除 ThreadLocal
      conn.remove();
    } catch (SQLException e) {
      log.error("ERROR_002_com.hq.db_回滚事务失败_line134..." + e.getMessage());
    }
  }

  /**
   * 关闭事务
   */
  public  void releaseConnection(Connection connection) throws SQLException {
    //得到ThreadLocal中的connection
    Connection con = conn.get();
    //如果参数连接与当前事务连接不相等，则说明参数连接不是事务连接，可以关闭，否则交由事务关闭
    if (connection != null && con != connection) {
      //如果连接没有被关闭，关闭之
      if (!connection.isClosed()) {
        connection.close();
      }
    }
  }

  /**
   * 关闭 DruidDataSource
   */
  public  void closeDataSource() {

    if (null != ds) {
      try {
        ds.getConnection().close();
        conn.remove();
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
  }

  // -----------------------重写 QueryRunner 中的方法--------------------------
  public  int[] batch(String sql, Object[][] params) throws SQLException {

    Connection con = getConnection();
    int[] result = run.batch(sql, params);
    releaseConnection(con);
    return result;
  }

  public  <T> T query(String sql, ResultSetHandler<T> handler, Object... params)
      throws SQLException {

    Connection con = getConnection();
    T result = run.query(con, sql, handler, params);
    releaseConnection(con);
    return result;
  }

  public  <T> T query(String sql, ResultSetHandler<T> handler) throws SQLException {

    Connection con = getConnection();
    T result = run.query(con, sql, handler);
    releaseConnection(con);
    return result;

  }

  public  int update(String sql, Object... params) throws SQLException {

    Connection con = getConnection();
    int result = run.update(con, sql, params);
    releaseConnection(con);
    return result;
  }

  public  int update(String sql, Object params) throws SQLException {

    Connection con = getConnection();
    int result = run.update(con, sql, params);
    releaseConnection(con);
    return result;
  }

  public  int update(String sql) throws SQLException {

    Connection con = getConnection();
    int result = run.update(con, sql);
    releaseConnection(con);
    return result;
  }

  /**
   * 将Map中的值分解为值列表与（键=？）列表
   *
   * @param flist (eg.name=?,age=?)
   * @param values 值的列表
   * @param map 待解析的 TreeMap
   */
  public  void parseFildAndQuery(StringBuilder flist, List<Object> values,
      TreeMap<String, Object> map) {

    if (null != map && null != map.keySet() && map.keySet().size() > 0) {
      Iterator<String> iterator = map.keySet().iterator();
      while (iterator.hasNext()) {
        String key = iterator.next();
        flist.append(key + "=?,");
        values.add(map.get(key));
      }
    }

    if (flist.length() > 0) {
      flist.delete(flist.length() - 1, flist.length());
    }
  }

  /**
   * 将 map 中的数据解析到  flist,qlist ,values中
   *
   * @param flist 字段名+"," (eg."name,age,sex")
   * @param qlist ？+","   (eg."?,?,?")
   * @param values 字段对应的值
   */
  public  void parseFildAndQuery(StringBuilder flist, StringBuilder qlist,
      List<Object> values, TreeMap<String, Object> map) {

    if (null != map && null != map.keySet() && map.keySet().size() > 0) {
      Iterator<String> iterator = map.keySet().iterator();
      while (iterator.hasNext()) {
        String key = iterator.next();
        flist.append(key + ",");
        qlist.append("?,");
        values.add(map.get(key));
      }
    }
    if (flist.length() > 0) {
      flist.delete(flist.length() - 1, flist.length());
      qlist.delete(qlist.length() - 1, qlist.length());
    }
  }

}
