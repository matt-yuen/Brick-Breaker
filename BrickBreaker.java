package MatthewYuen;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.applet.*;
import java.net.*;

/*********************************************************
*  Name: Matthew Yuen                                    *
*  Course: ICS 4U                                        *
*  Assignment: Summative                                 *
*  Purpose: To create a game of brick breaker            *
*  Due Date: May 26, 2017                                *
*********************************************************/

class BrickBreaker extends JPanel implements KeyListener, ActionListener
{
   static int height = 1000;
   static int width = 1000;
   static int screen = 0; //0 - main menu, 1 - play, 2 - instructions, 3 - settings, 4/5/6/7/8 - level 1 to 5 singleplayer 
   static JFrame frame = new JFrame("Brick Breaker");
   Drawing draw = new Drawing();
   static int paddlex=450;
   static int paddley=850;
   static int l=80;
   static int h=20;
   static boolean left = false;
   static boolean right = false;
   static Color c=Color.RED;
   boolean sound = true;
   static boolean exit = false;
   private Timer timer;   
   private int delay = 5;
   private int ballx = 480;
   private int bally = 830;
   private int ballxdir = 2;
   private int ballydir = -3;
   private boolean play = false;
   public int totalBricks = 18;
   public MapGenerator mg;
   private int score = 0;
   private int lives = 2;
   private boolean gameOver=false;
   private boolean win = false;
   private boolean ballOnPaddle = true;
   private int level = 1;
   private boolean powerUpActivated = false;
   private int powerUpActivatedRow;
   private int powerUpActivatedCol;
   public long powerUpTime;
   public boolean shoot = false;
   public int bulletX;
   public int bulletY;
   public int bulletW = 5;
   public int bulletL = 15;
   public boolean pause = false;
   public AudioClip music;
   
   //Constructor - sets up the JFrame, adds music
   public BrickBreaker()
   {
      frame.setSize(width,height);
      frame.add(draw);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.addMouseListener(new action());
      frame.addKeyListener(this);
      frame.setResizable(false);
      frame.setLocationRelativeTo(null);
      timer = new Timer(delay, this);
      timer.start();
      mg = new MapGenerator(level);
      frame.setFocusable(true);
      frame.setVisible(true);
      
      //Music
      try
      {
         music = Applet.newAudioClip (new URL ("file:music.wav"));
      }
      catch (Exception a)
      {
         System.out.println (a);
      }
      music.loop();  
   }
        
   //Main method - creates a BrickBreaker object                   
   public static void main(String [] args)
   {  
      BrickBreaker game = new BrickBreaker();
      game.setFocusable(true);  
   }
   
