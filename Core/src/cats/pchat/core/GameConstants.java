package cats.pchat.core;

import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * Josh
 * 14/08/13
 * 9:44 PM
 */
public interface GameConstants {

    int SPEED = 3;

    Dimension GAME_SIZE = new Dimension(500, 500);

    Rectangle GAME_BOUNDS = new Rectangle(0, 0, GAME_SIZE.width, GAME_SIZE.height);

    Dimension PLAYER_SIZE = new Dimension(25, 25);
}
