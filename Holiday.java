package Magicians;

import java.sql.Connection;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patberg
 */
public class Holiday {
    private static String dbURL = "jdbc:derby://localhost:1527/MagicianData;create=true;user=me;password=mine";
    private static String tableName = "restaurants";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;
    
    private String[] holidays = new String[10];
    
    Holiday()
    {
        for(int x=0; x<holidays.length;x++)
        {
            holidays[x]=null;
        }
    }
    Holiday(String[] h)
    {
        holidays=h;
    }
    
    public void add(String h)
    {
        for(int x=0; x<holidays.length;x++)
        {
            if(holidays[x]==null)
            {
                holidays[x]=h;
                x=holidays.length;
            }
        }
    }
    
    
}
