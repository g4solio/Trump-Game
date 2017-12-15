/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpgameserver;

import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author d.gozzi
 */
public class Player extends Thread
{

    public Card[] hand;
    public Socket playerSocket;
    ArrayList<Card> OwnedCards;
    public String nickname;

    public Player(Socket socket)
    {
        hand = new Card[3];
        playerSocket = socket;
        OwnedCards = new ArrayList<>();
        
    }
    
   

}
