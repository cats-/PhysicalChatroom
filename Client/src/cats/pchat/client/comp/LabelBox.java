package cats.pchat.client.comp;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Josh
 * 14/08/13
 * 10:02 PM
 */
public class LabelBox extends JPanel {

    private static final String EMPTY = "";

    private final JLabel label;
    private final JTextField box;

    public LabelBox(final String text, final boolean pass){
        super(new GridLayout(1, 2));

        label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);

        box = pass ? new JPasswordField(20) : new JTextField(20);
        box.setHorizontalAlignment(JLabel.CENTER);

        add(label);
        add(box);
    }

    public LabelBox(final String text){
        this(text, false);
    }

    public String value(){
        return box.getText().trim();
    }

    public void clear(){
        box.setText(EMPTY);
        box.repaint();
    }
}
