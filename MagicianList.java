package Magicians;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

//This class contains the list of magician names along with accesors to the magician table

public class MagicianList {
    private static String dbURL = "jdbc:derby://localhost:1527/MagicianData";
    private final String username = "mrp5379", password = "famfa50";
    // jdbc Connection
    private static Connection connection = null;
    private static PreparedStatement addMagician = null;
    private ArrayList<String> magicians = new ArrayList();
    
    MagicianList()
    {
        final String query = "SELECT * From APP.MAGICIANS";
        try(Connection conn = DriverManager.getConnection(dbURL,username,password);
                Statement stat = conn.createStatement();
                ResultSet resultSet = stat.executeQuery(query);
                )
        {
            while(resultSet.next())
            {
                magicians.add(resultSet.getString("NAME"));
            }
            
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
    }
    
    
    
    public int getMagicianID (String name)
    {
           int ID=0;
        final String query = 
            "SELECT *FROM APP.MAGICIANS";
        try(Connection connection = DriverManager.getConnection(dbURL,username,password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query))
        {
            
            while(resultSet.next())
            {
                if(resultSet.getString("NAME").equals(name))
                    ID = resultSet.getInt("MAGICIANID");
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        return ID;      
    }
    public String getMagicianName(int id)
    {
        String name="";
        final String query = 
            "SELECT *FROM APP.MAGICIANS";
        try(Connection connection = DriverManager.getConnection(dbURL,username,password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query))
        {
            ResultSetMetaData metaData = resultSet.getMetaData();
            
            while(resultSet.next())
            {
                if(resultSet.getInt("MAGICIANID")==id)
                    name = resultSet.getString("NAME");
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        return name;
    }
    
    public ArrayList getMagicians()
    {
        return magicians;
    }
    public void addMagician(String m)
    {      
        magicians.add(m);
        int id = 0;
        String query = "SELECT *FROM APP.MAGICIANS";
        
        try(Connection connection = DriverManager.getConnection(dbURL,username,password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query))
        {
            
            while(resultSet.next())
            {
                if(id<resultSet.getInt("MAGICIANID"))
                    id = resultSet.getInt("MAGICIANID");
            }
            
            query = "INSERT INTO APP.MAGICIANS (MAGICIANID, NAME) VALUES(?,?)";
            addMagician = connection.prepareStatement(query);
            
            //the id searches for the largest max value and adds one to it
            addMagician.setInt(1, id+1);
            addMagician.setString(2, m);
            addMagician.executeUpdate();
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
  
        
        
    }
    
    public void dropMagician(String m)
    { 
        magicians.remove(m);
        final String query = "DELETE FROM APP.MAGICIAN WHERE NAME = ?";
        
        try(Connection conn = DriverManager.getConnection(dbURL,username,password);
                PreparedStatement prepStat = conn.prepareStatement(query)
                )
        {
            prepStat.setString(1, m);
            ResultSet resSet = prepStat.executeQuery();
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
    }

}