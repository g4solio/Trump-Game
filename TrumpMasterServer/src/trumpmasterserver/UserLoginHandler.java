/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trumpmasterserver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author daddi
 */
public class UserLoginHandler
{

    public static UserLoginHandler instance = null;
    public ArrayList<String> registeredUserNames;
    public ArrayList<String> registeredPasswords;
    public final String fileLocation = "LoginList.txt";

    public UserLoginHandler getInstance()
    {
        if (instance == null)
        {
            new UserLoginHandler();
        }
        return instance;
    }

    public UserLoginHandler()
    {

        if (instance != null)
        {
            return;
        }

        instance = this;
        registeredUserNames = new ArrayList<>();
        registeredPasswords = new ArrayList<>();
        LoadData();
    }

    // 0 -----> va bene
    // 1------> password Sbagliata
    // 2 -----> utente non esiste
    public int Login(String username, String password)
    {
        for (String registeredUserName : registeredUserNames)
        {
            if(!registeredUserName.equals(username)) continue;
            if(registeredPasswords.get(registeredUserNames.indexOf(registeredUserName)).equals(password)) return 0;
            return 1;
        }
        return 2;
    }
    
    // 0 -------> tutto bene
    // 1 -------> utente gia esistente
    public int SignUp(String username, String password)
    {
        for (String registeredUserName : registeredUserNames)
        {
            if(!registeredUserName.equals(username)) continue;
            return 1;
        }
        registeredUserNames.add(username);
        registeredPasswords.add(password);
        SaveData();
        return 0;
    }
    
    //Users
    //Password
    public void LoadData()
    {

        JSONParser jSonParser = new JSONParser();

        try
        {
            Object object = jSonParser.parse(new FileReader(fileLocation));
            JSONObject jSonObject = (JSONObject) object;
            
            JSONArray usernames = (JSONArray) jSonObject.get("Users");
            for (Object username : usernames)
            {
                registeredPasswords.add(String.valueOf(username));
            }
            
            JSONArray passwords = (JSONArray) jSonObject.get("Passwords");
            
            for (Object password : passwords)
            {
                registeredPasswords.add(String.valueOf(password));
            }
        } 
        catch (FileNotFoundException ex)
        {
            System.out.println("Error reading Json: " + ex);
        }
        catch (IOException ex)
        {
            System.out.println("Error reading Json: " + ex);
        }
        catch (ParseException ex)
        {
            System.out.println("Error parsing Json: " + ex);
        }

    }
    
    
    public void SaveData()
    {
        JSONObject jSonObject = new JSONObject();
        JSONArray usersList = new JSONArray(registeredUserNames);
        JSONArray passwordList = new JSONArray(registeredPasswords);
        jSonObject.put("Users", usersList);
        jSonObject.put("Passwords", passwordList);
        
        
        try
        {
            FileWriter jsonFileWriter = new FileWriter(fileLocation, false);
            jsonFileWriter.write(jSonObject.toJSONString());
            jsonFileWriter.flush();
            jsonFileWriter.close();
        } catch (IOException ex)
        {
            System.out.println("Error Writing Json " + ex);
        }
    }
}
