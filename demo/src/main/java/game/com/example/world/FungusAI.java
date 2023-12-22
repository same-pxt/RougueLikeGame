package game.com.example.world;

import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class FungusAI extends CreatureAI {

    private CreatureFactory factory;
    private int spreadcount = 0;

    public static int spores = 5;
    public static double spreadchance = 0.01;

    public FungusAI(Creature creature, CreatureFactory factory) {
        super(creature);
        this.factory = factory;
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
            creature.dig(x, y);
        }        
    }
    public void automove(int x,int y)
    {
        Random random = new Random();
        int x1 = random.nextInt(3)-1;
        int y1 = random.nextInt(3)-1;
        this.creature.moveBy(x1, y1); 
    }
}
