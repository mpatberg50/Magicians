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
public class BookingList {
    private BookingListEntry[] bookingList = new BookingListEntry[10];
    
    public void addBooking(BookingListEntry b)
    {
        for(int x =0; x<bookingList.length; x++)
            if(bookingList[x]==null)
            {
                bookingList[x]=b;
                x=bookingList.length;
            }
    }
}
