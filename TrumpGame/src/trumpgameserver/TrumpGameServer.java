/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpgameserver;

import java.net.ServerSocket;

/**
 *
 * @author d.gozzi
 */
public class TrumpGameServer extends Thread
{
    private static TrumpGameServer instance = null;
    
    // <editor-fold desc="Variables" defaultstate="collapsed">
    public ServerSocket serverSocket; 
    public boolean serverIsRunning;
    // </editor-fold>
            
            
    public static TrumpGameServer getInstance()
    {
        if(instance == null) 
        {
            new TrumpGameServer();
            System.out.println("This should never happen");
        }
        return instance;
    }

    public TrumpGameServer()
    {
        if(instance != null) return;
        instance = this;
        serverIsRunning = true;
        this.start();
    }

    @Override
    public void run()
    {
        super.run(); 
        while (serverIsRunning)
        {
            
        }
    }
    
    
}
