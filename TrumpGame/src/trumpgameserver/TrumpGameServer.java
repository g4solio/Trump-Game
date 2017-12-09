/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpgameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public final int PORT = 62131;
    public int numMaxPlayer;
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
        try 
        {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) 
        {
            Logger.getLogger(TrumpGameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.start();
    }

    @Override
    public void run()
    {
        super.run(); 
        while (serverIsRunning)
        {
            try 
            {
                serverSocket.accept();
                
            } catch (IOException ex) 
            {
                Logger.getLogger(TrumpGameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
