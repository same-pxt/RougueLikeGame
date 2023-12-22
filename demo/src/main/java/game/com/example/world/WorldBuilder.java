package game.com.example.world;

import java.util.Random;
import java.util.Random; 
public class WorldBuilder {

    private int width;
    private int height;
    private Tile[][] tiles;

    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
    }

    public World build() {
        return new World(tiles);
    }

    private WorldBuilder randomizeTiles() {
        MazeGenerator mazeGenerator = new MazeGenerator(28);
        int [][] my_maze=mazeGenerator.generateMaze();
        //System.out.println("RAW MAZE\n" + mazeGenerator.getRawMaze());
        for (int width = 0; width < this.width; width++) {
            for (int height = 0; height < this.height; height++) {
                    tiles[width][height] = Tile.FLOOR;
                }
            }
        Random random = new Random();
        
        for(int i=0;i<28;i++)
        {
            for(int j=0;j<28;j++)
            {
                switch (my_maze[i][j]) {
                    case 0:
                        tiles[i+1][j+1] = Tile.WALL;
                        break;
                    case 1:
                        tiles[i+1][j+1] = Tile.FLOOR;
                        break;
                }
            }
        }
        for(int i=0;i<10;i++)
        {
            tiles[random.nextInt(29)][random.nextInt(29)]=Tile.BOUNDS;
        }
        return this;
    }
    public WorldBuilder makeCaves() {
        return randomizeTiles();
    }
}
