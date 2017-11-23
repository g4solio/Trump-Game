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
public class UserHandler
{

    public static UserHandler instance = null;
    public ArrayList<String> registeredUserName;
    public ArrayList<String> registeredPassword;
    public final String fileLocation = "";

    public UserHandler getInstance()
    {
        if (instance == null)
        {
            new UserHandler();
        }
        return instance;
    }

    public UserHandler()
    {

        if (instance != null)
        {
            return;
        }

        instance = this;
        registeredUserName = new ArrayList<>();
        registeredPassword = new ArrayList<>();
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
                registeredPassword.add(String.valueOf(username));
            }
            
            JSONArray passwords = (JSONArray) jSonObject.get("Passwords");
            
            for (Object password : passwords)
            {
                registeredPassword.add(String.valueOf(password));
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
        JSONArray usersList = new JSONArray(registeredUserName);
        JSONArray passwordList = new JSONArray(registeredPassword);
        jSonObject.put("Users", usersList);
        jSonObject.put("Passwords", passwordList);
        
        
        try
        {
            FileWriter jsonFileWriter = new FileWriter(fileLocation);
            jsonFileWriter.write(jSonObject.toJSONString());
            jsonFileWriter.flush();
            
        } catch (IOException ex)
        {
            System.out.println("Error Writing Json " + ex);
        }
    }
}
