package Magicians;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


//This is a list of all holidays and accessors to the holidays table

public class HolidayList {
    private final String dbURL = "jdbc:derby://localhost:1527/MagicianData";
    final private String username = "mrp5379", password = "famfa50";
    private  Connection connection;
    private PreparedStatement insertCustomer, printEntries;
    
    private final ArrayList<String> holidays = new ArrayList<String>();

    HolidayList()
    {
        final String query = 
            "SELECT * FROM APP.Holidays";
        try
        {
            Connection connection = DriverManager.getConnection(dbURL,username,password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
            
            while(resultSet.next())
            {
                holidays.add((String)resultSet.getObject("NAME"));
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
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
    
    public ArrayList getHolidayList()
    {
        return holidays;
    }
    
    public void addHoliday(String h)
    {
        holidays.add(h);
        int id = 0;
        String query = "SELECT *FROM APP.HOLIDAYS";
        
        try(Connection conn = DriverManager.getConnection(dbURL,username,password);
                Statement stat= conn.createStatement();
                ResultSet resSet = stat.executeQuery(query)
                )
        {
            
            while(resSet.next())
            {
                if(id<resSet.getInt("HOLIDAYID"))
                    id = resSet.getInt("HOLIDAYID");
            }
            
            query = "INSERT INTO APP.HOLIDAYS (HOLIDAYID, NAME) VALUES(?,?)";
            PreparedStatement prepStat = conn.prepareStatement(query);
            prepStat.setInt(1, id+1);
            prepStat.setString(2, h);
            prepStat.executeUpdate();
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
    }
    
    
}
