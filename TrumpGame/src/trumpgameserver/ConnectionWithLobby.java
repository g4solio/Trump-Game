/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpgameserver;

import java.net.Socket;

/**
 *
 * @author daddi
 */
public class ConnectionWithLobby extends Thread
{
    public Socket socket;

    public ConnectionWithLobby(Socket lobbySocket)
    {
        socket = lobbySocket;
        this.start();
    }

    @Override
    public void run()
    {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        while(true)
        {
            
        }
    }
    
    
}
