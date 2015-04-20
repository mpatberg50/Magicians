package Magicians;

//this class will create a booking entry

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;

public class BookingListEntry {
    private int customerID;
    private int holidayID;
    private int magicianID;
    
    private static String dbURL = "jdbc:derby://localhost:1527/MagicianData";
    final private String username = "mrp5379", password = "famfa50";
    private Connection connection;
    private PreparedStatement newBookingEntry, addMagician;
    
    
    BookingListEntry(int cusID, int holID)
    {
        customerID=cusID;
        holidayID=holID;
        final String query = "INSERT INTO APP.BOOKING"+
                                "(CUSTOMERID,HOLIDAYID,MAGICIANID,TIMEOFBOOKING)"+
                                 "VALUES(?,?,?,?)";        
        try
        {
            connection = DriverManager.getConnection(dbURL,username,password);
            newBookingEntry = connection.prepareStatement(query);
            
            newBookingEntry.setInt(1, customerID);
            newBookingEntry.setInt(2, holidayID);
            
            

            int magicianNumber = -1;
            final String query2 = "SELECT  APP.MAGICIANS.MAGICIANID, APP.BOOKING.CUSTOMERID"+
                                    " FROM APP.MAGICIANS "+
                                    " LEFT OUTER JOIN APP.BOOKING "+
                                    " ON APP.MAGICIANS.MAGICIANID=APP.BOOKING.MAGICIANID AND APP.BOOKING.HOLIDAYID=?";        
            try
            {
                  addMagician = connection.prepareStatement(query2);
                  addMagician.setInt(1, holidayID);
                  ResultSet resultSet = addMagician.executeQuery();

                  while(resultSet.next())
                  {
                      if(resultSet.getObject("CUSTOMERID")==null)
                      {
                          magicianNumber =  resultSet.getInt("MAGICIANID");
                      }
                  }
            }
            catch(SQLException exception)
            {
                exception.printStackTrace();
            }
            
            
            newBookingEntry.setInt(3, magicianNumber);
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            newBookingEntry.setTimestamp(4, currentTimestamp);
            newBookingEntry.executeUpdate();

            }
            catch(SQLException exception)
            {
                exception.printStackTrace();
            }
        }

    public int getCustomer()
    {
        return customerID;
    }
    public int getHoliday()
    {
        return holidayID;
    }
    public int getMagician()
    {
        return magicianID;
    }   
    public void setCustomer(int c)
    {
        customerID= c;
    }
    public void setHoliday(int h)
    {
        holidayID=h;
    }
    public void setMagician(int m)
    {
        magicianID=m;
    }
}