   //Class for all the drawing and output
   public class Drawing extends JComponent
   {
      //Drawing everything on the screen
      public void paint(Graphics g)
      {
         Graphics2D g2 = (Graphics2D) g;
             
         if (screen == 0) //Main menu
         {                     
            g.setColor(Color.BLACK);
            g.drawRect(0,0,1000,800);
            frame.getContentPane().setBackground(Color.BLACK);
         
            //Titles
            g.setFont(new Font("Verdana", Font.BOLD, 90));
            g.setColor(Color.RED);
            g.drawString("Brick Breaker", width/6, height/4);
            g.setFont(new Font("Verdana", Font.PLAIN, 60));
            g.drawString("Play", width/2-65, height/3+35);
            g.drawString("Instructions", width/2-180, height/3+120);
            g.drawString("Settings", width/2-125, height/3+205);
            
            //Hand drawn paddle
            g.setColor(c);
            g.fillRect(paddlex,paddley,l,h);
            g.fillOval(paddlex-5,paddley,h,h);
            g.fillOval(paddlex+l-15,paddley,h,h);
         }
         else if(screen == 1) //Select level
         {
            frame.getContentPane().setBackground(Color.BLACK);
            
            g.setColor(Color.RED);
            
            //Back
            g.setFont(new Font("Verdana", Font.BOLD, 35));
            g.drawString("Back",15,40);
            
            //DRAWING BORDER
            g.drawLine(100,50,275,50);
            g.drawArc(height/32,50, 150, 150, 90, 90);
            g.drawLine(height/32,125,height/32,845);
            g.drawArc(height/32,770, 150, 150, 180, 90);
            g.drawLine(100,920, 880,920);
            g.drawArc(801,770, 150, 150, 270, 90);
            g.drawLine(951,125,951,845);
            g.drawArc(801,50, 150, 150, 0, 90);
            g.drawLine(720,50,880,50);
            
            //Title
            g.setFont(new Font("Verdana", Font.PLAIN, 60));
            g.drawString("Select a Level", width/2-210, 75);
            
            //Level subtitles
            g.drawString("Level 1", width/2-115, 250);
            g.drawString("Level 2", width/2-115, 350);
            g.drawString("Level 3", width/2-115, 450);
            g.drawString("Level 4", width/2-115, 550);
            g.drawString("Level 5", width/2-115, 650);
         }
         else if (screen == 2) //Instructions
         {
            frame.getContentPane().setBackground(Color.BLACK);
            g.setColor(Color.RED);
            
            //Back
            g.setFont(new Font("Verdana", Font.BOLD, 35));
            g.drawString("Back",15,40);
            
            //DRAWING BORDER
            g.drawLine(100,50,310,50);
            g.drawArc(height/32,50, 150, 150, 90, 90);
            g.drawLine(height/32,125,height/32,845);
            g.drawArc(height/32,770, 150, 150, 180, 90);
            g.drawLine(100,920, 880,920);
            g.drawArc(801,770, 150, 150, 270, 90);
            g.drawLine(951,125,951,845);
            g.drawArc(801,50, 150, 150, 0, 90);
            g.drawLine(690,50,880,50);
            
            //Title
            g.setFont(new Font("Verdana", Font.PLAIN, 60));
            g.drawString("Instructions", width/2-180, 75);
            
            //Subtitles
            g.setFont(new Font("Verdana", Font.PLAIN, 45));
            g.drawString("Controls", 75, 145);
            g.drawString("Rules",75,300);
            g.drawString("Power Ups",75,510);
            
            //Controls
            g.setFont(new Font("Verdana", Font.PLAIN, 25));
            g.drawString("-To move the paddle, press and hold the left or right arrow key.", 100, 185);
            g.drawString("-To release the ball, press the spacebar", 100, 215);
            g.drawString("-To shoot the laser, press the spacebar.", 100, 245);
            
            //Rules
            g.setFont(new Font("Verdana", Font.PLAIN, 25));
            g.drawString("The objective of the game is break all the bricks while not letting",100,330);
            g.drawString("the ball(s) drop by deflecting them with the paddle. When all the",100,360);
            g.drawString("bricks have been broken, you may advance to the next level. If",100,390);
            g.drawString("the ball(s) hit the bottom of the screen, you lose. Power ups are",100,420);
            g.drawString("hidden behind blocks. They last for 10 seconds.",100,450);
            
            //Power Ups
            //Life
            g.setColor(Color.BLUE);
            g.fillRect(100,540,30,20);
            g.fillOval(100-10,540,20,20);
            g.fillOval(100+19,540,20,20); 
            //Catch
            g.setColor(Color.BLUE);
            g.fillRect(100,590,30,20);
            g.fillOval(100-10,590,20,20);
            g.fillOval(100+19,590,20,20); 
            //Gun
            g.setColor(Color.BLUE);
            g.fillRect(100,640,30,20);
            g.fillOval(100-10,640,20,20);
            g.fillOval(100+19,640,20,20);
            //Long
            g.setColor(Color.BLUE);
            g.fillRect(100,690,30,20);
            g.fillOval(100-10,690,20,20);
            g.fillOval(100+19,690,20,20);
            //Slow
            g.setColor(Color.BLUE);
            g.fillRect(100,740,30,20);
            g.fillOval(100-10,740,20,20);
            g.fillOval(100+19,740,20,20);
            //Wrap
            g.setColor(Color.BLUE);
            g.fillRect(100,790,30,20);
            g.fillOval(100-10,790,20,20);
            g.fillOval(100+19,790,20,20);
            //Flip  
            g.setColor(Color.BLUE);
            g.fillRect(100,840,30,20);
            g.fillOval(100-10,840,20,20);
            g.fillOval(100+19,840,20,20);
            
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Serif", Font.BOLD, 11));
            g.drawString("LIFE",100+5,540+15);
            g.drawString("CATCH",100-6,590+15);
            g.drawString("GUN",100+3,640+15);
            g.drawString("LONG",100-2,690+15);
            g.drawString("SLOW",100-2,740+15);
            g.drawString("WRAP",100-2,790+15);
            g.drawString("FLIP",100+4,840+15);
            
            g.setFont(new Font("Verdana", Font.PLAIN, 25));
            g.setColor(Color.RED);
            g.drawString("Gives an extra life",100+65,540+20);
            g.drawString("Catch and hold the ball",100+65,590+20);
            g.drawString("Gives you 3 bullets that damage bricks",100+65,640+20);
            g.drawString("Makes the paddle longer",100+65,690+20);
            g.drawString("Slows down the speed of the ball",100+65,740+20);
            g.drawString("Allows you to move paddle beyond the edges of the screen",100+65,790+20);
            g.drawString("Changes the direction of the paddle",100+65,840+20);
         }
         else if (screen == 3)//Settings
         {
            frame.getContentPane().setBackground(Color.BLACK);
            g.setColor(Color.RED);
            g.setFont(new Font("Verdana", Font.PLAIN, 60));            
            g.drawString("Settings", width/2-125, 75);
            
            //DRAWING BORDER
            g.drawLine(100,50,310,50);
            g.drawArc(height/32,50, 150, 150, 90, 90);
            g.drawLine(height/32,125,height/32,845);
            g.drawArc(height/32,770, 150, 150, 180, 90);
            g.drawLine(100,920, 880,920);
            g.drawArc(801,770, 150, 150, 270, 90);
            g.drawLine(951,125,951,845);
            g.drawArc(801,50, 150, 150, 0, 90);
            g.drawLine(690,50,880,50);
            
            //Subtitles
            g.setFont(new Font("Verdana", Font.PLAIN, 55));
            g.drawString("Colour", 100, 180);
            g.drawString("Sound", 100, 450);
            
            //Back
            g.setFont(new Font("Verdana", Font.BOLD, 35));
            g.drawString("Back",15,40);
            
            //Sound box
            g.drawRect(315,410,40,40);
            
            g.setColor(c);
            g.fillRect(335,150,l,h);
            g.fillOval(330,150,h,h);
            g.fillOval(400,150,h,h);
            
            g.setColor(Color.RED);
            g.setFont(new Font("Verdana", Font.PLAIN, 40));
            g.drawString("Red",100,300);
            g.drawRect(205,265,40,40);
            g.setColor(Color.BLUE);
            g.drawString("Blue",280,300);
            g.drawRect(400,265,40,40);
            g.setColor(Color.GREEN);
            g.drawString("Green",480,300);
            g.drawRect(630,265,40,40);
            g.setColor(Color.MAGENTA);
            g.drawString("Purple",700,300);
            g.drawRect(860,265,40,40);
            
            g.setColor(c);
            if(c == Color.RED)
            {
               g.drawLine(210,270,240,300);
               g.drawLine(210,300,240,270);
            }
            else if(c == Color.BLUE)
            {
               g.drawLine(405,270,435,300);
               g.drawLine(405,300,435,270);
            }
            else if(c == Color.GREEN)
            {
               g.drawLine(635,270,665,300);
               g.drawLine(635,300,665,270);
            }
            else if(c==Color.MAGENTA)
            {
               g.drawLine(865,270,895,300);
               g.drawLine(865,300,895,270);
            }
            
            g.setColor(Color.RED);
            if(sound)
            {
               g.drawLine(320,415,350,445);
               g.drawLine(320,445,350,415);
            }
         }
         else if(screen == 4 || screen == 5 ||screen == 6 ||screen == 7 ||screen == 8) //Singleplayer in game - level 1-5
         {   
            //Background
            frame.getContentPane().setBackground(Color.BLACK);
                        
            //Border
            g.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            g.drawRect(1,50,992,850);
            
            //Drawing bullets
            if(shoot)
            {
               g.setColor(Color.GREEN);
               g.fillRect(bulletX,bulletY,bulletW,bulletL);
            }
            
            //Paddle
            g.setColor(c);
            g.fillRect(paddlex,paddley,l,h);
            g.fillOval(paddlex-5,paddley,h,h);
            g.fillOval(paddlex+l-15,paddley,h,h);
            
            //Ball
            g.setColor(c);
            g.fillOval(ballx,bally,20,20);
            
            //Back
            g.setColor(Color.RED);
            g.setFont(new Font("Verdana", Font.BOLD, 35));
            g.drawString("Back",15,40);
            
            //Pause
            g.drawString("Pause",860,40);
            
            //Score
            g.setColor(Color.RED);
            g.setFont(new Font("Verdana", Font.BOLD, 35));
            g.drawString("Score: " + score,425,40);
            
            //PowerUp
            g.drawString("Power-Up: ",200,940);
            
            //Bullets
            g.drawString("Bullets: ", 500,940);
                    
            //Lives
            g.drawString("Lives: " + lives,800,940);
            
            //Level
            g.setColor(Color.WHITE);
            g.drawString("Level " + level,20,940);
                     
            if(gameOver)
            {
               g.setColor(Color.WHITE);
               g.setFont(new Font("Verdana", Font.BOLD, 75));
               g.drawString("GAME OVER",250,450);
               g.setFont(new Font("Verdana", Font.PLAIN, 35));
               g.drawString("Press Enter to restart",315,520);
            }
            
            if(win)
            {
               if(level == 5)
               {
                  g.setColor(Color.WHITE);
                  g.setFont(new Font("Verdana", Font.BOLD, 75));
                  g.drawString("YOU WIN!",300,450);
               }
               else
               {
                  g.setColor(Color.WHITE);
                  g.setFont(new Font("Verdana", Font.BOLD, 75));
                  g.drawString("LEVEL COMPLETE",130,450);
                  g.setFont(new Font("Verdana", Font.PLAIN, 30));
                  g.drawString("Press Enter to advance to the next level",200,520);
               }
            }
            
            //Map
            mg.draw((Graphics2D)g);
         }
         g.dispose();
      }
   }
   
