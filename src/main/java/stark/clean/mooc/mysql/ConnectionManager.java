package stark.clean.mooc.mysql;

import java.sql.*;

public class ConnectionManager
{
    /**
     * 修改为自己的数据库地址、端口号和名称
     */
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/mooc_data?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true";

    /**
     * 修改为自己的数据库用户名
     */
    public static final String USER_NAME = "DinoStark";

    /**
     * 修改为自己的数据库密码
     */
    public static final String PASSWORD = "non-feeling";

    private static final Connection CONNECTION;

    static
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            CONNECTION = DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.out.println("Error when connect to MySQL...");
            throw new RuntimeException(e);
        }
    }

    public static Statement createStatement() throws SQLException
    {
        return CONNECTION.createStatement();
    }

    public static PreparedStatement prepareStatement(String sql) throws SQLException
    {
        return CONNECTION.prepareStatement(sql);
    }
}
