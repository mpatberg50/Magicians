
package Magicians;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//this class will create a customer list of every customer and access data from the customer table

public class CustomerList {

    private final ArrayList<String> customers = new ArrayList();
    private final String dbURL = "jdbc:derby://localhost:1527/MagicianData";
    final private String username = "mrp5379", password = "famfa50";
    private Connection connection;
    private PreparedStatement insertCustomer;
    
    CustomerList()
    {
        final String query = "SELECT * FROM APP.CUSTOMER";
        try
        {
            connection = DriverManager.getConnection(dbURL,username,password);
            insertCustomer = connection.prepareStatement(query);
            ResultSet resultSet = insertCustomer.executeQuery();
            while(resultSet.next())
            {
                customers.add(resultSet.getString("NAME"));
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }
    
    public void add(String cust)
    {
 
        final String query = 
                "INSERT INTO APP.CUSTOMER"+
                "(Name,ID)"+
                "VALUES(?,?)";
        
        if(!this.contains(cust))
        {                
                try
                {
                    connection = DriverManager.getConnection(dbURL,username,password);
                    insertCustomer = connection.prepareStatement(query);
                    insertCustomer.setString(1, cust);
                    insertCustomer.setInt(2,customers.size());
                    customers.add(cust);
                    insertCustomer.executeUpdate();
                    
                }
                catch (SQLException sqlException)
                {
                    sqlException.printStackTrace();
                }
        }

        
    }
    public int getID(String cust)
    {
        int ID=0;
        final String query = 
            "SELECT *FROM APP.CUSTOMER";
        try(Connection connection = DriverManager.getConnection(dbURL,username,password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query))
        {
            ResultSetMetaData metaData = resultSet.getMetaData();
            
            while(resultSet.next())
            {
                if(resultSet.getString("NAME").equals(cust))
                    ID =resultSet.getInt("ID");
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
        for(int x=0; x<customers.size();x++)
            if(customers.get(x).equals(name))
                return true;
        return false;
    }
    public String getCustomerName(int id)
    {
        String name="";
        final String query = 
            "SELECT *FROM APP.CUSTOMER";
        try(Connection connection = DriverManager.getConnection(dbURL,username,password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query))
        {
            ResultSetMetaData metaData = resultSet.getMetaData();
            
            while(resultSet.next())
            {
                if(resultSet.getInt("ID")==id)
                    name = resultSet.getString("NAME");
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        return name;
    }
    public ArrayList getCustomerList()
    {
        return customers;
    }
}