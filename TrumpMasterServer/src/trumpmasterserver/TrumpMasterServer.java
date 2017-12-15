/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpmasterserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author d.gozzi
 */
public class TrumpMasterServer
{

    public static ArrayList<Lobby> lobbyList;
    static Random myRandom = null;

    public static Random getRandomClass()
    {
        if (myRandom == null)
        {
            myRandom = new Random();
        }
        return myRandom;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {

        lobbyList = new ArrayList<>();
        try
        {
            // TODO code application logic here
            ServerSocket serverSocket = new ServerSocket(1050);
            System.out.println("Server Has Been Started");
            while (true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("A new Connection has been accepted");
                new UserPlayer(socket);
            }

        }
        catch (IOException ex)
        {
            System.out.println("error Starting Server " + ex);
        }

    }

    public static Lobby GetLobbyById(int id)
    {
        for (Lobby lobby : lobbyList)
        {
            return lobby;
        }
        return null;
    }

    public static void CreateALobby(UserPlayer player, String name, String generatedPassword, int gameServerPort, int numMaxPlayerFromClient)
    {
        int idRoom = GenerateRoomId();
        String ipAdress = player.connectedSocket.getInetAddress().getHostAddress();
        Lobby newLobby = new Lobby(idRoom, name, generatedPassword, ipAdress, gameServerPort, numMaxPlayerFromClient);
        lobbyList.add(newLobby);
        newLobby.LogInToLobby(player, generatedPassword);

    }

    public static void CloseLobby(Lobby lobby)
    {
        lobby.CloseLobby();
        lobbyList.remove(lobby);
    }

    private static int GenerateRoomId()
    {
        int bound = 100 + lobbyList.size();
        int randomNumber;
        mainLoop:
        while (true)
        {
            randomNumber = getRandomClass().nextInt(bound);
            for (Lobby lobby : lobbyList)
            {
                if (lobby.id == randomNumber)
                {
                    continue mainLoop;
                }
            }
            break;
        }
        System.out.println("Random Number : " + randomNumber);
        return randomNumber;
    }

    static void SaySomething()
    {
        System.out.println("Something");
    }
}
