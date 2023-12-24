package game.com.example.screen;
import java.awt.event.KeyEvent;

import game.asciiPanel.*;
public abstract class RestartScreen implements Screen {

    @Override
    public abstract void displayOutput(AsciiPanel terminal);

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                PlayScreen t=new PlayScreen();
                return t;
            case KeyEvent.VK_1:
                OnlineScreen o=new OnlineScreen(1);
                return o;
            case KeyEvent.VK_2:
                OnlineScreen e=new OnlineScreen(0);
                return e;
            default:
                return this;
        }
    }
}
