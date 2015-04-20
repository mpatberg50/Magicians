/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Magicians;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Patberg
 */
public class CustomerList {

    private String[] customers = new String[10];
    private final String dbURL = "jdbc:derby://localhost:1527/MagicianData";
    final private String username = "mrp5379", password = "famfa50";
    private Connection connection;
    private PreparedStatement insertCustomer;
    
    CustomerList()
    {
        for(int x=0; x<customers.length;x++)
        {
            customers[x]=null;
        }
    }
    CustomerList(String[] h)
    {
        customers=h;
    }
    
    public void add(String cust)
    {
        final String query = 
                "INSERT INTO CUSTOMERS"+
                "(Name,CustomerID)"+
                "VALUES(?,?)";
        for(int x=0; x<customers.length;x++)
        {
            if(customers[x]==null)
            {
                customers[x]=cust;
                x=customers.length;
                
                try
                {
                    connection = DriverManager.getConnection(dbURL,username,password);
                    insertCustomer = connection.prepareStatement(query);
                    insertCustomer.setString(1, cust);
                    insertCustomer.setInt(2, x);
                    insertCustomer.executeQuery();
                }
                catch (SQLException sqlException)
                {
                    sqlException.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
    
public int getID(String cust)
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
                if(resultSet.getObject(1).equals(cust))
                    ID =(Integer) resultSet.getObject(2);
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        return ID;
    }

    public boolean contains(String name)
    {
        for(int x=0; x<customers.length;x++)
            if(customers[x].equals(name))
                return true;
        return false;
    }
}


