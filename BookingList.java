package Magicians;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
 
    
    private final static String dbURL = "jdbc:derby://localhost:1527/MagicianData;create=true;user=me;password=mine";
    private final String username = "mrp5379", password = "famfa50";
    private Connection connection;
    private PreparedStatement newBookingEntry;    
    
    public void addBooking(String customer, String holiday)
    {
        for(int x =0; x<bookingList.length;x++)
            if(bookingList[x]==null)
                bookingList[x]= new BookingListEntry(customer,holiday);
    }
    public BookingListEntry[] getBookingList()
    {
        return bookingList;
    }
}
