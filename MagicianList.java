/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Magicians;

/**
 *
 * @author Patberg
 */
public class MagicianList {
    private Magician[] list= new Magician[10];
    
    MagicianList()
    {
        for(int x = 0; x<list.length;x++)
            list[x]=null;
    }
    MagicianList(Magician[] l)
    {
        list=l;
    }
    
    public void add(Magician m)
    {
        for(int x=0; x<list.length;x++)
        {
            if(list[x]==null)
            {
                list[x]=m;
                x=list.length;
            }
        }
    }
    
    public void printList()
    {
        
    }
}
