/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpgameserver;

import com.sun.javafx.css.CalculatedValue;
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
    public Card briscola;
    public ArrayList<Card> cardOnField;
    public int playerTurnIndex;
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
        playerTurnIndex = 0;
        TrumpGameServer.getInstance().SendGameMessage("HasToPlay:" + playerPlayOrder.get(playerTurnIndex).nickname);
        playerTurnIndex ++;
        
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
        briscola = deck[40];
        TrumpGameServer.getInstance().SendGameMessage("TableHasBeenSettedUp:"+briscola.toString());

    }

    private boolean NeedToPlay()
    {
////        int totalOwnedCard = 0;
////        for (Player player : redTeam)
////        {
////            totalOwnedCard += player.OwnedCards.size();
////        }
////        for (Player player : blueTeam)
////        {
////            totalOwnedCard += player.OwnedCards.size();
////        }
////        if (totalOwnedCard == 40)
////        {
////            return false;
////        }
        if(currentdeckIndex >= 39) return false;
        return true;
    }

    private void GiveCardInHand()
    {
        for (Player player : redTeam)
        {
            for (int i = 0; i < 3; i++)
            {
                player.hand[i] = deck[currentdeckIndex];
                TrumpGameServer.getInstance().SendGameMessage("CatchACard:"+deck[currentdeckIndex].toString() + ":" + player.nickname);

                currentdeckIndex++;
            }
        }
        for (Player player : blueTeam)
        {
            for (int i = 0; i < 3; i++)
            {
                player.hand[i] = deck[currentdeckIndex];
                TrumpGameServer.getInstance().SendGameMessage("CatchACard:"+deck[currentdeckIndex].toString() + ":" + player.nickname);

                currentdeckIndex++;
            }
        }
    }

    public void DropACard(Player player, Card card)
    {
        for (int i = 0; i < 3; i++)
        {
            if(player.hand[i].equals(card)) player.hand[i] = null;            
        }
        cardOnField.add(card);
        TrumpGameServer.getInstance().SendGameMessage("CardHasBeenDropped:"+player.nickname+":"+card.toString());
        boolean allPlayerPlayed = true;
        for (Player playerPlayed : playerPlayOrder) 
        {
            if(!playerPlayed.playedThisTurn) 
            {
                allPlayerPlayed = false;
                break;
            }
        }
        if(allPlayerPlayed) TakeCard();
        TrumpGameServer.getInstance().SendGameMessage("HasToPlay:" + playerPlayOrder.get(playerTurnIndex).nickname);
        playerTurnIndex ++;
    }
    
    public void DrawACard()
    {
        for (Player player : playerPlayOrder)
        {
            for (int i = 0; i < 3; i++)
            {
                if(player.hand[i] != null) continue;
                player.hand[i] = deck[currentdeckIndex];
                TrumpGameServer.getInstance().SendGameMessage("CatchACard:"+deck[currentdeckIndex].toString() + ":" + player.nickname);

                currentdeckIndex ++;
            }
        }
        if(currentdeckIndex >= 39 && redTeam.size() > 1)
        {
            TrumpGameServer.getInstance().SendGameMessage("ShowAlliesCards:" + redTeam.get(0).nickname + ":" + redTeam.get(1).hand[0] + ":" + redTeam.get(1).hand[1] + ":" + redTeam.get(1).hand[2]);
            TrumpGameServer.getInstance().SendGameMessage("ShowAlliesCards:" + redTeam.get(1).nickname + ":" + redTeam.get(0).hand[0] + ":" + redTeam.get(0).hand[1] + ":" + redTeam.get(0).hand[2]);
            TrumpGameServer.getInstance().SendGameMessage("ShowAlliesCards:" + blueTeam.get(0).nickname + ":" + blueTeam.get(1).hand[0] + ":" + blueTeam.get(1).hand[1] + ":" + blueTeam.get(1).hand[2]);
            TrumpGameServer.getInstance().SendGameMessage("ShowAlliesCards:" + blueTeam.get(1).nickname + ":" + blueTeam.get(0).hand[0] + ":" + blueTeam.get(0).hand[1] + ":" + blueTeam.get(0).hand[2]);
        }
    }

    public void TakeCard()
    {
        Card winningCard = cardOnField.get(0);
        for (Card card : cardOnField) 
        {
            if(!BiggerCard(winningCard, card)) winningCard = card;
        }
        int winnerIndex = cardOnField.indexOf(winningCard);
        Player winnerPlayer = playerPlayOrder.get(winnerIndex);
        for (Card card : cardOnField) 
        {
            winnerPlayer.OwnedCards.add(card);
        }
        TrumpGameServer.getInstance().SendGameMessage("PlayerPickedUP:" + winnerPlayer.nickname);

        cardOnField.clear();
        playerPlayOrder.clear();
        if(!NeedToPlay())
        {
            CalculateTotalScore();
            return;
        }
        if(redTeam.contains(winnerPlayer))
        {
            int playerIndex = redTeam.indexOf(winnerPlayer);
            playerPlayOrder.add(winnerPlayer);
            playerPlayOrder.add(blueTeam.get(playerIndex));
            if(redTeam.size() >= 2)
            {
                if(playerIndex == 1)
                {
                    playerIndex = 0;
                }
                else
                {
                    playerIndex = 1;
                }
                playerPlayOrder.add(redTeam.get(playerIndex));
                playerPlayOrder.add(blueTeam.get(playerIndex));
            }
        }
        if(blueTeam.contains(winnerPlayer))
        {
            int playerIndex = blueTeam.indexOf(winnerPlayer);
            playerPlayOrder.add(winnerPlayer);
            playerPlayOrder.add(redTeam.get(playerIndex));
            if(redTeam.size() >= 2)
            {
                if(playerIndex == 1)
                {
                    playerIndex = 0;
                }
                else
                {
                    playerIndex = 1;
                }
                playerPlayOrder.add(blueTeam.get(playerIndex));
                playerPlayOrder.add(redTeam.get(playerIndex));
            }
        }
        TrumpGameServer.getInstance().SendGameMessage("HasToPlay:" + playerPlayOrder.get(playerTurnIndex).nickname);
        playerTurnIndex ++;

    }
    
    public boolean BiggerCard(Card underCard, Card overCard)
    {
        if(underCard.seed.equals(overCard.seed))
        {
            return underCard.number > overCard.number ? true : false;
        }
        if(underCard.seed.equals(briscola.seed))
        {
            return true;
        }
        if(overCard.seed.equals(briscola.seed))
        {
            return false;
        }
        return true;
    }
    
    public void CalculateTotalScore()
    {
        int redScore = 0;
        for (Player player : redTeam) 
        {
            for (Card card : player.OwnedCards) 
            {
                redScore += card.getValue();
            }
        }       
        int blueScore = 0;
        for (Player player : blueTeam) 
        {
            for (Card card : player.OwnedCards) 
            {
                blueScore += card.getValue();
            }
        }
        TrumpGameServer.getInstance().SendGameMessage("TotalScore:RedFaction:" + redScore + ":BlueFaction:" + blueScore);
        return;
        
    }
    
}
