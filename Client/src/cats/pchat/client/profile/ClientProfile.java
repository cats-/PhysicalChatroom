package cats.pchat.client.profile;

import cats.pchat.core.GameConstants;
import cats.pchat.core.profile.BasicProfile;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;

/**
 * Josh
 * 14/08/13
 * 9:43 PM
 */
public class ClientProfile extends BasicProfile implements GameConstants{

    public ClientProfile(final long uid){
        super(uid);
    }

    public void draw(final Graphics2D g){
        final Paint paint = new GradientPaint(location.x, location.y, color, location.x + PLAYER_SIZE.width, location.y + PLAYER_SIZE.height, color.brighter());
        g.setPaint(paint);
        g.fillRect(location.x, location.y, PLAYER_SIZE.width, PLAYER_SIZE.height);
        g.setColor(color);
        final FontMetrics metrics = g.getFontMetrics();
        g.drawString(name, location.x, location.y - metrics.getMaxDescent());
    }
}
