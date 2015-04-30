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
        final String query = "DELETE FROM APP.MAGICIANS WHERE NAME = ?";
        
        try(Connection conn = DriverManager.getConnection(dbURL,username,password);
                PreparedStatement prepStat = conn.prepareStatement(query)
                )
        {
            prepStat.setString(1, m);
            prepStat.executeUpdate();
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
    }
    
    public void assignMagician(String m)
    {
        String selectQuery = "SELECT * FROM APP.BOOKING WHERE MAGICIANID = -1 "
                        +"ORDER BY TIMEOFBOOKING ASC";
        int id = this.getMagicianID(m);
        ArrayList<Integer> holidaysBooked = new ArrayList();
        int holidayID;
        
        try(Connection con = DriverManager.getConnection(dbURL,username,password);
                Statement selectStatement = con.createStatement();
                ResultSet resSet = selectStatement.executeQuery(selectQuery))
        {
            String updateQuery = "UPDATE APP.BOOKING "
                    + "SET MAGICIANID=? "
                    + "WHERE CUSTOMERID = ? AND HOLIDAYID = ?";
            
           
            PreparedStatement prepStat = con.prepareStatement(updateQuery);
            prepStat.setInt(1, id);
            
            while(resSet.next())
            {
                holidayID = resSet.getInt("HOLIDAYID");
                
                if(!holidaysBooked.contains(holidayID))
                {
                    prepStat.setInt(2, resSet.getInt("CUSTOMERID"));
                    prepStat.setInt(3,holidayID);
                    prepStat.executeUpdate();
                    holidaysBooked.add(holidayID);
                }
                
            }
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
    }
    public void removeMagician(String m)
    {
        try(Connection conn = DriverManager.getConnection(dbURL,username,password))
        {
            String updateQuery = "UPDATE APP.BOOKING SET MAGICIANID=-1 WHERE MAGICIANID = ?";
            
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setInt(1, this.getMagicianID(m));
            updateStatement.executeUpdate();
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        //REASSIGN MAGICIANS
    }
    public void reAssignMagician()
    {
        String query = "SELECT * FROM APP.BOOKING "
                + " ORDER BY HOLIDAYID, TIMEOFBOOKING ASC";
        
        try(Connection connection = DriverManager.getConnection(dbURL, username, password);
                PreparedStatement assignMagician = connection.prepareStatement(query))
        {
            
            ResultSet magicianTable = assignMagician.executeQuery();
            int tempMagNum = -1;
            int customerID = -1;
            int holidayID = -1;
            int tempHolidayID;
            
            
            String addQuery = "UPDATE APP.BOOKING"
                    + " SET MAGICIANID = ? WHERE CUSTOMERID = ?";
            String removeQuery = "UPDATE APP.BOOKING"
                    + " SET MAGICIANID = -1 WHERE MAGICIANID = ?";

            PreparedStatement takeAwayMagician = connection.prepareStatement(removeQuery);
            PreparedStatement giveMagician = connection.prepareStatement(addQuery);
            
            
            while(magicianTable.next())
            {
                if(holidayID==-1)
                {
                    holidayID = magicianTable.getInt("HOLIDAYID");
                }
                
                
                tempHolidayID = magicianTable.getInt("HOLIDAYID");
                
                if(holidayID!=tempHolidayID  )
                {
                    if(tempMagNum!=-1)
                    {
                        takeAwayMagician.setInt(1, tempMagNum);
                        giveMagician.setInt(1, customerID);
                        giveMagician.setInt(2, tempMagNum);

                        takeAwayMagician.executeUpdate();
                        giveMagician.executeUpdate();
                    }
                    
                    tempHolidayID = holidayID;
                    customerID = -1;
                    tempMagNum = -1;    
                }
                
                
                
                if(magicianTable.getInt("MAGICIANID")==-1 && customerID == -1)
                {
                    customerID= magicianTable.getInt("CUSTOMERID");
                }
                else if(magicianTable.getInt("MAGICIANID")!=-1)
                {
                    tempMagNum = magicianTable.getInt("MAGICIANID");
                }
            }

        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
    }
    
}