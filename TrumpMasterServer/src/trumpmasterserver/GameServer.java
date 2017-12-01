/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpmasterserver;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author d.gozzi
 */
public class GameServer extends UserPlayer
{

    public boolean isServerRunning;
    public GameServer(Socket socket, Lobby lobby) throws IOException
    {
        super(socket);
        isServerRunning = true;
        lobbyJoined = lobby;
    }

    @Override
    public void run()
    {
       
        while (isServerRunning)
        {            
           try
            {
               lobbyJoined.SendToAllClient(readFromClient.readLine());
            }
            catch (IOException ex)
            {
                System.out.println("Error reading From Socket " + ex);
                Disconnect();
            } 
        }
       
    }

    @Override
    public void Disconnect()
    {
        isServerRunning = false;
        super.Disconnect(); 
    }
    
    
    
    
}
