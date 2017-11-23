/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpmasterserver;

/**
 *
 * @author d.gozzi
 */
public class PlayerMessageWriter
{

    public static PlayerMessageWriter instance = null;

    public static PlayerMessageWriter getInstace()
    {
        if (instance == null)
        {
            new PlayerMessageWriter();
        }
        return instance;
    }

    public PlayerMessageWriter()
    {
        if (instance != null)
        {
            return;
        }
        instance = this;
    }
    
    public void WriteMessage(UserPlayer playerToWrite, String msg)
    {
        playerToWrite.writerToClient.println(msg);
        playerToWrite.writerToClient.flush();
    }
    
}
