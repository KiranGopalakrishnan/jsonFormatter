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
        Connection conn=a.connect("jdbc:mysql://sql9.freesqldatabase.com:3306/sql9165691?zeroDateTimeBehavior=convertToNull","sql9165691","zVA1NIi6Vy");
        ArrayList<String> params = new ArrayList();
        ResultSet data = a.runQuery("SELECT * FROM users",params);
        String response = a.createResponse("{userId,fname,lname,/c/timestamp:123756739,/a/data[{email}]},/a/secondDATA[{email}]",data);
        System.out.println(response);
    }
}
