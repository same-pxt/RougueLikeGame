package game.com.example.screen;
import game.asciiPanel.*;
public class StartScreen extends RestartScreen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("welcome to our game.", 0, 0);
        terminal.write("Press ENTER to continue...", 0, 1);
        terminal.write("choose your indentify ",0,2);
        terminal.write("1 for client ",0,3);
        terminal.write("2 for server ",0,4);
        terminal.write("please server first ",0,5);
        
    }

}