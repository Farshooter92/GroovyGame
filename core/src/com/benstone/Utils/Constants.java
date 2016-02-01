package com.benstone.Utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ben on 1/29/2016.
 */
public final class Constants
{
    public static final boolean DEBUG_BUILD = false;

    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 480;
    public static final float WORLD_TO_SCREEN = 32;

    // World Parameters
    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    // Filters. Increments by powers of 2. Up to 32 Categories
    public static final short BIT_PLAYER = 1;
    public static final short BIT_GROUND = 2;
    public static final short BIT_ENEMY = 4;

    // Asset ID's
    public static final String PLAYER_ASSETS_ID = "player";
    public static final String BACKGROUND_ASSETS_ID = "background";
    public static final String GROUND_ASSETS_ID = "ground";

    // Image Paths
    public static final String PLAYER_IMAGE_PATH = "player.png";
    public static final String BACKGROUND_IMAGE_PATH = "background.png";
    public static final String GROUND_IMAGE_PATH = "ground.png";
    public static final String OBSTACLE_IMAGE_PATH = "obstacle.png";

    // Scripts
    public static final String PLAYER_SCRIPT = "player.groovy";
    public static final String GROUND_SCRIPT = "ground.groovy";
    public static final String OBSTACLE_SCRIPT = "obstacle.groovy";

    // Font
    public static final String FONT_NAME = "OCR_A_Extended_Regular.ttf";

    // Player Properties
    public static final float PLAYER_WIDTH = 4;
    public static final float PLAYER_HEIGHT = 4;
    public static final float PLAYER_SPAWN_X = 4;
    public static final float PLAYER_SPAWN_Y = 4;
    public static final float PLAYER_SIDE_TO_SIDE_SPEED = 1;
    public static final float PLAYER_JUMP_FORCE = 5000;

    // Names
    public static final String PLAYER_ID = "Player";
    public static final String GROUND_ID = "Ground";
    public static final String OBSTACLE_ID = "Obstacle";

    // Controls
    public static final float JUMP_BUTTON_WIDTH = 100;
    public static final float JUMP_BUTTON_HEIGHT = 100;
    public static final float MOVEMENT_TOUCHPAD_WIDTH = 100;
    public static final float MOVEMENT_TOUCHPAD_HEIGHT = 100;
    public static final float MOVEMENT_TOUCHPAD_DEADZONE = 5;

}
