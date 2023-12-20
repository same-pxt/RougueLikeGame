package game.com.example.screen;
import java.awt.event.KeyEvent;

import game.asciiPanel.*;
public interface Screen {
    public void displayOutput(AsciiPanel terminal);

    public Screen respondToUserInput(KeyEvent key);
}


