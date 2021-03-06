/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpgameserver;

/**
 *
 * @author m.de stefano
 */
public class ServerMessageHandler
{

    public static ServerMessageHandler instance = null;

    public static ServerMessageHandler getInstace()
    {
        if (instance == null)
        {
            new ServerMessageHandler();
        }
        return instance;
    }

    public ServerMessageHandler()
    {
        if (instance != null)
        {
            return;
        }
        instance = this;
    }

    public void HandleMessage(String msg)
    {
        if (msg.equals("") || msg.equals(" ") || msg == null)
        {
            return;
        }
        System.out.println("Handling msg: " + msg);
        String[] splittedMsg = msg.split(">", 2);

        String tipeOfMessage = splittedMsg[0];
        String message = splittedMsg[1];

        if (tipeOfMessage.contains("PlayerSettings"))
        {

            /////////
            if (message.contains("MatchStarted"))
            {
                TrumpGameServer.gameMechanics.StartMatch();
                return;
            }
            if (message.contains("ListPlayer"))
            {
                String[] metaMessage = message.split(":");
                TrumpGameServer.gameMechanics.redTeam.clear();
                TrumpGameServer.gameMechanics.blueTeam.clear();
                for (int i = 1; i < metaMessage.length - 1; i += 2)
                {
                    if(metaMessage[i+1].equals("RedFaction")) TrumpGameServer.gameMechanics.redTeam.add(new Player(metaMessage[i]));
                    else TrumpGameServer.gameMechanics.blueTeam.add(new Player(metaMessage[i]));
                }
                return;
            }
            if (message.contains("ChangeFactions"))
            {
                String[] metaMessage = message.split(":", 3);
                if(metaMessage[2].equals("RedFaction"))
                {
                    for (int i = 0; i < TrumpGameServer.gameMechanics.blueTeam.size(); i++) 
                    {
                        Player player = TrumpGameServer.gameMechanics.blueTeam.get(i);
                        if(player.nickname.equals(metaMessage[1])) 
                        {
                            TrumpGameServer.gameMechanics.blueTeam.remove(player);
                            TrumpGameServer.gameMechanics.redTeam.add(player);
                            return;
                        }                   
                    }
                }
                for (int i = 0; i < TrumpGameServer.gameMechanics.redTeam.size(); i++) 
                    {
                        Player player = TrumpGameServer.gameMechanics.redTeam.get(i);
                        if(player.nickname.equals(metaMessage[1])) 
                        {
                            TrumpGameServer.gameMechanics.redTeam.remove(player);
                            TrumpGameServer.gameMechanics.blueTeam.add(player);
                            return;
                        }                   
                    }
                return;
            }
            if(message.contains("LobbyHasBeenClosed"))
            {
                TrumpGameServer.getInstance().Close();
                return;
            }
        }
        if (tipeOfMessage.contains("GameInteraction"))
        {
            if (message.contains("CardHasBeenDropped"))
            {
                System.out.println("DroppingCardOnServer");
                String[] metaMessage = message.split(":", 3);
                for (Player player : TrumpGameServer.gameMechanics.playerPlayOrder) 
                {
                    if(player.nickname.equals(metaMessage[1])) TrumpGameServer.gameMechanics.DropACard(player, Card.GetCardFromDeck(metaMessage[2]));
                }
            }
        }
    }
}
