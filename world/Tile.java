package world;

import asciiPanel.AsciiPanel;
import java.awt.Color;

public enum Tile {

    FLOOR((char) 250, AsciiPanel.green),

    WALL((char) 177, AsciiPanel.brightBlack),

    BOUNDS((char) 3, AsciiPanel.brightRed);

    private char glyph;

    public char glyph() {
        return glyph;
    }

    private Color color;

    public Color color() {
        return color;
    }

    public boolean isDiggable() {
        return this != Tile.WALL;
    }

    public boolean isGround() {
        return this != Tile.WALL && this != Tile.BOUNDS;
    }
    public boolean isBounus() {
        return this ==Tile.BOUNDS;
    }
    Tile(char glyph, Color color) {
        this.glyph = glyph;
        this.color = color;
    }
}
