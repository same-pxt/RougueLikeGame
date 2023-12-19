package screen;

import asciiPanel.AsciiPanel;
import world.*;
import asciiPanel.AsciiPanel;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayScreen implements Screen,Runnable{

    private World world;
    private Creature player;
    private int screenWidth;
    private int screenHeight;
    private List<String> messages;
    private List<String> oldMessages;
    AsciiPanel terminal;
    CreatureFactory creatureFactory;
    int h=0;
    public PlayScreen()
    {
        this.screenHeight=30;
        this.screenWidth=30;
        this.messages = new ArrayList<String>();
        creatWorld();
        creatureFactory=new CreatureFactory(this.world);
        
        
    }
    @Override
    public void displayOutput(AsciiPanel terminal) {
        this.terminal=terminal;
        displayTiles(terminal, getScrollX(), getScrollY());
        terminal.write("Hp  "+player.hp(),2,32);
        terminal.write("Remains:"+world.FungSize,12,32);
        if (world.FungSize==0) {
            terminal.clear();
            terminal.write("Win!",10,20);
        }
        else if(world.playerSize==0)
        {
            terminal.clear();
            terminal.write("Lose!",10,20);
        }
    }
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_A:
                player.moveBy(-1, 0);
                break;
            case KeyEvent.VK_D:
                player.moveBy(1, 0);
                break;
            case KeyEvent.VK_W:
                player.moveBy(0, -1);
                break;
            case KeyEvent.VK_S:
                player.moveBy(0, 1);
                break;
            default :
                player.moveBy(0, 0);
                break;
        }
        return this;
    }

    public void creatWorld()
    {
        WorldBuilder w =new WorldBuilder(30, 30);
        this.world = w.makeCaves().build();
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        // Show terrain
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
        }
        // Show creatures
        for (Creature creature : world.getCreatures()) {
            if (creature.x() >= left && creature.x() < left + screenWidth && creature.y() >= top
                    && creature.y() < top + screenHeight) {
                terminal.write(creature.glyph(), creature.x() - left, creature.y() - top, creature.color());
            }
        }
        // Creatures can choose their next action now
        world.update();
    }
    public void creatCreature()
    {
        this.player=creatureFactory.newPlayer(messages);
        for(int i=0;i<8;i++)
        {
            Creature t=creatureFactory.newFungus();
            t.start();
        }
    }
    public int getScrollX() {
        return Math.max(0, Math.min(player.x() - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player.y() - screenHeight / 2, world.height() - screenHeight));
    }
    @Override
    public void run()
    { 
        //Thread player1=new Thread(creatureFactory.newPlayer(messages));
        //Thread player2=new Thread(creatureFactory.newPlayer(messages));
        creatCreature();
    }
}
