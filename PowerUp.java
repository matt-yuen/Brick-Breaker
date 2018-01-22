package MatthewYuen;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

//To deal with all the power ups
public class PowerUp
{
   public int x;
   public int y;
   public int l=30;
   public int h=20;
   public int type;
   public boolean activated=false;
   public int bullets = 3; 
   
   //Determines location of the power up and which type of power up it is
   public PowerUp(int x, int y, int type)
   {
      this.x = x;
      this.y = y;
      this.type = type;
   }
   
   //Sets the coordinates of the power up
   public void setCoordinates(int x, int y)
   {
      this.x = x;
      this.y = y;
   }
   
   //Draws the power ups
   public void draw(Graphics2D g)
   {
      if(type <= 7 && y<=875 && !activated)
      {
         g.setColor(Color.BLUE);
         g.fillRect(x,y,l,h);
         g.fillOval(x-10,y,h,h);
         g.fillOval(x+19,y,h,h);    
         g.setColor(Color.YELLOW);
      
         g.setFont(new Font("Serif", Font.BOLD, 11));
         //Checking which type of power up
         if(type == 1)
            g.drawString("LIFE",x+5,y+15);
         else if(type == 2)
            g.drawString("CATCH",x-6,y+15);
         else if(type == 3)
            g.drawString("GUN",x+3,y+15);
         else if(type == 4)
            g.drawString("LONG",x-2,y+15);
         else if(type == 5)
            g.drawString("SLOW",x-2,y+15);
         else if(type == 6)
            g.drawString("WRAP",x-2,y+15);
         else if(type == 7)
            g.drawString("FLIP",x+4,y+15);
      }
      else if(activated)
      {
         //Drawing active power up at bottom of the screen
         g.setColor(Color.BLUE);
         g.fillRect(425,920,l,h);
         g.fillOval(415,920,h,h);
         g.fillOval(444,920,h,h);    
         g.setColor(Color.YELLOW);
         
         g.setFont(new Font("Serif", Font.BOLD, 11));
         //Checking which type of power up
         if(type == 1)
            g.drawString("LIFE",430,935);
         else if(type == 2)
            g.drawString("CATCH",419,935);
         else if(type == 3)
         {
            g.drawString("GUN",428,935);
            g.setFont(new Font("Verdana", Font.BOLD, 35));
            g.setColor(Color.RED);
            g.drawString(""+bullets, 660,940);
         }
         else if(type == 4)
            g.drawString("LONG",423,935);
         else if(type == 5)
            g.drawString("SLOW",423,935);
         else if(type == 6)
            g.drawString("WRAP",423,935);
         else if(type == 7)
            g.drawString("FLIP",429,935);
      }
   }
}