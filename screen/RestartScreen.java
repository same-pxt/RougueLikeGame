package screen;
import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
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
