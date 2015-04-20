package Magicians;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


//This class will contain all of the commands for interacting with the database
//It will add each booking entry along with getting statuses of the waitlist,magicians, and holidays

public class BookingList {
    private final static String dbURL = "jdbc:derby://localhost:1527/MagicianData";
    private final String username = "mrp5379", password = "famfa50";
    private Connection connection = null;
    private PreparedStatement printEntries = null;  
    private final CustomerList customerList;
    private final MagicianList magicianList;
    private final HolidayList holidayList;
    
    public BookingList()
    {
        customerList = new CustomerList();
        magicianList = new MagicianList();
        holidayList = new HolidayList();
    }
    
    public void addBooking(String customer, String holiday)
    {
            customerList.add(customer);
            new BookingListEntry(customerList.getID(customer),holidayList.getHolidayID(holiday));
    }
    public String getHolidayBookingList (String hol)
    {
        String list = "";
        final String query = "SELECT CUSTOMERID,HOLIDAYID,MAGICIANID   "
                + "FROM   APP.BOOKING   WHERE   HOLIDAYID = ? AND MAGICIANID <> -1";
        
        try
        {
            connection = DriverManager.getConnection(dbURL,username,password);
            printEntries = connection.prepareStatement(query);
            printEntries.setInt(1, holidayList.getHolidayID(hol));
            
            ResultSet resultSet = printEntries.executeQuery();
            
            list+="Customer\t  Holiday\t  Magician\n";

            
            while(resultSet.next())
            {
                list+= customerList.getCustomerName(resultSet.getInt("CUSTOMERID")) + "\t " + holidayList.getHolidayName(resultSet.getInt("HOLIDAYID"))+ "\t "+ magicianList.getMagicianName(resultSet.getInt("MAGICIANID")) + "\n";
            }
        }
        catch(SQLException exception)
        {
                exception.printStackTrace();
        }
        
        return list;
    }
    public String getMagicianStatus(String m)
    {
        String list = m + "\n\n";
        final String query = "SELECT CUSTOMERID,HOLIDAYID,MAGICIANID   "
                + "FROM   APP.BOOKING   WHERE   MAGICIANID = ?";
        
        
        try
        {
            connection = DriverManager.getConnection(dbURL,username,password);
            printEntries = connection.prepareStatement(query);
            printEntries.setInt(1, magicianList.getMagicianID(m));
            ResultSet resultSet = printEntries.executeQuery();
            
            list+="Holiday\t  Customer\n";

            
            while(resultSet.next())
            {
                list+=  holidayList.getHolidayName(resultSet.getInt("HOLIDAYID"))+ "\t "+ customerList.getCustomerName(resultSet.getInt("CUSTOMERID")) + "\n";
            }          
            
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
        return list;
    }
    public String getHolidayStatus(String h)
    {
        String list = h + "\n\n";
        final String query = "SELECT CUSTOMERID,HOLIDAYID,MAGICIANID   "
                + "FROM   APP.BOOKING   WHERE   HOLIDAYID = ?";
        
        
        try
        {
            connection = DriverManager.getConnection(dbURL,username,password);
            printEntries = connection.prepareStatement(query);
            printEntries.setInt(1, holidayList.getHolidayID(h));
            ResultSet resultSet = printEntries.executeQuery();
            
            list+="Customer\tMagician\n\n";

            
            while(resultSet.next())
            {
                list+=  customerList.getCustomerName(resultSet.getInt("CUSTOMERID"))+ "\t"+ magicianList.getMagicianName(resultSet.getInt("MAGICIANID")) + "\n";
                
            }          
            
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
        return list;
    }
    
    public String getWaitlist(String h)
    {
        
        String list = "Waitlist for " + h + " \n\n";
        final String query = "SELECT CUSTOMERID,HOLIDAYID,MAGICIANID,TIMEOFBOOKING "
                + "FROM APP.BOOKING WHERE HOLIDAYID = ? AND MAGICIANID=-1 "
                + "ORDER BY TIMEOFBOOKING ASC";
        
        
        try
        {
            connection = DriverManager.getConnection(dbURL,username,password);
            printEntries = connection.prepareStatement(query);
            printEntries.setInt(1, holidayList.getHolidayID(h));
            ResultSet resultSet = printEntries.executeQuery();
            
            
            int x =1;
            
            while(resultSet.next())
            {
                list+=  x+ ". " + customerList.getCustomerName(resultSet.getInt("CUSTOMERID"))+"\n";
                x++;
            }          
            
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
        return list;
    }
    
}
