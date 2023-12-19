package world;

import java.util.List;

import asciiPanel.AsciiPanel;
import world.Creature.creatureType;


public class CreatureFactory {

    private World world;

    public CreatureFactory(World world) {
        this.world = world;
    }

    public Creature newPlayer(List<String> messages) {
        Creature player = new Creature(creatureType.PLAYER,this.world, (char)6, AsciiPanel.brightWhite, 100, 20, 5, 9);
        world.addAtEmptyLocation(player);
        world.playerSize++;
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
