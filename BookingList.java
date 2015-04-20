package Magicians;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patberg
 */
public class BookingList {
    private final BookingListEntry[] bookingList = new BookingListEntry[20];
 
    
    private final static String dbURL = "jdbc:derby://localhost:1527/MagicianData";
    private final String username = "mrp5379", password = "famfa50";
    private Connection connection = null;
    private PreparedStatement printEntries = null;   
    private final DatabaseAccessors accessors;
    private final CustomerList customerList;
    
    public BookingList()
    {
        accessors = new DatabaseAccessors();
        customerList = new CustomerList();
    }
    
    
    public void addBooking(String customer, String holiday)
    {
        for(int x =0; x<bookingList.length;x++)
            if(bookingList[x]==null)
            {
                customerList.add(customer);
                bookingList[x]= new BookingListEntry(customer,holiday);
                x=bookingList.length;
            }
    }
    public BookingListEntry[] getBookingList()
    {
        return bookingList;
    }
    public String getHolidayBookingList (String hol)
    {
        String list = "";
        final String query = "SELECT CUSTOMERID,HOLIDAYID,MAGICIANID   "
                + "FROM   APP.BOOKING   WHERE   HOLIDAYID = ?";
        
        try
        {
            connection = DriverManager.getConnection(dbURL,username,password);
            printEntries = connection.prepareStatement(query);
            printEntries.setInt(1, accessors.getHolidayID(hol));
            
            ResultSet resultSet = printEntries.executeQuery();
            
            list+="Customer\t  Holiday\t  Magician\n";

            
            while(resultSet.next())
            {
                System.out.println(resultSet.getInt("CUSTOMERID")+ " " + resultSet.getInt("HOLIDAYID")+ " "+ resultSet.getInt("MAGICIANID"));
                list+= accessors.getCustomerName(resultSet.getInt("CUSTOMERID")) + "\t| " + accessors.getHolidayName(resultSet.getInt("HOLIDAYID"))+ "\t| "+ accessors.getMagicianName(resultSet.getInt("MAGICIANID")) + "\n";
            }
        }
        catch(SQLException exception)
        {
                exception.printStackTrace();
        }
        
        return list;
    }
}
