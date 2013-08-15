package cats.pchat.client.utils;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JPanel;

/**
 * Josh
 * 14/08/13
 * 10:01 PM
 */
public final class Spacing {

    public static int spacing = 10;

    private Spacing(){}

    public static Component v(){
        return Box.createRigidArea(new Dimension(0, spacing));
    }

    public static Component h(){
        return Box.createRigidArea(new Dimension(spacing, 0));
    }

    public static void add(final JPanel container, final Component component, final boolean vertical){
        container.add(component);
        container.add(vertical ? v() : h());
    }
}
