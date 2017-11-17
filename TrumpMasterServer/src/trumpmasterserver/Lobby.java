/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpmasterserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author d.gozzi
 */
public class Lobby 
{
    
    public UserPlayer[] connectedPlayer;
    public String lobbyName;
    public String password;
    public int id;
    public Socket gameServerSocket;
    
    public Lobby(int generatedId, String name, String generatedPassword, String gameServerIp, int gameServerPort)
    {
        lobbyName = name;
        id = generatedId;
        password = generatedPassword;
        try
        {
            gameServerSocket = new Socket(InetAddress.getByName(gameServerIp), gameServerPort);
        }
        catch (IOException ex)
        {
            System.out.println("Error creating Connection to server " + ex);
        }
    }
    
}