   //Deals with the mechanics of the game, decides what to do for different actions
   public void actionPerformed(ActionEvent e)
   {
      timer.start();
      
      //Checking if power up timer is done
      if(mg.pU[powerUpActivatedRow][powerUpActivatedCol].type == 3)
      { 
         if(mg.pU[powerUpActivatedRow][powerUpActivatedCol].bullets == 0)
         {
            mg.pU[powerUpActivatedRow][powerUpActivatedCol].activated=false;
            powerUpActivated = false;
         }
      }
      else if(powerUpActivated && ((System.nanoTime()-powerUpTime)/1000000000) >= 10)
      {
         mg.pU[powerUpActivatedRow][powerUpActivatedCol].activated=false;
         powerUpActivated = false;
         
         //Changing length of paddle back to original
         l = 80;
      }
         
      if(play)
      {
         //Checking if ball intersects paddle
         if(new Rectangle(ballx,bally,20,20).intersects(new Rectangle(paddlex,paddley,l,h)))
         {
            bally=829;
            ballOnPaddle = true;
            if(powerUpActivated && mg.pU[powerUpActivatedRow][powerUpActivatedCol].type == 2)
            {
               bally=829;
               ballydir=-ballydir;
            }
            else            {
               if(ballydir > 0)
                  ballydir=-ballydir;
               ballOnPaddle = false;
            }
         }
         
         //Checking if ball or bullet intersects bricks
         A: for(int i = 0; i<mg.map.length; i++)
            for(int j = 0; j<mg.map[0].length; j++)
            {
               if(mg.map[i][j]>0)
               {
                  Rectangle brickRect = new Rectangle(j * mg.brickWidth + 75, i * mg.brickHeight + 120, mg.brickWidth, mg.brickHeight);
                  Rectangle ballRect = new Rectangle(ballx,bally,20,20);
                  if(ballRect.intersects(brickRect))
                  {
                     mg.setBrickValue(mg.map[i][j]-1,i,j);
                     if(mg.map[i][j]==0)
                     {
                        totalBricks--;
                        score += 25;
                     }
                     else
                     {
                        score += 10;
                     }
                     
                     if(ballx + 19 <= brickRect.x || ballx + 1 >= brickRect.x + brickRect.width)
                        ballxdir=-ballxdir;
                     else //if(bally + 1 <= brickRect.y + brickRect.height || bally + 19 >= brickRect.y)
                        ballydir=-ballydir;
                     break A;
                  }
                  
                  //Bullet intersection
                  if(new Rectangle(bulletX,bulletY,bulletW,bulletL).intersects(brickRect))
                  {
                     shoot = false;
                     mg.setBrickValue(mg.map[i][j]-1,i,j);
                     if(mg.map[i][j]==0)
                     {
                        totalBricks--;
                        score += 25;;
                     }
                     else
                     {
                        score += 10;
                     }
                  }
               }
               else
               {
                  
               }
            }
            
         //Activating power up
         for(int i = mg.map.length-1; i>=0; i--)
         {
            for(int j = mg.map[0].length-1; j>=0; j--)
            {
               if(mg.pU[i][j].type <= 7 && new Rectangle(mg.pU[i][j].x-10,mg.pU[i][j].y,59,20).intersects(new Rectangle(paddlex-5,paddley,l+10,h)))
               {
                  if(powerUpActivated)
                  {
                     mg.pU[powerUpActivatedRow][powerUpActivatedCol].activated = false;
                  }
                  mg.pU[i][j].activated = true;
                  powerUpActivated = true;
                  powerUpActivatedRow = i;
                  powerUpActivatedCol = j;
                  if(mg.pU[powerUpActivatedRow][powerUpActivatedCol].type != 3)
                     powerUpTime = System.nanoTime();
               }
            }
         }
         
         //Powerup effects
         if(powerUpActivated)
         {  
            //Life
            if(mg.pU[powerUpActivatedRow][powerUpActivatedCol].type == 1)
            {
               lives++;
               mg.pU[powerUpActivatedRow][powerUpActivatedCol].type=8;
               mg.pU[powerUpActivatedRow][powerUpActivatedCol].activated=false;
               powerUpActivated = false;
            }
            //Slow
            else if(mg.pU[powerUpActivatedRow][powerUpActivatedCol].type == 5)
            {
               if(ballydir < 0)
                  ballydir = -2;               
               else               
                  ballydir = 2;
               if(ballxdir < 0)
                  ballxdir = -1;
               else
                  ballxdir = 1;
            }
            //Long
            else if(mg.pU[powerUpActivatedRow][powerUpActivatedCol].type == 4)
            {
               l = 130;
            }
         }
         else
         {
            if(ballydir < 0)
               ballydir = -3;               
            else              
               ballydir = 3;
            if(ballxdir < 0)
               ballxdir = -2;
            else
               ballxdir = 2;
         }
         
                  
         //Stopping paddle at the edge of the screens if !Wrap
         if(mg.pU[powerUpActivatedRow][powerUpActivatedCol].type == 6 && mg.pU[powerUpActivatedRow][powerUpActivatedCol].activated)
         {
            if(paddlex-5 >= width)
            {
               int d = ballx - paddlex;
               paddlex = 0-l;
               if(ballOnPaddle)
                  ballx = paddlex + d; 
            }
            else if(paddlex <= 0-l-5)
            {
               int c = ballx - paddlex;
               paddlex = width + 5;
               if(ballOnPaddle)
                  ballx = paddlex + c;
            }         }
         else
         {
            if(paddlex >= width-l-13)
            {
               right=false;
               paddlex = width-l-13;
               if(ballOnPaddle && !powerUpActivated)
               {
                  ballx = paddlex+30;
               }
            }
            else if(paddlex <= 7)
            {
               left=false;
               paddlex = 7;
               draw.repaint();
               if(ballOnPaddle && !powerUpActivated)
                  ballx = paddlex+30;
            }
         }
         
         
         //Moving the paddle
         if(right)
         {
            paddlex += 3;
            
            if(ballOnPaddle)
               ballx += 3;
         }
         else if(left)
         {
            paddlex -= 3;
            
            if(ballOnPaddle)
               ballx -= 3;
         }
         
         if(!ballOnPaddle)
         {
         //Moving the ball
            ballx += ballxdir;
            bally += ballydir;
         }
         
         //Checking if ball hits the walls
         if(ballx <= 0 || ballx >= width-20)
         {
            ballxdir = -ballxdir;
         }
         if(bally <= 51)
         {
            ballydir = -ballydir;
         }
         
         //Checking if ball hits the bottom of the screen
         if(bally >= 875)
         {
            if(lives > 0)
            {
               //Reinitialize variables but don't reset blocks, player can play on until no more lives
               lives--;
               play=false;
               ballx = 480;
               bally = 830;
               paddlex=450;
               paddley=850;
               ballxdir = +2;
               ballydir = -3;
               score=0;
               gameOver = false;
               ballOnPaddle = true;
            }
            else
            {
               play = false;
               ballxdir = 0;
               ballydir = 0;
               gameOver = true;
               ballOnPaddle = true;
            }
         }
         
         //Checking if win
         if(totalBricks==0)
         {
            play = false;
            ballxdir = 0;
            ballydir = 0;
            win=true;
         }
         
         if(shoot)
         {
            bulletY -= 5;          
         }
      }
      draw.repaint();
   }
   
