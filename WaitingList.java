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
public class WaitingList {
    private WaitingListEntry[] waitList = new WaitingListEntry[20];
    
    public void addWaitList(WaitingListEntry b)
    {
        for(int x =0; x<waitList.length; x++)
            if(waitList[x]==null)
            {
                waitList[x]=b;
                x=waitList.length;
            }
    }
    
    public WaitingListEntry[] getWaitList()
    {
        return waitList;
    }
}
