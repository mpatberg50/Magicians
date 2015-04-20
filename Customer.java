package Magicians;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
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
public class Customer {
    private String name;
    private static String dbURL = "jdbc:derby://localhost:1527/MagicianData;create=true;user=me;password=mine";
    final private String username = "mrp5379", password = "famfa50";
    private PreparedStatement insertCustomer;
    
    Customer(String n)
    {
        name=n;
    }
    Customer()
    {
        name="";
    }
    

    public int getID()
    {
        int ID=0;
        final String query = 
            "SELECT *FROM CUSTOMERS";
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
    public String getName()
    {
        return name;
    }
    public void setName(String s)
    {
        name=s;
    }
    
}
