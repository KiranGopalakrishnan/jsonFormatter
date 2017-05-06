/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utilities.database;

/**
 *
 * @author vkalangi
 */
public class getFollowing {
    public String getFollowing() throws SQLException
    {
        database a = new database();
        try
        {
         boolean conn=a.connect("jdbc:oracle:thin:@144.217.163.57:1521:XE","project2","pro2pw");
         if(conn!=false){
        ArrayList<String> params = new ArrayList();
        params.add("1");
         ResultSet data = a.runQuery("SELECT * FROM PROJECT2.\"FOLLOWING\" WHERE USERID=?",params);
        if(data.next())
        {
          //System.out.println(data);
        String response = a.createResponse("{USERID,/c/timestamp:123756739,/a/data[{FOLLOWERID}]}",data);
        return response;  
       }
        else
        {
           database a1 = new database();
           String wrong= a1.JsonWrong();
           return wrong;
        
       }
         }
         else
         {
         database a1 = new database();
           String error= a1.JsonError();
           return error;
         }
       }
        catch(SQLException e)
            
       {
           database a1 = new database();
           String error= a1.JsonError();
           return error;
            
       }
    }
    
}

