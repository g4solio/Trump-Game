/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpmasterserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author d.gozzi
 */
public class UserPlayer extends Thread
{

    public boolean isLogged;
    public String nickName;
    public Lobby lobbyJoined;
    public Socket connectedSocket;
    public PrintWriter writerToClient;
    public BufferedReader readFromClient;

    public UserPlayer(Socket socket) throws IOException
    {
        connectedSocket = socket;
        writerToClient = new PrintWriter(new OutputStreamWriter(connectedSocket.getOutputStream()));
        readFromClient = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
        isLogged = false;
        this.start();
        return;
    }

    @Override
    public void run()
    {
        super.run();

        do
        {
            try
            {
                readFromClient.readLine();
            }
            catch (IOException ex)
            {
                System.out.println("Error reading From Socket " + ex);
            }
        }
        while (isLogged);
    }

    public void Login(String loggedNickName)
    {
        isLogged = true;
        nickName = loggedNickName;
    }

}