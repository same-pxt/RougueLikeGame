package game.com.example.world;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import game.asciiPanel.AsciiFont;
import game.asciiPanel.AsciiPanel;
import game.com.example.ApplicationMain;
import game.com.example.screen.PlayScreen;
import game.com.example.screen.RestartScreen;
import game.com.example.screen.StartScreen;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.Before;    
    
public class CreatureTest {

    World world;
    CreatureFactory creaturefactory;
    @Before
    public void setup(){
        Tile [][]tiles=new Tile[30][30];
        for(int i=0;i<30;i++)
        {
            for(int j=0;j<30;j++)
            {
                tiles[i][j]=Tile.FLOOR;
            }
        }
        this.world=new World(tiles);
        this.creaturefactory=new CreatureFactory(this.world);
    }
        
    @Test
    public void testMoveBy() {
        Creature f1=this.creaturefactory.newFungus();
        int x1=f1.x();
        int y1=f1.y();
        f1.moveBy(1, 1);
        int x2=f1.x();
        int y2=f1.y();
        if(x1!=29||x1!=0)
            assertEquals(x1+1, x2);
        if(y1!=29||y1!=0)
            assertEquals(y1+1, y2);

        f1.setX(0);
        f1.setY(0);
        f1.moveBy(-1, 1);
        x1=f1.x();
    }

    @Test
    public void testModifyHp() {
        Creature f1=this.creaturefactory.newFungus();
        int hp1=f1.hp();
        f1.modifyHP(-6);
        int hp2=f1.hp();
        assertEquals(hp1, hp2+6);
        f1.modifyHP(110);
        hp2=f1.hp();
        assertEquals(100, hp2);
        f1.modifyHP(-101);
        hp2=f1.hp();
        assertEquals(-1, hp2);
        f1.attackValue();

        List<String>messages=new ArrayList<>();
        Creature f2=this.creaturefactory.newPlayer(messages);
        f2.defenseValue();
        f2.dig(0, 0);
    }
    @Test
    public void testPlayScreen()
    {
        PlayScreen t=new PlayScreen();
        t.creatCreature();
        StartScreen r=new StartScreen();
    }
}
    