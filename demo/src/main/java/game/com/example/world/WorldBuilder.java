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
        System.out.println("RAW MAZE\n" + mazeGenerator.getRawMaze());
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

    private WorldBuilder smooth(int factor) {
        Tile[][] newtemp = new Tile[width][height];
        if (factor > 1) {
            smooth(factor - 1);
        }
        for (int width = 0; width < this.width; width++) {
            for (int height = 0; height < this.height; height++) {
                // Surrounding walls and floor
                int surrwalls = 0;
                int surrfloor = 0;

                // Check the tiles in a 3x3 area around center tile
                for (int dwidth = -1; dwidth < 2; dwidth++) {
                    for (int dheight = -1; dheight < 2; dheight++) {
                        if (width + dwidth < 0 || width + dwidth >= this.width || height + dheight < 0
                                || height + dheight >= this.height) {
                            continue;
                        } else if (tiles[width + dwidth][height + dheight] == Tile.FLOOR) {
                            surrfloor++;
                        } else if (tiles[width + dwidth][height + dheight] == Tile.WALL) {
                            surrwalls++;
                        }
                    }
                }
                Tile replacement;
                if (surrwalls > surrfloor) {
                    replacement = Tile.WALL;
                } else {
                    replacement = Tile.FLOOR;
                }
                newtemp[width][height] = replacement;
            }
        }
        tiles = newtemp;
        return this;
    }

    public WorldBuilder makeCaves() {
        return randomizeTiles();
    }
}
