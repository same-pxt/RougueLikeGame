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
            default:
                return this;
        }
    }
}
