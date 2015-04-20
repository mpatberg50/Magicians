package Magicians;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patberg
 */
import java.sql.*;
import java.util.Calendar;

public class BookingListEntry {
    private String customer;
    private String holiday;
    private String magician;
    private String name;
    
    private static String dbURL = "jdbc:derby://localhost:1527/MagicianData";
    final private String username = "mrp5379", password = "famfa50";
    private Connection connection;
    private PreparedStatement newBookingEntry, addMagician;
    private DatabaseAccessors accessors = new DatabaseAccessors();
    
    
    BookingListEntry(String c, String h)
    {
        customer=c;
        holiday=h;
        final String query = "INSERT INTO APP.BOOKING"+
                                "(CUSTOMERID,HOLIDAYID,MAGICIANID,TIMEOFBOOKING)"+
                                 "VALUES(?,?,?,?)";        
        try
        {
            connection = DriverManager.getConnection(dbURL,username,password);
            newBookingEntry = connection.prepareStatement(query);
            
            newBookingEntry.setInt(1, accessors.getCustomerID(c));
            newBookingEntry.setInt(2, accessors.getHolidayID(h));
            
            

            int magicianNumber = -1;
            final String query2 = "SELECT  APP.MAGICIANS.MAGICIANID, APP.BOOKING.CUSTOMERID"+
                                    " FROM APP.MAGICIANS "+
                                    " LEFT OUTER JOIN APP.BOOKING "+
                                    " ON APP.MAGICIANS.MAGICIANID=APP.BOOKING.MAGICIANID AND APP.BOOKING.HOLIDAYID=?";        
            try
            {
                  addMagician = connection.prepareStatement(query2);
                  addMagician.setInt(1, accessors.getHolidayID(holiday));
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

    public String getCustomer()
    {
        return customer;
    }
    public String getHoliday()
    {
        return holiday;
    }
    public void setCustomer(String c)
    {
        customer= c;
    }
    public void setHoliday(String h)
    {
        holiday=h;
    }
    public String getMagician()
    {
        return magician;
    }
}