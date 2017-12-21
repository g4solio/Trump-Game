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
        System.out.println("Handling msg: " + msg);
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

                return;
            }
            if (message.contains("LoginRefused"))
            {

                return;
            }
            if (message.contains("AvailableLobbies"))
            {

                return;
            }
            if (message.contains("SignUpAccepted"))
            {

                return;
            }

            if (message.contains("SingUpRefused"))
            {

                return;
            }
            if (message.contains("LobbyCreatedSuccesfully"))
            {

                return;
            }
            if (message.contains("PlayerJoinedLobby"))
            {

                return;
            }
            if (message.contains("PlayerAbandonedLobby"))
            {
                return;
            }
            if (message.contains("LobbyHasBeenClosed"))
            {

                return;
            }
            if (message.contains("FactionChangedSuccesfully"))
            {

                return;
            }
            if (message.contains("ErrorChangingFaction"))
            {

                return;
            }
            if (message.contains("ChangeFactions"))
            {

                return;
            }
            if (message.contains("GameHasBeenStarted"))
            {

                return;
            }
            if (message.contains("ReadyToPlay"))
            {

                return;
            }
            if (message.contains("NotReadyToPlay"))
            {

                return;
            }
            if (message.contains("MatchStarted"))
            {

                return;
            }
        }
    }
}