   //Class to control all mouse events
   public class action extends MouseAdapter
   {
      public void mousePressed(MouseEvent e)
      {
      }
   
      //Checks if user clicked on the screen and if in the right spot, decides what to do
      public void mouseReleased(MouseEvent e)
      {      
         if(screen == 0)
         {
            if(e.getX() > 445 && e.getX() < 565 && e.getY() > 350 && e.getY() < 410)
               screen = 1;
            else if(e.getX() > 325 && e.getX() < 695 && e.getY() > 445 && e.getY() < 490)
               screen = 2;
            else if(e.getX() > 385 && e.getX() < 635 && e.getY() > 525 && e.getY() < 580)
               screen = 3;
            draw.repaint();
         }
         else if(screen == 1)
         {
            //Back
            if(e.getX() > 22 && e.getX() < 110 && e.getY() > 45 && e.getY() < 70)
               screen = 0;
            else if(e.getX() > 385 && e.getX() < 600 && e.getY() > 235 && e.getY() < 285)
            {
               screen = 4;
               level = 1;
            }
            else if(e.getX() > 385 && e.getX() < 600 && e.getY() > 335 && e.getY() < 385)
            {
               screen = 5;
               level = 2;
            }
            else if(e.getX() > 385 && e.getX() < 600 && e.getY() > 435 && e.getY() < 485)
            {
               screen = 6;
               level = 3;
            }
            else if(e.getX() > 385 && e.getX() < 600 && e.getY() > 535 && e.getY() < 585)
            {
               screen = 7;
               level = 4;
            }
            else if(e.getX() > 385 && e.getX() < 600 && e.getY() > 635 && e.getY() < 685)
            {
               screen = 8;
               level = 5;
            }
            setTotalBricks();
            mg = new MapGenerator(level);
            draw.repaint();
         }
         else if(screen == 2)
         {
         //Back
            if(e.getX() > 22 && e.getX() < 110 && e.getY() > 45 && e.getY() < 70)
               screen = 0;
            draw.repaint();
         }
         else if(screen == 3)
         {
         //Colour
            if(e.getY() > 305 && e.getY() < 345)
            {
               if(e.getX() > 215 && e.getX() < 255)
                  c=Color.RED;
               else if(e.getX() > 405 && e.getX() < 445)
                  c=Color.BLUE;
               else if(e.getX() > 635 && e.getX() < 675)
                  c=Color.GREEN;
               else if(e.getX() > 865 && e.getX() < 905)
                  c=Color.MAGENTA;
            }
         
         //Sound
            if(e.getX() > 325 && e.getX() < 365 && e.getY() > 445 && e.getY() < 485)
               if(sound)
               {
                  sound = false;
                  music.stop();
               }
               else
               {
                  sound = true;
                  music.play();
                  music.loop();
               }
         
         //Back
            if(e.getX() > 22 && e.getX() < 110 && e.getY() > 45 && e.getY() < 70)
               screen = 0;
            draw.repaint();
         }      
         else if(screen == 4 || screen == 5 ||screen == 6||screen == 7 ||screen == 8)
         {
            if(e.getX() > 22 && e.getX() < 110 && e.getY() > 45 && e.getY() < 70)
            {
               screen = 0;
               
               //Reinitialize variables
               play=false;
               ballx = 480;
               bally = 830;
               ballxdir = +2;
               ballydir = -3;
               paddlex=450;
               paddley=850;
               score=0;
               setTotalBricks();
               lives=2;
               gameOver = false;
               win = false;
               ballOnPaddle = true;
               mg=new MapGenerator(level);
            }
            //Pause
            else if(e.getX() > 860 && e.getX() < 985 && e.getY() > 45 && e.getY() < 70)
            {
               pause = true;
               mg.p = true;
               play = false;
            }
            else if(pause && e.getX() > 0 && e.getX() < width && e.getY() > 0 && e.getY() < height)
            {
               pause = false;
               mg.p = false;
               play = true;
            }
            
            draw.repaint();
         }
      }
   
