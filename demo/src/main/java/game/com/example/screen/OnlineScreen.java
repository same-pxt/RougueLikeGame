package game.com.example.screen;

import game.asciiPanel.AsciiPanel;
import game.com.example.world.Creature;
import game.com.example.world.CreatureFactory;
import game.com.example.world.Tile;
import game.com.example.world.World;
import game.com.example.online.*;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OnlineScreen implements Screen{
    public World world;
    public Creature player;
    private Creature player1;
    private Creature player2;
    private Creature player3;
    private int screenWidth;
    private int screenHeight;
    private List<String> messages;
    private List<String> oldMessages;
    AsciiPanel terminal;
    public CreatureFactory creatureFactory;
    int isClient;
    Client client;
    OnlineScreen(int isClient)
    {
        this.screenHeight=30;
        this.screenWidth=30;
        this.messages = new ArrayList<String>();
        this.isClient=isClient;
        
        if(isClient==1)
        {
            try {
                
                client=new Client(this);
                client.start(); 
                while (this.world==null) {
                    System.out.println("WAITING");
                    continue;
                }
                this.creatureFactory=new CreatureFactory(this.world);
                this.player=creatureFactory.newPlayer(messages);
                client.sendStringToServer(client.socketChannel,"put "+this.player.id+" "+this.player.x()+" "+this.player.y());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
        else
        {
            try {
                creatWorld();
                creatureFactory=new CreatureFactory(this.world);  
                creatCreature();
                Server server=new Server(this);
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void displayOutput(AsciiPanel terminal)
    {
        displayTiles(terminal, getScrollX(), getScrollY());
    }

    private void  displayTiles(AsciiPanel terminal, int left, int top) {
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
        world.update();
    }
    @Override
    public Screen respondToUserInput(KeyEvent key)
    {
        if(this.isClient==0)
        {
            switch (key.getKeyCode()) 
            {
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
        }
        else
        {
            switch (key.getKeyCode()) 
            {
            case KeyEvent.VK_A:
                    try {
                        this.client.sendStringToServer(client.socketChannel,"move "+this.player.id+" "+"-1"+" "+"0");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;
            case KeyEvent.VK_D:
                try {
                        this.client.sendStringToServer(client.socketChannel,"move "+this.player.id+" "+"1"+" "+"0");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;
            case KeyEvent.VK_W:
                try {
                        this.client.sendStringToServer(client.socketChannel,"move "+this.player.id+" "+"0"+" "+"-1");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;
            case KeyEvent.VK_S:
                try {
                        this.client.sendStringToServer(client.socketChannel,"move "+this.player.id+" "+"0"+" "+"1");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;
            default :
                player.moveBy(0, 0);
                break;
            }
        }
        
        return this;
    }

    void creatWorld()
    {
        Tile [] [] tiles =new Tile[30][30];
        for(int i=0;i<30;i++)
        {
            for(int j=0;j<30;j++)
            {
                tiles[i][j]=Tile.FLOOR;
            }
        }
        this.world=new World(tiles);
    }
    void creatCreature()
    {
        this.player=creatureFactory.newPlayer(messages);
        for(int i=0;i<4;i++)
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
}
