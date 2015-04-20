package Magicians;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patberg
 */
public class Holiday {
    private final String dbURL = "jdbc:derby://localhost:1527/MagicianData";
    final private String username = "mrp5379", password = "famfa50";
    private  Connection connection;
    private PreparedStatement insertCustomer;
    
    private String[] holidays = new String[10];

    Holiday()
    {
        final String query = 
            "SELECT * FROM APP.Holidays";
  //      Class.forName("org.apache.derby.jdbc.ClientDriver");
        try
        {
            Connection connection = DriverManager.getConnection(dbURL,username,password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            
            int count =0;
            while(resultSet.next())
            {
                holidays[count]= (String)resultSet.getObject("NAME");
                count++;
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
    }
    Holiday(String[] h)
    {
        holidays=h;
    }
    
    public void add(String h)
    {
        for(int x=0; x<holidays.length;x++)
        {
            if(holidays[x]==null)
            {
                holidays[x]=h;
                x=holidays.length;
            }
        }
    }
    public String printStatus(String holiday)
    {
        String h ="";
       
         final String bookingQuery = 
            "SELECT APP.MAGICIANS.NAME, APP.CUSTOMER.NAME,APP.BOOKING.TIMEOFBOOKING"
                 + "FROM APP.BOOKING"
                 + "INNER JOIN APP.MAGICIANS"
                 + "ON APP.MAGICIANS.MAGICIANID  = APP.BOOKING.MAGICIANID"
                 + "INNER JOIN APP.CUSTOMER"
                 + "ON APP.CUSTOMER.CUSTOMERID = APP.BOOKING.CUSTOMERID"
                 ;
        try
        {
            Connection connection = DriverManager.getConnection(dbURL,username,password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(bookingQuery);
            ResultSetMetaData metaData = resultSet.getMetaData();
            
            int count =0;
            
            h+="Magician\tCustomer\n";
            
            while(resultSet.next())
            {
                h+=(String)resultSet.getObject(1)+ "\t" + resultSet.getObject(2)+"\n";
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
        
        
        
        return h;
    }
    
    
}
