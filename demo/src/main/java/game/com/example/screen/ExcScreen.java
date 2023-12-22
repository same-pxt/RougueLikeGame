package game.com.example.screen;

import game.asciiPanel.AsciiPanel;
import game.com.example.world.Creature;
import game.com.example.world.World;
import game.com.example.world.Creature.creatureType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Executors;
import java.awt.event.KeyEvent;

public class ExcScreen extends RestartScreen{

    private PlayScreen ps;
    ExcScreen(PlayScreen ps)
    {
        this.ps=ps;
    }

    private void save() {
        try(ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream(new File("save.data")))) {
                oos.writeObject(this.ps.world);
                oos.flush();
                oos.close();
        } catch(IOException io) {
            io.printStackTrace();
        }
    }
    private void load() {
        try(ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream("save.data"))) {
                this.ps.world = (World)ois.readObject();
                ois.close();
            for(Creature creature:this.ps.world.creatures)
            {
                if(creature.type==creatureType.FUNGUS)
                {
                    creature.start();
                }
                else
                {
                    this.ps.player=creature;
                }
            }
        } catch(IOException io) {
            io.printStackTrace();
        } catch(ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }
    }
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("1 Restart", 7, AsciiPanel.brightYellow);
        terminal.writeCenter("2  Load  ", 9, AsciiPanel.brightYellow);
        terminal.writeCenter("3  Save  ", 11, AsciiPanel.brightYellow);
        terminal.writeCenter("4  Record  ", 13, AsciiPanel.brightYellow);
        terminal.writeCenter("5  Reply  ", 15, AsciiPanel.brightYellow);
        terminal.write("Press Esc to back", 0, 19, AsciiPanel.brightWhite);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch(key.getKeyCode()) {
            case KeyEvent.VK_1:
                return new PlayScreen();
            case KeyEvent.VK_2:
                load();
                return this.ps;
            case KeyEvent.VK_3:
                save();
                return this.ps;
            case KeyEvent.VK_ESCAPE:
                return this.ps;
            case KeyEvent.VK_4:
                save();
                return this.ps;
            case KeyEvent.VK_5:
                return new ReplayScreen(this.ps);
            default:
                return this; 
        }
    }
}
