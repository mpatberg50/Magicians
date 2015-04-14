/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Magicians;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Patberg
 */
public class CustomerList {

    private Customer[] customers = new Customer[10];
    private final String dbURL = "jdbc:derby://localhost:1527/MagicianData;create=true;user=me;password=mine";
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
    CustomerList(Customer[] h)
    {
        customers=h;
    }
    
    public void add(Customer cust)
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
                    insertCustomer.setString(1, cust.getName());
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
        for(int x=0; x<customers.length;x++)
            if(customers[x].equals(cust))
                return x;
        
        return 0;
    }
}


