package world;

import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlayerAI extends CreatureAI {

    private List<String> messages;

    public PlayerAI(Creature creature, List<String> messages) {
        super(creature);
        this.messages = messages;
    }

    public synchronized void onEnter(int x, int y, Tile tile) {
        // LocalDateTime currentDateTime = LocalDateTime.now();
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        // String formattedDateTime = currentDateTime.format(formatter);
        if (tile.isGround()) {
            //System.out.println(this + "arrive at"+x +" "+y + "at" +formattedDateTime);
            creature.setX(x);
            creature.setY(y);
        } else if (tile.isDiggable()) {
            if(tile.isBounus())
            {
                creature.modifyHP(+10);
            }
            creature.dig(x, y);
        }        
    }
    public void automove(int x,int y)
    {
        
    }
    public synchronized void onNotify(String message) {
        this.messages.add(message);
    }
}
