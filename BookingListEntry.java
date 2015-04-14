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


public class BookingListEntry {
    private Customer cust;
    private Holiday hol;
    private Magician mag;
    private String name;
    
    private static String dbURL = "jdbc:derby://localhost:1527/MagicianData;create=true;user=me;password=mine";
    final private String username = "mrp5379", password = "famfa50";
    private Connection connection;
    private PreparedStatement newBookingEntry;
    
    
    BookingListEntry(Customer c, Holiday h)
    {
        cust=c;
        hol=h;
        final String query = "INSERT INTO BOOKING"+
                                "(CUSTOMERID,HOLIDAYID,MAGICIANID,TIMEOFBOOKING)"+
                                 "VALUES(?,?,?,?)";        
        try
        {
            connection = DriverManager.getConnection(dbURL,username,password);
            newBookingEntry = connection.prepareStatement(query);
            
            newBookingEntry.setInt(1, c.getID(query));
            
            
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
        
    }
    
    public Customer getCustomer()
    {
        return cust;
    }
    public Holiday getHoliday()
    {
        return hol;
    }
    public void setCustomer(Customer c)
    {
        cust= c;
    }
    public void setHoliday(Holiday h)
    {
        hol=h;
    }
    public void setMagician(Magician m)
    {
        mag=m;
    }
    public Magician getMagician()
    {
        return mag;
    }
}
