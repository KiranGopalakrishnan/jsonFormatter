/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonformat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utilities.database;

/**
 *
 * @author 1691618
 */
public class getNotification {
    public void getNotify(String UserID) throws SQLException{
        
        
        database a = new database();
       
         Boolean conn=a.connect("jdbc:oracle:thin:@144.217.163.57:1521:XE","project2","pro2pw");
         if(conn){
        ArrayList<String> params = new ArrayList();
       params.add(UserID);
        ResultSet data = a.runQuery("select * from PROJECT2.\"Users\" where \"userID\"=?",params);
          System.out.println(data);
          data.last();
          int k=data.getRow();
        
      
        
        if(k>0){
           
          ArrayList<String> params1 = new ArrayList();
         params1.add(UserID);  
            
         ResultSet data1 = a.runQuery("select * from PROJECT2.NOTIFICATIONS WHERE \"USERID\"=?",params1);
       String response = a.createResponse("{/c/Staus:OK,/c/timestamp:UTC(123756739),USERID,/a/data[{USERID&NOTTEXT}]}",data1);
        System.out.println(response);
       
        
        }
        else{
           String response = a.createResponse("{/c/Staus:Error,/c/timestamp:123756739}",data);
        System.out.println(response); 
        }
    }
        
        
        
        
  /*
        
        database a = new database();
        Connection conn=a.connect("jdbc:oracle:thin:@144.217.163.57:1521:XE","project2","pro2pw");
        
        ArrayList<String> params = new ArrayList();
        params.add("1");
        //ResultSet data = a.runQuery("SELECT * FROM PROJECT2.\"NOTIFICATIONS\" WHERE USERID = ?",params);
        ResultSet data = a.runQuery("SELECT * FROM PROJECT2.\"Users\" WHERE userID = ?",params);
          //System.out.println(data);
        String response = a.createResponse("{/c/timestamp:UTC(123756739),USERID,/a/data[{USERID&NOTTEXT}]}",data);
        System.out.println(response);
        
        */      
        
    }
    
}
