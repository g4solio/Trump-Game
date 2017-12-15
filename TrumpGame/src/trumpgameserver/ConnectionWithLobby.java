/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpgameserver;

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
 * @author daddi
 */
public class ConnectionWithLobby extends Thread
{
    public Socket socket;
    public PrintWriter writer;
    public BufferedReader reader;
    public boolean canListen;
    public ConnectionWithLobby(Socket lobbySocket)
    {
        socket = lobbySocket;
        canListen = true;
        try {
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {
            System.out.println("Error Creating Reader And Writer: "+ ex);
        }
        this.start();
    }

    @Override
    public void run()
    {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        while(canListen)
        {
            try {
                ServerMessageHandler.getInstace().HandleMessage(reader.readLine());
            } catch (IOException ex) {
                System.out.println("Error reading Message: " +ex);
            }
        }
    }
    
    public void Write(String message)
    {
        writer.println(message);
        writer.flush();
    }
    
    public void Close() throws IOException, InterruptedException
    {
        writer.close();
        reader.close();
        socket.close();
        canListen = false;
        this.join();
    }
    
}
