import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ele here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ele extends Actor
{
    /**
     * Act - do whatever the Ele wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    boolean owner = true;
    
    public Ele(){
        if(owner==true){
            
        }
        
        try{
        Thread t = new Client();
        t.start();}catch(Exception e){e.printStackTrace();}
    }
    
    public void act() 
    {
        if(owner==true){
            move();
        }else{
           int y = Client.y;
           int x = Client.x;
        
           setLocation(x,y);
        }
        
    }
    
    public void move(){
        int x = getX();
        int y = getY();
        
        if(Greenfoot.isKeyDown("W")) y -= 2;
        if(Greenfoot.isKeyDown("S")) y += 2;
        if(Greenfoot.isKeyDown("A")) x -= 2;
        if(Greenfoot.isKeyDown("D")) x += 2;
        
        setLocation(x,y);
        
        Sender.sendData(x,y);
        
    }
    
    
}
