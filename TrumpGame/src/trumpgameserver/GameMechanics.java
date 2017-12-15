/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpgameserver;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author daddi
 */
public class GameMechanics
{

    public ArrayList<Player> redTeam;
    public ArrayList<Player> blueTeam;
    public Card[] deck;
    public int currentdeckIndex;
    public ArrayList<Player> playerPlayOrder;
    public Card biscola;
    public ArrayList<Card> cardOnField;
    public GameMechanics()
    {
        redTeam = new ArrayList<>();
        blueTeam = new ArrayList<>();
        playerPlayOrder = new ArrayList<>();
        deck = new Card[40];

        
        AllocateCard();
    }

    public void AllocateCard()
    {
        String[] seeds =
        {
            "G", "W", "C", "S"
        };
        String[] numbers =
        {
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10"
        };
        int deckIndex = 0;
        for (String seed : seeds)
        {
            for (String number : numbers)
            {
                deck[deckIndex] = new Card(number, seed);
            }
        }
    }

    public void StartMatch()
    {
        ShuffleDeck();
        GiveCardInHand();
        for (int i = 0; i < redTeam.size(); i++)
        {
            playerPlayOrder.add( i * 2, redTeam.get(i));
            
        }
        for (int i = 0; i < blueTeam.size(); i++)
        {
            playerPlayOrder.add((i + 1) * 2 - 1 , redTeam.get(i));           
        }
//        while (NeedToPlay())
//        {
//            
//            DropACard();
//        }
    }

    private Random myRandom = null;

    public Random GetRandom()
    {
        if (myRandom == null)
        {
            myRandom = new Random();
        }
        return myRandom;
    }

    public void ShuffleDeck()
    {
        Card cardForSwitch;
        for (int i = 0; i < deck.length; i++)
        {

            int positionCard1 = GetRandom().nextInt(40);
            int positionCard2 = GetRandom().nextInt(40);
            cardForSwitch = deck[positionCard1];
            deck[positionCard1] = deck[positionCard2];
            deck[positionCard2] = cardForSwitch;

        }
        currentdeckIndex = 0;
        biscola = deck[39];
    }

    private boolean NeedToPlay()
    {
        int totalOwnedCard = 0;
        for (Player player : redTeam)
        {
            totalOwnedCard += player.OwnedCards.size();
        }
        for (Player player : blueTeam)
        {
            totalOwnedCard += player.OwnedCards.size();
        }
        if (totalOwnedCard == 40)
        {
            return false;
        }
        return true;
    }

    private void GiveCardInHand()
    {
        for (Player player : redTeam)
        {
            for (int i = 0; i < 3; i++)
            {
                player.hand[i] = deck[currentdeckIndex];
                currentdeckIndex++;
            }
        }
        for (Player player : blueTeam)
        {
            for (int i = 0; i < 3; i++)
            {
                player.hand[i] = deck[currentdeckIndex];
                currentdeckIndex++;
            }
        }
    }

    private void DropACard(Player player, Card card)
    {
        for (int i = 0; i < 3; i++)
        {
            if(player.hand[i].equals(card)) player.hand[i] = null;            
        }
        cardOnField.add(card);
        TrumpGameServer.getInstance().SendGameMessage("CardHasBeenDroppe:"+player.nickname+":"+card.toString());
    }
    
    public void DrawACard()
    {
        for (Player player : playerPlayOrder)
        {
            for (int i = 0; i < 3; i++)
            {
                if(player.hand[i] != null) continue;
                player.hand[i] = deck[currentdeckIndex];
                currentdeckIndex ++;
            }
        }
    }

    public void TakeCard()
    {
        
    }
    
}
