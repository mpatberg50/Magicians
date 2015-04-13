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
public class Magician {
    private static String dbURL = "jdbc:derby://localhost:1527/MagicianData;create=true;user=me;password=mine";
    private static String tableName = "restaurants";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;
    
    private String[] magicians = new String[10];
    
    Magician()
    {
        for(int x=0; x<magicians.length;x++)
        {
            magicians[x]=null;
        }
    }
    Magician(String[] m)
    {
        magicians=m;
    }
    
    public void add(String m)
    {
        for(int x=0; x<magicians.length;x++)
        {
            if(magicians[x]==null)
            {
                magicians[x]=m;
                x=magicians.length;
            }
        }
    }
}
