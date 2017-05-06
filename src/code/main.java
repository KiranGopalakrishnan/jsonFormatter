package code;

import utilities.database;

import javax.xml.transform.Result;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Kiran on 03-04-2017.
 */
public class main {
    public static void main(String[] args) throws SQLException {
        database a = new database();
        Connection conn=a.connect("jdbc:oracle:thin:@144.217.163.57:1521:XE","project2","pro2pw");
        
        ArrayList<String> params = new ArrayList();
        ResultSet data = a.runQuery("SELECT * FROM PROJECT2.\"Users\"",params);
          System.out.println(data);
        String response = a.createResponse("{userID,/c/timestamp:123756739,/a/data[{email}]},/a/secondDATA[{email}]",data);
        System.out.println(response);
    }
}
