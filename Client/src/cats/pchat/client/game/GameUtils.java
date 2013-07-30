package cats.pchat.client.game;

import cats.pchat.core.profile.Profile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 9:36 PM
 */
public final class GameUtils {

    public static final Dimension PROFILE_SIZE = new Dimension(30, 30);
    public static final Dimension GAME_SIZE = new Dimension(500, 500);
    public static final Dimension MSG_SIZE = new Dimension(15, 15);

    private GameUtils(){}

    public static void draw(final Graphics2D g, final Profile profile){
        final Color c = profile.color().get();
        final Point p = profile.pos().get();
        g.drawImage(icon(c, PROFILE_SIZE).getImage(), p.x, p.y, null);
    }

    private static ImageIcon icon(final Color c, final Dimension d){
        final BufferedImage bi = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        final Paint paint = new GradientPaint(0, 0, c, d.width, d.height, c.darker());
        final Graphics2D g = bi.createGraphics();
        g.setPaint(paint);
        g.fillRect(0, 0, d.width, d.height);
        g.dispose();
        return new ImageIcon(bi);
    }

    public static ImageIcon icon(final Color c){
        return icon(c, MSG_SIZE);
    }
}
