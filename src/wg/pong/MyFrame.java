package wg.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author anagy
 */
public class MyFrame extends JFrame implements KeyListener {
    JPanel ball=new JPanel();
    JPanel user=new JPanel();
    JLabel scoreLabel1=new JLabel();
    JLabel scoreLabel2=new JLabel();
    JPanel enemy=new JPanel();
    int vectorX;
    int vectorY;
    int width=50;
    int height=50;
    int frameWidth=1920;
    int frameHeight=1080;
    int userWidth=25;
    int userHeight=150;
    int enemyWidth=25;
    int enemyHeight=150;
    int enemyScore=0;
    int userScore=0;
    int enemyVectorX=1;
     int enemyVectorY=1;
     boolean ability=false;
     boolean switch1=true;
     int counter=0;
     boolean boosted=false;
    
    MyFrame (){
        characters();
        initsFrame();
        timer();
    }
    
    void ballReset(){
     vectorX=-1;
     vectorY=-1;
     ball.setBounds(frameWidth/2,frameHeight/2,width,height);
        ball.setBackground(Color.red);
    }
    
    void characters(){
       
        ballReset();
        //user
        user.setSize(userWidth,userHeight);
        user.setBackground(Color.white);
        user.setLocation(100,frameHeight/2);
        
        //enemy
        enemy.setSize(enemyWidth,enemyHeight);
        enemy.setBackground(Color.white);
        enemy.setLocation(frameWidth-100,frameHeight/2);
        
        //scores
        Font font=new Font("Calibri",Font.PLAIN,35);
        scoreLabel1.setBounds(300,50,200,100);
    scoreLabel1.setForeground(Color.red);
        scoreLabel1.setFont(font);
        scoreLabel1.setText("Points: 0");
        
         scoreLabel2.setBounds(1200,50,200,100);
        scoreLabel2.setForeground(Color.red);
        scoreLabel2.setFont(font);
        scoreLabel2.setText("Points: 0");
    }
    
    void initsFrame(){
        this.setSize(frameWidth,frameHeight);
        this.setLayout(null);
        this.setUndecorated(true); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.black);

        this.setVisible(true);
        this.add(ball);
        this.add(user);
        this.add(enemy);
        this.add(scoreLabel1);
         this.add(scoreLabel2);
          this.addKeyListener(this);
    }

    void boost(){
      
        if (counter<3) {
            switch1=true;
            counter++;
        }else{
            switch1=false;
            counter=0;
        }
        if (switch1) {
            System.out.println("1"+vectorX);
            if (vectorX>0) {
                vectorX++;
              
            }else{
                vectorX--;
            }
        }else{
            System.out.println("0 "+vectorY);
            if (vectorY>0) {
                vectorY++;
              
            }else{
                vectorY--;
            }
        }
        
          boosted=true;
    }
    
    void timer(){
TimerTask task1 = new MyTimerTask1();
java.util.Timer timer = new java.util.Timer();
timer.schedule(task1, 0, 5);

TimerTask task2 = new MyTimerTask2();
timer.schedule(task2, 0, 1);
        } 

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==32) {
            user.setBackground(Color.blue);
            ability=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       if (e.getKeyCode()==32) {
            user.setBackground(Color.white);
            ability=false;
        }
    }
    
    class MyTimerTask1 extends TimerTask {
    public void run() {
        //ball movement
        ball.setLocation(ball.getX()+vectorX, ball.getY()+vectorY);
        
        
        //enemy ai
        if (ball.getY()>enemy.getY()) {
           enemy.setLocation(enemy.getX(), ball.getY()+enemyVectorY); 
        }else{
            enemy.setLocation(enemy.getX(), ball.getY()-enemyVectorY);
        }
        
        //down up border
        if (ball.getY()+height>frameHeight||ball.getY()<1) {
           vectorY*=-1; 
            
        }
        
        //getting points
        if (ball.getX()+width>frameWidth-25) {
          userScore++;
           scoreLabel1.setText("Points: "+userScore);
          ballReset();
        }
        
        if (ball.getX()<25) {
           enemyScore++;
           scoreLabel2.setText("Points: "+enemyScore);
           ballReset();
        }
        
         //boosted reset
                   if (ball.getX()>frameWidth/2-500&&ball.getX()<frameWidth/2+500) {
            boosted=false;
        }
        
    }
}
    
    class MyTimerTask2 extends TimerTask {
    public void run() {
     user.setLocation(user.getX(),(int) MouseInfo.getPointerInfo().getLocation().getY());
            //player collision
                    if (ball.getX()-1<user.getX()+userWidth&&ball.getY()+height>user.getY()&&ball.getY()<user.getY()+userHeight&&!boosted) {
                        if (ability) {
                            vectorY*=-1;
                        }
                      
                      vectorX*=-1;    
            boost();
                       }
  
//enemy collision
                 if (ball.getX()+width>enemy.getX()&&ball.getY()+height>enemy.getY()&&ball.getY()<enemy.getY()+enemyHeight&&!boosted) {
                        vectorX*=-1;
                        boost();
                       }  
                  
    }
}
    
    }