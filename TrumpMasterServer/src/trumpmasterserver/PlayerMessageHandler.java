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
            player.lobbyJoined.SendToAllClient(msg);
            //SendMsg
            return;
        }
        if (tipeOfMessage.contains("PlayerSettings"))
        {
            if (message.contains("Login"))
            {
                String[] metaMessage = message.split(":", 3);
                //int loginResult = GetUserLoginHandler().Login(metaMessage[1], metaMessage[1]);
                int loginResult = 0;
                System.out.println("wow");
                switch (loginResult)
                {
                    case 0:
                        WriteToClient(player, "<PlayerSettings>" + "LoginAccepted");
                        player.Login(metaMessage[1]);
                        break;
                    case 1:
                        WriteToClient(player, "<PlayerSettings>" + "LoginRefused:" + "Wrong password");
                        break;
                    case 2:
                        WriteToClient(player, "<PlayerSettings>" + "LoginRefused:" + "User not registered");
                        break;
                }
                return;
            }
            if (message.contains("ListOfLobbies"))
            {
                String responseToClient = "<PlayerSettings>AvailableLobbies";
                for (Lobby lobby : TrumpMasterServer.lobbyList)
                {
                    if (lobby.isTheMatchStarted)
                    {
                        continue;
                    }
                    responseToClient += ":" + lobby.lobbyName + ":" + lobby.id;
                }
                WriteToClient(player, responseToClient);
                return;
            }
            if (message.contains("AddMeToLobby"))
            {
                String[] metaMessage = message.split(":", 3);
                Lobby lobby = TrumpMasterServer.GetLobbyById(Integer.parseInt(metaMessage[1]));
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
                        msgToSend += "LoginRefused:" + "The Lobby is full";
                        break;
                    case 1:
                        msgToSend += "LoginRefused:" + "Wrong password";
                        break;
                    case 0:
                        msgToSend += "LoginAccepted:RedFaction";
                        break;
                    case 3:
                        msgToSend += "LoginAccepted:BlueFaction";
                        break;
                }

                WriteToClient(player, msgToSend);
                //And Finish the statement
                return;
            }
            if (message.contains("SignUp"))
            {
                String[] metaMessage = message.split(":", 3);

                int signUpResult = GetUserLoginHandler().SignUp(metaMessage[1], metaMessage[2]);
                String msgToSend = "<PlayerSettings>";
                switch (signUpResult)
                {
                    case 0:
                        msgToSend += "SignUpAccepted";
                        break;
                    case 1:
                        msgToSend += "SingUpRefused:NickName has already been taken";
                        break;
                }
                WriteToClient(player, msgToSend);
                return;
            }

            if (message.contains("CreateALobby"))
            {
                String[] metaMessage = message.split(":", 5);
//                for (String string : metaMessage) {
//                    System.out.println(string);
//                }
                //System.out.println(TrumpMasterServer.lobbyList);
                //TrumpMasterServer.SaySomething();
                TrumpMasterServer.CreateALobby(player, metaMessage[1], metaMessage[3], Integer.parseInt(metaMessage[4]), Integer.parseInt(metaMessage[2]));
                WriteToClient(player, "<PlayerSettings>LobbyCreatedSuccesfully");
                return;
            }
            if (message.contains("LobbyHasBeenClosed"))
            {
                TrumpMasterServer.CloseLobby(player.lobbyJoined);
                //WriteToClient(player, "<PlayerSettings>LobbyHasBeenClosed");
                return;
            }
            if (message.contains("ChangeFaction"))
            {
                String[] metaMessage = message.split(":", 2);
                int result = player.lobbyJoined.ChangeFaction(player, metaMessage[1]);

                String msgToSend = "<PlayerSettings>";

                switch (result)
                {
                    case 0:
                        msgToSend += "FactionChangedSuccesfully";
                        break;
                    case 1:
                        msgToSend += "ErrorChangingFaction";
                        break;
                }
                WriteToClient(player, msgToSend);
                if (result == 0)
                {
                    player.lobbyJoined.RefreshFactionChange(player, metaMessage[1]);
                }
                return;
            }
            if (message.contains("ReadyToPlay"))
            {
                player.lobbyJoined.NotifyReadyToPlay(player, true);
                return;
            }
            if (message.contains("NotReadyToPlay"))
            {
                player.lobbyJoined.NotifyReadyToPlay(player, false);
                return;
            }
            if (message.contains("RemoveMeFromRoom"))
            {
                player.lobbyJoined.RemovePlayerFromRoom(player);
                return;
            }
            if (message.contains("Disconnecting"))
            {
                player.Disconnect();
                return;
            }
            if (message.contains("StartTheMatch"))
            {
                player.lobbyJoined.StartTheMatch();
                return;
            }
        }
        if (tipeOfMessage.contains("GameInteraction"))
        {
            WriteToClient(player.lobbyJoined.gameServer, msg);
        }
    }

    public void WriteToClient(UserPlayer player, String msg)
    {
        PlayerMessageWriter.getInstace().WriteMessage(player, msg);
    }

    public UserLoginHandler GetUserLoginHandler()
    {
        return UserLoginHandler.instance.getInstance();
    }

}
