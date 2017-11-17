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
        }
    }

}
