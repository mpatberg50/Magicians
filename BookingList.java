package Magicians;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;


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
    
    //returns whether or not a legitamite booking can be made
    public boolean addBooking(String customer, String holiday)
    {
        if(!customerList.contains(customer))
        {
            customerList.add(customer);
        }
        
        if(!customerHasBooking(customer,holiday))
        {
            new BookingListEntry(customerList.getID(customer),holidayList.getHolidayID(holiday));
            return true;
        }
        return false;
    }
    //gets output for booking
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
            
            list+="Customer\tMagician\n";

            
            while(resultSet.next())
            {
                list+= customerList.getCustomerName(resultSet.getInt("CUSTOMERID")) + "\t "+ magicianList.getMagicianName(resultSet.getInt("MAGICIANID")) + "\n";
            }
        }
        catch(SQLException exception)
        {
                exception.printStackTrace();
        }
        
        return list;
    }
    
 
    //gets output for magician status, m is magician requested
    public String getMagicianStatus(String m)
    {
        String list = m + "'s customer list.\n\n";
        final String query = "SELECT CUSTOMERID,HOLIDAYID,MAGICIANID   "
                + "FROM   APP.BOOKING   WHERE   MAGICIANID = ?";
        
        try
        {
            connection = DriverManager.getConnection(dbURL,username,password);
            printEntries = connection.prepareStatement(query);
            printEntries.setInt(1, magicianList.getMagicianID(m));
            ResultSet resultSet = printEntries.executeQuery();
            
            list+="Holiday\t  Customer\n";

            if(!resultSet.next())
                return m + " has no customers.";
            
            do
            {
                list+=  holidayList.getHolidayName(resultSet.getInt("HOLIDAYID"))+ "\t "+ customerList.getCustomerName(resultSet.getInt("CUSTOMERID")) + "\n";
            }while(resultSet.next());          
            
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
        return list;
    }
    //gets output for holiday status, h is holiday requested
    public String getHolidayStatus(String h)
    {
        String list = h + "'s bookings.\n\n";
        final String query = "SELECT CUSTOMERID,HOLIDAYID,MAGICIANID   "
                + "FROM   APP.BOOKING   WHERE   HOLIDAYID = ? AND MAGICIANID<>-1";
        
        
        try
        {
            connection = DriverManager.getConnection(dbURL,username,password);
            printEntries = connection.prepareStatement(query);
            printEntries.setInt(1, holidayList.getHolidayID(h));
            ResultSet resultSet = printEntries.executeQuery();
            
            list+="Customer\tMagician\n\n";

            if(!resultSet.next())
                return "There are no bookings for " + h +".";
            do
            {
                list+=  customerList.getCustomerName(resultSet.getInt("CUSTOMERID"))+ "\t"+ magicianList.getMagicianName(resultSet.getInt("MAGICIANID")) + "\n";
            }while(resultSet.next() && resultSet.getInt("MAGICIANID")!=-1);          
            
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
        return list;
    }
    //gets output for waitlist status, h is holiday requested
    public String getWaitlist(String h)
    {
        
        String list ="";
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
 
            if(!resultSet.next())
                list = "There is no waitlist for " + h +".";
            else
            {               
                list = "Waitlist for " + h + " \n\n"; 
                do   
                {
                    list+=  x+ ". " + customerList.getCustomerName(resultSet.getInt("CUSTOMERID"))+"\n";
                    x++;
                }while(resultSet.next());    
            }
            
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        
        return list;
    }
    //checks to see if a customer already has a booking for a desired holiay
    private boolean customerHasBooking(String customer, String holiday)
    {
        final String query = "SELECT HOLIDAYID, CUSTOMERID FROM APP.BOOKING "
                + "WHERE HOLIDAYID= ? AND CUSTOMERID = ?";
        try(Connection con = DriverManager.getConnection(dbURL,username,password);
                PreparedStatement prepStat= con.prepareStatement(query)
                )
        {
           prepStat.setInt(1, holidayList.getHolidayID(holiday));
           prepStat.setInt(2, customerList.getID(customer));
           ResultSet resultSet = prepStat.executeQuery();
           
           if(!resultSet.next())
               return false;
        }
            
         catch(SQLException exception)
        {
            exception.printStackTrace();
        }
                
        return true;
    }   
    
    
    //returns list of holidays in database;
    public ArrayList getHolidays ()
    {
        return holidayList.getHolidayList();
    }
    public ArrayList getMagicians()
    {
        return magicianList.getMagicians();
    }
    public ArrayList getCustomers()
    {
        return customerList.getCustomerList();
    }
    
    
    public void addMagician(String m)
    {
        magicianList.addMagician(m);
        magicianList.assignMagician(m);     
        
    }
    public void addHoliday (String h)
    {
        holidayList.addHoliday(h);
    }
    public void dropMagician(String m)
    {       
        magicianList.removeMagician(m);
        magicianList.reAssignMagician();
        magicianList.dropMagician(m);

    }
    public boolean customerInSystem(String c)
    {
        return customerList.contains(c);
    }
    
    public boolean cancelBooking(String c, String h)
    {
        final int customerID = customerList.getID(c);
        final int holidayID = holidayList.getHolidayID(h);

        int magicianID = -1;
        
        String findQuery = "SELECT * FROM APP.BOOKING WHERE HOLIDAYID = ?"
                + " ORDER BY TIMEOFBOOKING ASC";

        
        //deletes from database
        String deleteQuery = "DELETE  FROM APP.BOOKING WHERE CUSTOMERID=? AND HOLIDAYID = ? ";
       
        try
        {         
            Connection conn =DriverManager.getConnection(dbURL,username,password);
            PreparedStatement findBooking = conn.prepareStatement(findQuery);
            findBooking.setInt(1, holidayID);
            ResultSet findSet = findBooking.executeQuery();
            
            if(!findSet.next())
            {
                //if empty return false
                return false;
            }
            else
            {
                String setMagicianQuery = "UPDATE APP.BOOKING SET MAGICIANID = ? "
                        + "WHERE CUSTOMERID = ? AND HOLIDAYID=?";
                PreparedStatement setMagician = conn.prepareStatement(setMagicianQuery);
                
                do
                {
                    if(findSet.getInt("CUSTOMERID")==customerID)
                    {
                        magicianID = findSet.getInt("MAGICIANID");
                    }
                    else if(findSet.getInt("MAGICIANID")==-1 && magicianID>=0)
                    {
                        
                        
                        setMagician.setInt(1,magicianID);
                        setMagician.setInt(2, findSet.getInt("CUSTOMERID"));
                        setMagician.setInt(3, findSet.getInt("HOLIDAYID"));
                        setMagician.executeUpdate();
                        
                        System.out.println(magicianID);
                        //ensures it only goes through else if once
                        magicianID=-2;
                    }
                }while(findSet.next());
            }
            
            
            PreparedStatement deleteMagician = conn.prepareStatement(deleteQuery);
            deleteMagician.setInt(1, customerID);
            deleteMagician.setInt(2, holidayID);
            deleteMagician.executeUpdate();
        }
        catch(SQLException exception)
        {
            exception.printStackTrace();
        }
        
        //magicianID will = -1 if it does not find a magicianID
        if(magicianID!=-1)
            return true;
        
        return false;
    }
    
}
