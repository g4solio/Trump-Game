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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author d.gozzi
 */
public class Lobby
{

    public ArrayList<UserPlayer> redFaction;
    public ArrayList<UserPlayer> blueFaction;
    public String lobbyName;
    public String password;
    public int id;
    public GameServer gameServer;
    public int numMaxPlayer;
    public boolean isTheMatchStarted;

    public Lobby(int generatedId, String name, String generatedPassword, String gameServerIp, int gameServerPort, int numMaxPlayerFromClient)
    {
        isTheMatchStarted = false;
        redFaction = new ArrayList<>();
        blueFaction = new ArrayList<>();

        lobbyName = name;
        id = generatedId;
        password = generatedPassword;
        try
        {
            gameServer = new GameServer(new Socket(InetAddress.getByName(gameServerIp), gameServerPort), this);
        }
        catch (IOException ex)
        {
            System.out.println("Error creating Connection to server " + ex);
        }
        numMaxPlayer = numMaxPlayerFromClient;
    }

    // 0 ----> All Good, RedFaction
    // 1 ----> Wrong Password
    // 2 ----> Room Full
    // 3 ----> All Good, BlueFaction
    public int LogInToLobby(UserPlayer player, String passwordFromClient)
    {
        int returnValue = 0;
        if (!passwordFromClient.equals(password))
        {
            return 1;
        }
        if ((redFaction.size() + blueFaction.size()) >= numMaxPlayer)
        {
            return 2;
        }

        if (redFaction.size() >= numMaxPlayer / 2)
        {
            blueFaction.add(player);
            returnValue = 3;
        }
        else
        {
            redFaction.add(player);
        }
        player.lobbyJoined = this;

        String msgToSend = "<PlayerSettings>PlayerJoinedLobby:" + player.nickName + ":" + (returnValue == 0 ? "RedFaction" : "BlueFaction");
        for (UserPlayer factionedPlayer : redFaction)
        {
            PlayerMessageWriter().WriteMessage(factionedPlayer, msgToSend);
        }
        for (UserPlayer factionedPlayer : blueFaction)
        {
            PlayerMessageWriter().WriteMessage(factionedPlayer, msgToSend);
        }
        return returnValue;
    }

    public void CloseLobby()
    {
        for (UserPlayer connectedPlayer : redFaction)
        {
            connectedPlayer.lobbyJoined = null;
            PlayerMessageWriter().WriteMessage(connectedPlayer, "<PlayerSettings>LobbyHasBeenClosed");
        }
        for (UserPlayer connectedPlayer : blueFaction)
        {
            connectedPlayer.lobbyJoined = null;
            PlayerMessageWriter().WriteMessage(connectedPlayer, "<PlayerSettings>LobbyHasBeenClosed");
        }

        gameServer.Disconnect();

    }

    // 0 ----> All good
    // 1 ----> Can't switch
    public int ChangeFaction(UserPlayer player, String faction)
    {
        if (faction.equals("RedFaction"))
        {
            if (blueFaction.contains(player) && redFaction.size() < numMaxPlayer)
            {
                blueFaction.remove(player);
                redFaction.add(player);
                return 0;
            }
            return 1;
        }
        if (faction.equals("BlueFaction"))
        {
            if (redFaction.contains(player) && blueFaction.size() < numMaxPlayer)
            {
                redFaction.remove(player);
                blueFaction.add(player);
                return 0;
            }
            return 1;
        }
        return 1;
    }

    public void RefreshFactionChange(UserPlayer player, String Faction)
    {
        String msgToSend = "<PlayerSettings>ChangeFactions:" + player.nickName + ":" + Faction;
        for (UserPlayer connectedPlayer : redFaction)
        {
            connectedPlayer.lobbyJoined = null;
            PlayerMessageWriter().WriteMessage(connectedPlayer, msgToSend);
        }
        for (UserPlayer connectedPlayer : blueFaction)
        {
            connectedPlayer.lobbyJoined = null;
            PlayerMessageWriter().WriteMessage(connectedPlayer, msgToSend);
        }
    }

    public PlayerMessageWriter PlayerMessageWriter()
    {
        return PlayerMessageWriter.getInstace();
    }

    public void NotifyReadyToPlay(UserPlayer playerReady, boolean readyToPlay)
    {
        String msgToSend = "<PlayerSettings>";
        msgToSend += readyToPlay ? "ReadyToPlay:" : "NotReadyToPlay:";
        msgToSend += playerReady.nickName;
        for (UserPlayer redPlayer : redFaction)
        {
            PlayerMessageWriter().WriteMessage(redPlayer, msgToSend);
        }
        for (UserPlayer bluePlayer : blueFaction)
        {
            PlayerMessageWriter().WriteMessage(bluePlayer, msgToSend);
        }
        
    }

    void RemovePlayerFromRoom(UserPlayer player)
    {
        if (redFaction.contains(player))
        {
            redFaction.remove(player);
        }
        else
        {
            blueFaction.remove(player);
        }
        String msgToSend = "<PlayerSettings>PlayerAbandonedLobby" + player.nickName;
        for (UserPlayer redPlayer : redFaction)
        {
            PlayerMessageWriter().WriteMessage(redPlayer, msgToSend);
        }
        for (UserPlayer bluePlayer : blueFaction)
        {
            PlayerMessageWriter().WriteMessage(bluePlayer, msgToSend);
        }
        return;
    }

    public void StartTheMatch()
    {
        isTheMatchStarted = true;

    }

    public void SendToAllClient(String msg)
    {
        for (UserPlayer redPlayer : redFaction)
        {
            PlayerMessageWriter().WriteMessage(redPlayer, msg);
        }
        for (UserPlayer bluePlayer : blueFaction)
        {
            PlayerMessageWriter().WriteMessage(bluePlayer, msg);
        }
        return;
    }

}
