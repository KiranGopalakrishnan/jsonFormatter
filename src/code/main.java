package code;

import utilities.database;

import javax.xml.transform.Result;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vijay.getFollowing;

/**
 * Created by Kiran on 03-04-2017.
 */
public class main {
    public static void main(String[] args) throws SQLException {
        getFollowing g=new getFollowing();
       String res= g.getPosts();
        System.err.println(res);
    }
    
}
