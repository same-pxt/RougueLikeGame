package game.com.example.world;

import java.util.List;

import game.asciiPanel.*;
import game.com.example.world.Creature.creatureType;
import java.io.Serializable;

public class CreatureFactory implements Serializable{

    private World world;

    public CreatureFactory(World world) {
        this.world = world;
    }

    public Creature newPlayer(List<String> messages) {
        Creature player = new Creature(creatureType.PLAYER,this.world, (char)6, AsciiPanel.brightWhite, 100, 20, 5, 9);
        world.addAtEmptyLocation(player);
        world.playerSize++;
        new PlayerAI(player, messages);
        Creature.playId++;
        return player;
    }
    public Creature newPlayer(int id,int x,int y,List<String> messages) {
        Creature player = new Creature(creatureType.PLAYER,this.world, (char)6, AsciiPanel.brightWhite, 100, 20, 5, 9);
        player.setX(x);
        player.setY(y);
        player.id=id;
        this.world.creatures.add(player);
        new PlayerAI(player, messages);
        return player;
    }
    public Creature newFungus() {
        Creature fungus = new Creature(creatureType.FUNGUS,this.world, (char)2, AsciiPanel.brightYellow, 20, 20, 3, 0);
        world.addAtEmptyLocation(fungus);
        world.FungSize=world.FungSize+1;
        new FungusAI(fungus, this);
        return fungus;
    }
}