      public void mouseClicked(MouseEvent e){}
   
      public void mouseEntered(MouseEvent e){}
   
      public void mouseExited(MouseEvent e){}
   }
   
   //Decides what to do when the arrow keys are pressed
   public void keyPressed(KeyEvent e)
   {
         //Exit program if escape is clicked
      if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
      {
         System.exit(0);
      }
      
      if(screen == 4 || screen == 5 ||screen == 6||screen == 7 ||screen == 8)
      {
         //Checking if player launched ball
         if(e.getKeyCode() == KeyEvent.VK_SPACE)
         {
            if(ballOnPaddle)
            {
               ballOnPaddle = false;
               play = true;;
            }
            else if(powerUpActivated && mg.pU[powerUpActivatedRow][powerUpActivatedCol].type == 3 && !shoot)
            {
               shoot = true;
               bulletX = paddlex + l/2 - 3;
               bulletY = paddley;
               mg.pU[powerUpActivatedRow][powerUpActivatedCol].bullets--;
            }
         }
         
         //Checking if right arrow key
         else if(e.getKeyCode() == KeyEvent.VK_RIGHT && !gameOver && !win && !pause)
         {
            play=true;
         
            if(powerUpActivated && mg.pU[powerUpActivatedRow][powerUpActivatedCol].type == 7)
            {
               left = true;
            }
            else
            {
               right = true;
            }   
         }
         //Checking if left arrow key
         else if(e.getKeyCode() == KeyEvent.VK_LEFT && !gameOver && !win && !pause)
         {
            play = true;
            
            if(powerUpActivated && mg.pU[powerUpActivatedRow][powerUpActivatedCol].type == 7)
            {
               right = true;
            }
            else
            {
               left = true;
            }
            //}
         }
         else if(e.getKeyCode() == KeyEvent.VK_ENTER && gameOver)
         {
            if(!play)
            {  
            //Restart after losing-reinitialize variables
               play=true;
               ballx = 480;
               bally = 830;
               ballxdir = +2;
               ballydir = -3;
               paddlex=450;
               paddley=850;
               score=0;
               setTotalBricks();
               mg=new MapGenerator(level);
               lives=2;
               gameOver = false;
               draw.repaint();
               play=false;
               ballOnPaddle = true;
            }
         }
         //If completed the level
         else if(win && e.getKeyCode() == KeyEvent.VK_ENTER)
         {
            if(level >= 1 && level <=4)
            {
               level++;
               screen++;
               win = false;
               ballx = 480;
               bally = 830;
               ballxdir = +2;
               ballydir = -3;
               paddlex=450;
               paddley=850;
               score=0;
               setTotalBricks();
               mg=new MapGenerator(level);
               lives=2;
               gameOver = false;
               play=false;
               ballOnPaddle = true;
               // draw.repaint();
            }
         }
      }
   }
   
   //Decides what to do when arrow keys are released
   public void keyReleased(KeyEvent e)
   {
      //Moving the paddle left
      if(e.getKeyCode() == KeyEvent.VK_LEFT)
      {
         if(powerUpActivated && mg.pU[powerUpActivatedRow][powerUpActivatedCol].type == 7)
            right = false;
         else
            left=false;
      }
      //Moving the paddle right
      else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
      {
         if(powerUpActivated && mg.pU[powerUpActivatedRow][powerUpActivatedCol].type == 7)
            left = false;
         else
            right=false;
      }
   }
   
   public void keyTyped(KeyEvent e){}
   
   //Resetting the total number of bricks depending on the level
   public void setTotalBricks()
   {
      if(level == 1)
         totalBricks = 18;
      else if(level == 2)
         totalBricks = 27;
      else if(level == 3)
         totalBricks = 36;
      else if(level == 4)
         totalBricks = 45;
      else if(level == 5)
         totalBricks = 45;
   }
}