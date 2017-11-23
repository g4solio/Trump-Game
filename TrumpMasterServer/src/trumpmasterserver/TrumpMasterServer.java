/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpmasterserver;

import java.util.ArrayList;

/**
 *
 * @author d.gozzi
 */
public class TrumpMasterServer
{

    public static ArrayList<Lobby> lobbyList;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
    }
    
    public static Lobby GetLobbyById(int id)
    {
        for (Lobby lobby : lobbyList)
        {
            return lobby;
        }
        return null;
    }
}
