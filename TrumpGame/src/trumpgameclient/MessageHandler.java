/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpgameclient;

/**
 *
 * @author m.de stefano
 */
public class MessageHandler
{

    public static MessageHandler instance = null;

    public static MessageHandler getInstace()
    {
        if (instance == null)
        {
            new MessageHandler();
        }
        return instance;
    }

    public MessageHandler()
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
        //System.out.println("Handling msg: " + msg);
        String[] splittedMsg = msg.split(">", 2);

        String tipeOfMessage = splittedMsg[0];
        String message = splittedMsg[1];
        if (tipeOfMessage.contains("Communication"))
        {

            return;
        }
        if (tipeOfMessage.contains("PlayerSettings"))
        {

            /////////
            if (message.contains("LoginAccepted"))
            {
                System.out.println("You have been acceppted");
                return;
            }
            if (message.contains("LoginRefused"))
            {
                System.out.println("Yuo had problem login");
                return;
            }
            if (message.contains("AvailableLobbies"))
            {
                System.out.println(msg);
                return;
            }
            if (message.contains("SignUpAccepted"))
            {
                System.out.println("SignUp accepted");
                return;
            }

            if (message.contains("SingUpRefused"))
            {
                System.out.println("SignUp refused");
                return;
            }
            if (message.contains("LobbyCreatedSuccesfully"))
            {
                System.out.println("Loby created Succesfully");
                return;
            }
            if (message.contains("PlayerJoinedLobby"))
            {
                System.out.println(msg);
                return;
            }
            if (message.contains("PlayerAbandonedLobby"))
            {
                System.out.println(msg);
                return;
            }
            if (message.contains("LobbyHasBeenClosed"))
            {
                System.out.println("Lobby has been closed");
                return;
            }
            if (message.contains("FactionChangedSuccesfully"))
            {
                System.out.println("Faction Changed Succesfully");
                return;
            }
            if (message.contains("ErrorChangingFaction"))
            {
                System.out.println("Error changing Faction");
                return;
            }
            if (message.contains("ChangeFactions"))
            {
                System.out.println(msg);
                return;
            }
            if (message.contains("GameHasBeenStarted"))
            {
                System.out.println("Game Has been started");
                return;
            }
            if (message.contains("ReadyToPlay"))
            {
                System.out.println(msg);
                return;
            }
            if (message.contains("NotReadyToPlay"))
            {
                System.out.println(msg);
                return;
            }
            if (message.contains("MatchStarted"))
            {
                System.out.println("Match Started");
                return;
            }
        }
        if(tipeOfMessage.contains("GameInteraction"))
        {
            if(message.contains("CardHasBeenDropped"))
            {
                System.out.println(msg);
                return;
            }
            if(message.contains("PlayerPickedUP"))
            {             
                System.out.println(msg);
                return;
            }
            if(message.contains("TotalScore"))
            {
                System.out.println(msg);
                return;
            }
            if(message.contains("CatchACard"))
            {
                if(msg.contains(TrumpGameClient.nickName))
                    System.out.println(msg);
                return;
            }
            if(message.contains("TableHasBeenSettedUp"))
            {
                System.out.println(msg);
                return;
            }
            if(message.contains("HasToPlay"))
            {
                System.out.println(msg);
                return;
            }
        }
    }
}
