package common;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.APPConfig.HOST;

/**
 * Created by cxy on 2017/6/8.
 */
public class SQLHelper {
//    private Connection conn = null;
//    private PreparedStatement ps = null;
//    private ResultSet rs = null;
//    private CallableStatement callableStatement = null;
//    private String URL;

    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        String sql = "Select *  from carModel_b c where unidentified is null and yearPattern='2003款' and gases='2.0T' and seriesId=471 and brandId=33 order by modelName order by modelName ";
        String sql = "Select top 10 *  from carModel_b c where unidentified is null and gases='2.0T' and seriesId=471 and brandId=33 order by modelName ";
        SQLHelper sqlHelper = new SQLHelper(false);
        System.err.println(sqlHelper.executeQueryList(APPConfig.COMPANYID, sql, null));
    }

    private final String mUrl, mUser, mPwd;

//    public SQLHelper() {
//        this(false);
//    }

    public SQLHelper(HttpServletRequest req) {
        this(!(req.getRequestURL().toString().startsWith("http://" + HOST) || req.getRequestURL().toString().startsWith("https://" + HOST)));
    }

    public SQLHelper(boolean debug) {
        if (debug) {
            mUrl = APPConfig.URL_LOCAL;
            mUser = APPConfig.USERNAME_DEBUG;
            mPwd = APPConfig.PASSWORD_DEBUG;
        } else {
            mUrl = APPConfig.URL_BZ5155;
            mUser = APPConfig.USERNAME;
            mPwd = APPConfig.PASSWORD;
        }
    }



    private Connection getConnection(String CompanyId) throws SQLException {
        String url = mUrl.replace("[CompanyId]", CompanyId);
        return DriverManager.getConnection(url, mUser, mPwd);
    }

    public int ExecuteNonQuery(String CompanyId, String sql, String[] parameters) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection(CompanyId);
            ps = conn.prepareStatement(sql);
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            return ps.executeUpdate();
        } catch (Throwable e) {
            //
        } finally {
            close(ps);
            close(conn);
        }
        return -1;
    }

    /**
     * 执行插入返回当前插入行Id
     *
     * @param CompanyId  企业ID
     * @param sql        插入语句
     * @param parameters 参数
     * @return 返回结果Id
     */
    public Long ExecuteInsertGetId(String CompanyId, String sql, String[] parameters) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Long id = 0L;
        try {
            conn = getConnection(CompanyId);
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs == null) {
                id = 0L;
            } else if (rs.next()) {
                id = rs.getLong(1);
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return -1L;
    }

    /**
     * 执行带参数的查询语句
     *
     * @param CompanyId  企业ID
     * @param sql        查询语句
     * @param parameters 参数
     * @return List<Map<String,Objecy>> 结果集合
     */
    public <T> List<T> executeQueryList(String CompanyId, String sql, String[] parameters, Class<T> cc) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection(CompanyId);
            ps = conn.prepareStatement(sql);
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            rs = ps.executeQuery();
            if (rs == null) {
                return null;
            }
            return this.populate(rs, cc);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return new ArrayList<T>();
    }

    /**
     * 执行带参数的查询语句
     *
     * @param CompanyId  企业ID
     * @param sql        查询语句
     * @param parameters 参数
     * @return List<Map<String,Objecy>> 结果集合
     */
    public List<Map<String, Object>> executeQueryList(String CompanyId, String sql, String[] parameters) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection(CompanyId);
            ps = conn.prepareStatement(sql);
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            rs = ps.executeQuery();
            if (rs == null) {
                return null;
            }
            return toList(rs, ps.getMetaData());
        } catch (Exception e) {
            System.err.println(sql);
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return new ArrayList<Map<String, Object>>();
    }

    /**
     * 执行带参数的存储过程
     *
     * @param CompanyId  企业ID
     * @param procedure  存储过程 Sql
     * @param parameters 参数集
     * @return
     */
    public List<Map<String, Object>> executeQueryProcedure(String CompanyId, String procedure, String[] parameters) {
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement callableStatement = null;
        rs = null;
        try {
            conn = getConnection(CompanyId);
            callableStatement = conn.prepareCall(procedure);
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    callableStatement.setString(i + 1, parameters[i]);
                }
            }
            rs = callableStatement.executeQuery();
            if (rs == null) {
                return null;
            }
            return toList(rs, callableStatement.getMetaData());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(callableStatement);
            close(conn);
        }
        return new ArrayList<Map<String, Object>>();
    }


    private void close(AutoCloseable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Throwable e) {
            }
        }
    }

    private List<Map<String, Object>> toList(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        final int columnCount = rsmd.getColumnCount();
        Map<String, Object> data = null;
        // 循环结果集
        while (rs.next()) {
            data = new HashMap<>();
            // 每循环一条将列名和列值存入Map
            for (int i = 1; i <= columnCount; i++) {
                data.put(rsmd.getColumnLabel(i), rs.getObject(rsmd.getColumnLabel(i)));
            }
            // 将整条数据的Map存入到List中
            datas.add(data);
        }
        return datas;
    }

    /**
     * 将结果集转换成实体对象集合
     *
     * @param rs 结果集
     * @param cc 实体对象映射类
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private <T> List<T> populate(ResultSet rs, Class<T> cc) throws SQLException, InstantiationException, IllegalAccessException {
        //结果集 中列的名称和类型的信息
        ResultSetMetaData rsm = rs.getMetaData();
        int colNumber = rsm.getColumnCount();
        List<T> list = new ArrayList<>();
        Field[] fields = cc.getDeclaredFields();
        String str = "";
        //遍历每条记录
        while (rs.next()) {
            //实例化对象
            T obj = cc.newInstance();
            //取出每一个字段进行赋值
            for (int i = 1; i <= colNumber; i++) {
                Object value = rs.getObject(i);
                //匹配实体类中对应的属性
                for (int j = 0; j < fields.length; j++) {
                    Field f = fields[j];
                    str = rsm.getColumnName(i);
                    if (f.getName().equals(str)) {
                        boolean flag = f.isAccessible();
                        f.setAccessible(true);
                        if (String.class.equals(f.getType()) && !(value instanceof String)) {
                            if (value == null) {
                                f.set(obj, null);
                            } else {
                                try {
                                    f.set(obj, String.valueOf(value));
                                } catch (Exception e) {
                                    throw new IllegalArgumentException(f.getType() + " no set " + (
                                            value == null ? null : value.getClass()), e);
                                }
                            }
                        } else {
                            f.set(obj, value);
                        }
                        f.setAccessible(flag);
                        break;
                    }
                }
            }
            list.add(obj);
        }
        return list;
    }

    /**
     * 返回数据集第一行第一列
     *
     * @param CompanyId  企业ID
     * @param sql        查询语句
     * @param parameters 参数
     * @return Object 结果
     */
    public Object ExecScalar(String CompanyId, String sql, String[] parameters) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection(CompanyId);
            ps = conn.prepareStatement(sql);
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    ps.setString(i + 1, parameters[i]);
                }
            }
            rs = ps.executeQuery();
            if (rs == null) {
                return null;
            }
            Object obj = null;
            if (rs.next()) {
                obj = rs.getObject(1);
            }
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return null;
    }

    //查询多条sql语句
    public void ExecuteSqlTran(String CompanyId,List<String> SQLStringlist){
        Connection conn = null;
        Statement  stmt =null;
        try {
            conn = getConnection(CompanyId);//连接数据库
            conn.setAutoCommit(false);
            stmt=conn.createStatement();
            for (int i = 0; i < SQLStringlist.size(); i++) {
                String strsql = SQLStringlist.get(i).toString();
                if (strsql.trim().length() > 1)
                {
                    stmt.addBatch(strsql);  // 若不出现异常，则继续执行到try语句完，否则跳转到catch语句中
                }
            }
            stmt.executeBatch(); // 4) SQLException将一批命令提交给数据库来执行，如果全部命令执行成功，则返回更新计数组成的数组。
            conn.commit();  //进行手动提交（commit）
            conn.setAutoCommit(true); //3,提交完成后回复现场将Auto commit,还原为true,
        } catch (SQLException e) {
            try
            {
                conn.rollback();
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return;
        } finally {
            close(conn);
        }
       /* conn.Open();
        SqlCommand cmd = new SqlCommand();
        cmd.Connection = conn;
        SqlTransaction tx = conn.BeginTransaction();
        cmd.Transaction = tx;
        try
        {
            for (int n = 0; n < SQLStringlist.Count; n++)
            {
                string strsql = SQLStringlist[n].ToString();
                if (strsql.Trim().Length > 1)
                {
                    cmd.CommandText = strsql;
                    cmd.ExecuteNonQuery();
                }
            }
            tx.Commit();
        }
        catch (System.Data.SqlClient.SqlException E)
        {
            tx.Rollback();
            throw new Exception(E.Message);
        }*/
    }


}
