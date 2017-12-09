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

public class GameManager 
{
    
    private static GameManager instance = null;
    
    public GameManager getInstanceGameManager()
    {
        if(instance == null) new GameManager();
        return instance;
    }
    
}
