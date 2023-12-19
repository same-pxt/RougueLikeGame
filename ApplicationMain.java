 import java.awt.event.KeyEvent;
 import java.awt.event.KeyListener;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
 import javax.swing.JButton;

 import  java.util.Timer;
import java.util.TimerTask;

 import asciiPanel.AsciiFont;
 import asciiPanel.AsciiPanel;
 import screen.Screen;
 import screen.StartScreen;

 
 

 public class ApplicationMain extends JFrame implements KeyListener {
 
     private AsciiPanel terminal;
     private Screen screen;
     private Timer timer;// 计时器，固定频率刷新
     public ApplicationMain() {
         super();
         terminal = new AsciiPanel(30, 35, AsciiFont.CP437_16x16);
         add(terminal);
         pack();//自适应大小
         screen = new StartScreen();
         addKeyListener(this);

         paintByTimer();
     }
 
     @Override
     public void repaint() {
         terminal.clear();
         screen.displayOutput(terminal);
         super.repaint();
     }
 
     /**
      *
      * @param e
      */
     public void keyPressed(KeyEvent e) {
         screen = screen.respondToUserInput(e);
         repaint();
     }
 
     /**
      *
      * @param e
      */
     public void keyReleased(KeyEvent e) {
     }
 
     /**
      *
      * @param e
      */
     public void keyTyped(KeyEvent e) {
     }

     private void paintByTimer(){
        Runnable runnable = new Runnable(){
            @Override
            public void run(){
                repaint();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 300, TimeUnit.MILLISECONDS);
    }
     public static void main(String[] args) {
         ApplicationMain app = new ApplicationMain();
         app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         app.setVisible(true);
     }
 
 }
 