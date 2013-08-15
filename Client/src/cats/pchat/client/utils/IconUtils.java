package cats.pchat.client.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * Josh
 * 14/08/13
 * 11:37 PM
 */
public final class IconUtils {

    public static Dimension size = new Dimension(15, 15);

    private IconUtils(){}

    public static ImageIcon icon(final Color color){
        final BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        final Paint paint = new GradientPaint(0, 0, color, size.width, size.height, color.brighter());
        final Graphics2D g = image.createGraphics();
        g.setPaint(paint);
        g.fillRect(0, 0, size.width, size.height);
        g.dispose();
        return new ImageIcon(image);
    }
}
