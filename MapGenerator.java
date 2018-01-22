package MatthewYuen;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

//Creates the map for each level
public class MapGenerator
{
   public int map[][];
   public int brickWidth;
   public int brickHeight;
   public String []type;
   public int fall = 1;
   public PowerUp [][]pU;
   public int row;
   public int col;
   public boolean p = false;
   
   //Creates the map layout and locates power ups in random locations depending on the level
   public MapGenerator(int level)
   {
      if(level == 1)
      {
         row = 2;
         col = 9;
      }
      else if(level == 2)
      {
         row = 3;
         col = 9;
      }
      else if(level == 3)
      {
         row = 4;
         col = 9;
      }
      else if(level == 4)
      {
         row = 5;
         col = 9;
      }
      else if(level == 5)
      {
         row = 5;
         col = 9;
      }
   
      map = new int[row][col];
      type = new String[]{"", "LIFE", "CATCH", "GUN", "LONG", "SLOW", "WRAP", "FLIP"};
      pU = new PowerUp[row][col];
      
      brickWidth = 850/col;
      brickHeight = (row*50)/row;
      
      for(int i = 0; i<map.length; i++)
         for(int j = 0; j<map[0].length; j++)
         {
            //1-3 means the brick has not been broken, 0 means it's broken
            map[i][j]=3;
            
            //Randomizing power ups
            if(level == 1)
               pU[i][j] = new PowerUp (j * brickWidth + 75 + brickWidth/3,i * brickHeight + 120+brickHeight/3,(int)(Math.random()*50+1));
            else if(level == 2)
               pU[i][j] = new PowerUp (j * brickWidth + 75 + brickWidth/3,i * brickHeight + 120+brickHeight/3,(int)(Math.random()*40+1));
            else if(level == 3)
               pU[i][j] = new PowerUp (j * brickWidth + 75 + brickWidth/3,i * brickHeight + 120+brickHeight/3,(int)(Math.random()*35+1));
            else if(level == 4)
               pU[i][j] = new PowerUp (j * brickWidth + 75 + brickWidth/3,i * brickHeight + 120+brickHeight/3,(int)(Math.random()*30+1));
            else if(level == 5)
               pU[i][j] = new PowerUp (j * brickWidth + 75 + brickWidth/3,i * brickHeight + 120+brickHeight/3,(int)(Math.random()*25+1));
              
         }
   }
   
   //Drawing the map and powerups
   public void draw(Graphics2D g)
   {      
      for(int i = map.length-1; i>=0; i--)
         for(int j = map[0].length-1; j>=0; j--)
         {
            if(map[i][j]>0)
            {
               //Drawing hidden powerups
               g.setColor(Color.WHITE);
               g.setFont(new Font("Serif", Font.BOLD, 25)); 
               pU[i][j].draw((Graphics2D)g);    
               
               //Drawing blocks
               if(map[i][j]==3)
                  g.setColor(Color.RED);
               else if(map[i][j]==2)
                  g.setColor(new Color(150,0,0));
               else if(map[i][j]==1)
                  g.setColor(new Color(75,0,0));
               g.fillRect(j * brickWidth + 75, i * brickHeight + 120, brickWidth, brickHeight);
               
               //Borders around blocks
               g.setStroke(new BasicStroke(3));
               g.setColor(Color.BLACK);
               g.drawRect(j * brickWidth + 75, i * brickHeight + 120, brickWidth, brickHeight);
            }
            //If brick is broken, power up is revealed and falls down
            else
            {
               pU[i][j].draw((Graphics2D)g);
            
               if(!p)
                  pU[i][j].y++;
            }
         }
   }
   
   
   //To set the type of brick -> 3 - bright red, 2 - red, 1 - dark red, 0 - broken
   public void setBrickValue(int value, int row, int col)
   {
      map[row][col]=value;
   }
}