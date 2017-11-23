/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpmasterserver;

import jdk.nashorn.internal.codegen.CompilerConstants;

/**
 *
 * @author d.gozzi
 */
public class PlayerMessageHandler
{

    public static PlayerMessageHandler instance = null;

    public static PlayerMessageHandler getInstace()
    {
        if (instance == null)
        {
            new PlayerMessageHandler();
        }
        return instance;
    }

    public PlayerMessageHandler()
    {
        if (instance != null)
        {
            return;
        }
        instance = this;
    }

    public void HandleMessage(UserPlayer player, String msg)
    {
        if (msg.equals("") || msg.equals(" ") || msg == null)
        {
            return;
        }
        System.out.println("Handling msg: " + msg);
        String[] splittedMsg = msg.split(">", 2);

        String tipeOfMessage = splittedMsg[0];
        String message = splittedMsg[1];
        if (tipeOfMessage.contains("Communication"))
        {
            if (player.lobbyJoined == null)
            {
                System.out.println("That sould never happen");
            }
            //SendMsg
            return;
        }
        if (tipeOfMessage.contains("PlayerSettings"))
        {
            if (message.equals("Login"))
            {
                String[] metaMessage = message.split(":", 3);
                //Check if the credentials are correct
                //then
                player.Login(metaMessage[1]);
                return;
            }
            if (message.equals("ListOfRooms"))
            {
                String responseToClient = "<PlayerSettings>AvailableRooms";
                for (Lobby lobby : TrumpMasterServer.lobbyList)
                {
                    responseToClient += ":" + lobby.lobbyName + ":" + lobby.id;
                }
                WriteToClient(player, responseToClient);
                return;
            }
            if (message.equals("AddMeToRoom"))
            {
                String[] metaMessage = message.split(":", 3);
                Lobby lobby = TrumpMasterServer.GetLobbyById(Integer.getInteger(metaMessage[1]));
                if (lobby == null)
                {
                    WriteToClient(player, "<PlayerSettings>LoginRefused:Lobby Not Found");
                    return;
                }
                //Create the login function in the lobby class
                int result = lobby.LogInToLobby(player, metaMessage[2]);
                String msgToSend = "<PlayerSettings>";
                switch (result)
                {
                    case 2:
                        msgToSend += "LoginRefused:" + "The Room is full";
                        break;
                    case 1:
                        msgToSend += "LoginRefused:" + "Wrong password";
                        break;
                    case 0:
                        msgToSend += "LoginAccepted";
                        break;
                }
                WriteToClient(player, msgToSend);
                //And Finish the statement
                return;
            }
            if(message.equals("SignUp"))
            {
              String[] metaMessage = message.split(":", 3);
              
            }
        }
    }

    public void WriteToClient(UserPlayer player, String msg)
    {
        PlayerMessageWriter.getInstace().WriteMessage(player, msg);
    }

}
