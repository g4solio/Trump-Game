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
public class MessageHandler {

    public static MessageHandler instance = null;

    public static MessageHandler getInstace() {
        if (instance == null) {
            new MessageHandler();
        }
        return instance;
    }

    public MessageHandler() {
        if (instance != null) {
            return;
        }
        instance = this;
    }

    public void HandleMessage(String msg) {
        if (msg.equals("") || msg.equals(" ") || msg == null) {
            return;
        }
        System.out.println("Handling msg: " + msg);
        String[] splittedMsg = msg.split(">", 2);

        String tipeOfMessage = splittedMsg[0];
        String message = splittedMsg[1];
        if (tipeOfMessage.contains("Communication")) {

            return;
        }
        if (tipeOfMessage.contains("PlayerSettings")) {

            /////////
            if (message.equals("LoginAccepted")) {

                return;
            }
            if (message.equals("LoginRefused")) {

                return;
            }
            if (message.equals("AvailableLobbies")) {

                return;
            }
            if (message.equals("SignUpAccepted")) {

                return;
            }

            if (message.equals("SingUpRefused")) {

                return;
            }
            if (message.equals("LobbyCreatedSuccesfully")) {

                return;
            }
            if (message.equals("PlayerJoinedLobby")) {

                return;
            }
            if (message.equals("PlayerAbandonedLobby")) {
                return;
            }
            if (message.equals("LobbyHasBeenClosed")) {

                return;
            }
            if (message.equals("FactionChangedSuccesfully")) {

                return;
            }
            if (message.equals("ErrorChangingFaction")) {

                return;
            }
            if (message.equals("ChangeFactions")) {

                return;
            }
            if (message.equals("GameHasBeenStarted")) {

                return;
            }
            if (message.equals("ReadyToPlay")) {

                return;
            }
            if (message.equals("NotReadyToPlay")) {

                return;
            }
            if (message.equals("MatchStarted")) {

                return;
            }
        }
    }
}
