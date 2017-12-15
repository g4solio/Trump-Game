/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpgameserver;

/**
 *
 * @author d.gozzi
 */
public class Card
{
    public String seed;
    public int number;

    public Card(String seedGenerated, String numberGenerated)
    {
        seed = seedGenerated;
        number = Integer.parseInt(numberGenerated);
    }

    @Override
    public String toString()
    {
        //return super.toString(); //To change body of generated methods, choose Tools | Templates.
        String numberValue = number != 10 ? "0" + number : "" + number;
        return numberValue + seed;
    }
    
    public int getValue()
    {
        switch (number) {
            case 1:
                return 11;
            case 3:
                return 10;
            case 8:
                return 2;
            case 9:
                return 3;
            case 10:
                return 4;
            default:
                return 0;
        }
    }
    
    public static Card GetCardFromDeck(String card)
    {
        for (Card cardInArray : TrumpGameServer.gameMechanics.deck) 
        {
            if(cardInArray.toString().equals(card)) return cardInArray;
        }
        return null;
    }
}
