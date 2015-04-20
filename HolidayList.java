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
public class HolidayList {
    private final String dbURL = "jdbc:derby://localhost:1527/MagicianData";
    final private String username = "mrp5379", password = "famfa50";
    private  Connection connection;
    private PreparedStatement insertCustomer, printEntries;
    
    private String[] holidays = new String[10];

    HolidayList()
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
    HolidayList(String[] h)
    {
        holidays=h;
    }
    public int getHolidayID (String name)
     {
        int ID=0;
        final String query = 
            "SELECT *FROM APP.HOLIDAYS";
        try(Connection connection = DriverManager.getConnection(dbURL,username,password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query))
        {
            ResultSetMetaData metaData = resultSet.getMetaData();
            
            while(resultSet.next())
            {
                if(resultSet.getString("NAME").equals(name))
                    ID =resultSet.getInt("HOLIDAYID");
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        return ID;      
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
    public String getHolidayName(int id)
    {
        String name="";
        final String query = 
            "SELECT *FROM APP.HOLIDAYS";
        try(Connection connection = DriverManager.getConnection(dbURL,username,password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query))
        {
            ResultSetMetaData metaData = resultSet.getMetaData();
            
            while(resultSet.next())
            {
                if(resultSet.getInt("HOLIDAYID")==id)
                    name = resultSet.getString("NAME");
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        return name;
    }
    

    
    
}
