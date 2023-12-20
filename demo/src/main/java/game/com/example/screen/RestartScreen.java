package game.com.example.screen;
import java.awt.event.KeyEvent;

import game.asciiPanel.*;
public abstract class RestartScreen implements Screen,Runnable {

    @Override
    public abstract void displayOutput(AsciiPanel terminal);

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                PlayScreen t=new PlayScreen();
                t.run();
                return t;
            default:
                return this;
        }
    }
    @Override
    public void run()
    {
        
    }
}
