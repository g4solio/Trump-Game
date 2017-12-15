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
    public String number;

    public Card(String seedGenerated, String numberGenerated)
    {
        seed = seedGenerated;
        number = numberGenerated;
    }

    @Override
    public String toString()
    {
        //return super.toString(); //To change body of generated methods, choose Tools | Templates.
        return number + seed;
    }
    
    
}
