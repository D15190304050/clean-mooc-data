package stark.clean.mooc.io;

import java.sql.*;

public class MysqlConnectionDemo
{
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/spike?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true"; // 修改为自己的数据库地址、端口号和名称
        String username = "DinoStark"; // 修改为自己的数据库用户名
        String password = "non-feeling"; // 修改为自己的数据库密码
        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT VERSION()");
        resultSet.next();
        String version = resultSet.getString(1);
        System.out.println(version);

        resultSet.close();
    }
}
