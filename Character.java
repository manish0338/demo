import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.net.*;
import java.io.*;

/**
 * Write a description of class Character here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Character extends Actor
{
    /**
     * Act - do whatever the Character wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        move();
    }
    
    public void move() {
        int y = getY();
        int x = getX();
        
        if(Greenfoot.isKeyDown("W")) y--;
        if(Greenfoot.isKeyDown("S")) y++;
        if(Greenfoot.isKeyDown("A")) x--;
        if(Greenfoot.isKeyDown("D")) x++;
        
        setLocation(x,y);
        try {
            String serverName = "10.0.0.74";
            int port = Integer.parseInt("5000");
            Socket client = new Socket(serverName, port);
            
            OutputStream outToServer = client.getOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outToServer);
            XY xy = new XY();
            xy.x = x;
            xy.y = y;
            os.writeObject(xy);
            client.close();
        }catch(Exception e){}
        
        
    }
}
