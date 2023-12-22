package game.com.example.screen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.io.ObjectOutputStream;
import java.lang.annotation.Retention;
import java.util.concurrent.Executors;
import java.awt.event.KeyEvent;

import game.asciiPanel.AsciiPanel;
import game.com.example.world.Creature;
import game.com.example.world.World;
import game.com.example.world.Creature.creatureType;

public class ReplayScreen  extends RestartScreen{

    private PlayScreen ps;
    long lastPosition = 0;
    ReplayScreen(PlayScreen ps)
    {
        this.ps=ps;
        load();
    }
    public void load()
    {
        try(ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream("save.data"))) {
                this.ps.world = (World)ois.readObject();
                ois.close();
            for(Creature creature:this.ps.world.creatures)
            {
                if(creature.type==creatureType.PLAYER)
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
    public void move()
    {
        String filePath = "data.txt";

        try {
            RandomAccessFile file = new RandomAccessFile(filePath, "r");

            // 如果上次读取位置不为0，将文件指针移动到上次读取的位置
            if (lastPosition > 0) {
                file.seek(lastPosition);
            }

            String line;
            while ((line = file.readLine()) != null) {
                if(!line.isEmpty())
                {
                    String[] parts = line.split(" ");
                    if (parts.length == 3) {
                        int id = Integer.parseInt(parts[0]);
                        int x = Integer.parseInt(parts[1]);
                        int y = Integer.parseInt(parts[2]);

                        for(Creature creature:this.ps.world.creatures)
                        {
                            if(creature.id==id)
                            {
                                creature.moveBy(x-creature.x(), y-creature.y());
                                System.out.println(id+" "+x+" "+y);
                            }
                        }
                        
                    }
                }
                else
                {
                    lastPosition = file.getFilePointer();
                    break;
                }
            }
            System.out.println();
            // 保存当前文件指针位置，以便下次读取时使用
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch(key.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                move();
                return this;
            default:
                return this; 
        }
    }
    @Override
    public void displayOutput(AsciiPanel terminal){
        this.ps.displayOutput(terminal);
    }
}
