package game.com.example.screen;
import game.asciiPanel.*;
public class StartScreen extends RestartScreen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("welcome to our game.", 0, 0);
        terminal.write("Press ENTER to continue...", 0, 1);
    }

}