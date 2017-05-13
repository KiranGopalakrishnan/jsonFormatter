/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonformat;

import utilities.*;

import javax.xml.transform.Result;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static oracle.net.aso.b.a;
import static oracle.net.aso.b.a;

/**
 *
 * @author 1691661
 */
public class Changepass {
           database a = new database();
       public String Changepass(String userID,String pass) throws SQLException{
                 database a = new database();
       try
       {
         Boolean conn=a.connect("jdbc:oracle:thin:@144.217.163.57:1521:XE","project2","pro2pw");
         ArrayList<String> params = new ArrayList();
         if(conn){
       
       
       params.add(userID);
        ResultSet data = a.runQuery("select * from PROJECT2.\"Users\" where \"userID\"=?",params);
         // System.out.println(data);
          data.last();
          int k=data.getRow();
        
      
        
        if(k>0){
           
          ArrayList<String> params1 = new ArrayList();
          params1.add(pass);
           params1.add(userID);
            
        ResultSet data1 = a.runQuery("UPDATE PROJECT2.\"Users\" SET \"password\" = ?  WHERE \"userID\" = ?",params1);      
        String response = a.createResponse("{/c/Staus:OK,/c/timestamp:" + a.timestamp+",/c/Message:Password changed Successfully}",data);
       
       
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