package com.benstone.Utils;

/**
 * Created by Ben on 1/29/2016.
 */
public final class Constants
{
    // World Parameters
    public static final int MIN_WORLD_WIDTH = 600;
    public static final int MIN_WORLD_HEIGHT = 480;

    // Pixels Per Meter
    public static final float PPM = 32;

    // Filters. Increments by powers of 2. Up to 32 Categories
    public static final short BIT_PLAYER = 1;
    public static final short BIT_WALL = 2;
    public static final short BIT_ENEMY = 4;

    // Tiles
    public static final char PLAYER = 'P';
    public static final char WALL = 'W';
}
