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
public class BookingListEntry {
    private Customer cust;
    private Holiday hol;
    
    BookingListEntry(Customer c, Holiday h)
    {
        cust=c;
        hol=h;
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
}
