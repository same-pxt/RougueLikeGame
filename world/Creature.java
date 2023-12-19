package world;

import java.awt.Color;
import java.lang.Thread;
import java.security.PublicKey;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Creature extends Thread{

    enum creatureType
    {
        PLAYER,
        FUNGUS,
    }
    private World world;

    private int x;

    public void setX(int x) {
        this.x = x;
    }

    public int x() {
        return x;
    }

    private int y;

    public void setY(int y) {
        this.y = y;
    }

    public int y() {
        return y;
    }

    private char glyph;

    public char glyph() {
        return this.glyph;
    }

    private Color color;

    public Color color() {
        return this.color;
    }

    private CreatureAI ai;

    public void setAI(CreatureAI ai) {
        this.ai = ai;
    }

    private int maxHP;

    public int maxHP() {
        return this.maxHP;
    }

    private int hp;

    public int hp() {
        return this.hp;
    }

    public synchronized void modifyHP(int amount) {
        this.hp += amount;
        if(this.hp>100)
            hp=100;
        if (this.hp < 1) {
            world.remove(this);
            if(this.type==creatureType.PLAYER)
            {
                this.world.playerSize--;
            }
            else if(this.type==creatureType.FUNGUS)
            {
                this.world.FungSize--;
            }
        }
    }

    private int attackValue;

    public int attackValue() {
        return this.attackValue;
    }

    private int defenseValue;

    public int defenseValue() {
        return this.defenseValue;
    }

    private int visionRadius;

    public int visionRadius() {
        return this.visionRadius;
    }

    public boolean canSee(int wx, int wy) {
        return ai.canSee(wx, wy);
    }

    public Tile tile(int wx, int wy) {
        return world.tile(wx, wy);
    }

    public void dig(int wx, int wy) {
        world.dig(wx, wy);
    }

    public synchronized void moveBy(int mx, int my) {
        if(this.hp<=0)
        {
            return;
        }
        if(x+mx<0 || x+mx >29 || y+my<0 || y+my >29)
        {   
            Creature other = world.creature(x, y);
            if (other == null) {
                ai.onEnter(x, y, world.tile(x, y));
            } else {
                if(this.type!=other.type)
                {
                    attack(other);
                    if(other.hp()>0)
                        other.attack(this);
                }
                    
            }
            return ;
        }
        Creature other = world.creature(x + mx, y + my);
        if (other == null) {
            ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
        } else {
            if(this.type!=other.type)
            {
                attack(other);
                if(other.hp()>0)
                    other.attack(this);
            }
        }
    }

    public synchronized void attack(Creature other) {
        int damage = Math.max(0, this.attackValue() - other.defenseValue());
        damage = (int) (Math.random() * damage) + 1;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDateTime = currentDateTime.format(formatter);
        System.out.println(this + "attack"+ x +" "+y + "at" +formattedDateTime + " with" + damage);
        other.modifyHP(-damage);
        this.notify("You attack the '%s' for %d damage.", other.glyph, damage);
        other.notify("The '%s' attacks you for %d damage.", glyph, damage);
    }

    public void update() {
        this.ai.onUpdate();
    }

    public synchronized boolean canEnter(int x, int y) {
        return world.tile(x, y).isGround();
    }

    public void notify(String message, Object... params) {
        ai.onNotify(String.format(message, params));
    }

    public creatureType type;
    public Creature(creatureType type,World world, char glyph, Color color, int maxHP, int attack, int defense, int visionRadius) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.attackValue = attack;
        this.defenseValue = defense;
        this.visionRadius = visionRadius;
        this.type=type;
    }

    @Override
    public void run()
    {
        while (this.world.FungSize>0 && this.hp>0) {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            this.ai.automove(this.x(),this.y());
            this.yield();
        }
    }
}
