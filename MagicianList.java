package Magicians;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patberg
 */


public class MagicianList {
    private static String dbURL = "jdbc:derby://localhost:1527/MagicianData;";
    private final String username = "mrp5379", password = "famfa50";
    // jdbc Connection
    private static Connection connection = null;
    private static PreparedStatement addMagician = null;
    private String[] magicians = new String[10];
    private final DatabaseAccessors accessors= new DatabaseAccessors();
    
    MagicianList()
    {
        for(int x=0; x<magicians.length;x++)
            magicians[x]="";
    }
    MagicianList(String[] m)
    {
        magicians=m;
    }
    
    
    
    public int getID(String name)
    {
        int ID=0;
        final String query = 
            "SELECT *FROM MAGICIANS";
        try(Connection connection = DriverManager.getConnection(dbURL,username,password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query))
        {
            ResultSetMetaData metaData = resultSet.getMetaData();
            
            while(resultSet.next())
            {
                if(resultSet.getObject(1).equals(name))
                    ID =(Integer) resultSet.getObject(2);
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        return ID;
    }
    
    
    

}