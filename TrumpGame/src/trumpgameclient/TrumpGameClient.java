/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpgameclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import trumpgameserver.*;

/**
 *
 * @author d.gozzi
 */
public class TrumpGameClient {

    public static Socket socket;

    public static boolean InputFromConsole = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //new GUIHandler();
        if (InputFromConsole) {
            try {
                SetSocketParameter("127.0.0.1");
                // TODO code application logic here
                BufferedReader inputKeyboard = new BufferedReader(new InputStreamReader(System.in));
                while (true) {

                    String userInput = inputKeyboard.readLine();
                    if(userInput.contains("CreateALobby")) new trumpgameserver.TrumpGameServer();
                    WriterClass.instance.WriteToServer(userInput);

                }

            } catch (IOException ex) {
                System.out.println("Error " +ex);
            }
        }

    }

    public static void CloseConnection() {

        try {
            WriterClass.instance.Disconnecting();
            WriterClass.instance.writerToServer.close();
            ListenerClass.instance.StopThread();
            socket.close();

        } catch (IOException ex) {
            System.out.println("Error Disconnectiong: " + ex);
        }
    }

    public static void SetUpIOWithServer() {
        new WriterClass();
        new ListenerClass();
        new MessageHandler();
    }

    public static void SetSocketParameter(String address) throws UnknownHostException, IOException {

        socket = new Socket(InetAddress.getByName(address), 1050);
        SetUpIOWithServer();

    }

}
