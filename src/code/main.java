package code;

import Functions.GetPosts;
import utilities.database;
import jsonformat.*;

import javax.xml.transform.Result;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Functions.getFollowing;

/**
 * Created by Kiran on 03-04-2017.
 */
public class main {
    public static void main(String[] args) throws SQLException {
        //GetPosts g=new GetPosts();
      // String res= g.getPosts();
        //System.out.println(res);
     Changepass cp=new Changepass();
        String res4=cp.Changepass("1", "loginko");
        System.out.println(res4);
        
    }
    
}
