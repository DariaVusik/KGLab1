import javax.swing.*;
import java.awt.*;


public class RGBSwatch extends JPanel implements Observer {

    private final ColorPanel colorPanel;
    private int R;
    private int G;
    private int B;

    private final JTextField redTextField;
    private final JTextField greenTextField;
    private final JTextField blueTextField;

    private final JSlider redScrollBar;
    private final JSlider greenScrollBar;
    private final JSlider blueScrollBar;

    boolean is_source;



    public RGBSwatch(ColorPanel colorpanel) {
        super();
        this.colorPanel = colorpanel;

        Color color = this.colorPanel.getColor();

        R = color.getRed();
        G = color.getGreen();
        B = color.getBlue();

        redTextField = new JTextField("" + R);
        greenTextField = new JTextField("" + G);
        blueTextField = new JTextField("" + B);

        int MAX_VAl = 255;
        redScrollBar = new JSlider(JSlider.HORIZONTAL, 0, MAX_VAl, R);
        blueScrollBar = new JSlider(JSlider.HORIZONTAL, 0, MAX_VAl, B);
        greenScrollBar = new JSlider(JSlider.HORIZONTAL, 0, MAX_VAl, G);
        is_source = false;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel row0 = new JPanel();
        row0.setLayout(new BoxLayout(row0, BoxLayout.X_AXIS));
        row0.add(new JLabel("RGB:"));

        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        row1.add(new JLabel("Red:"));
        row1.add(redTextField);
        row1.add(redScrollBar);

        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        row2.add(new JLabel("Green:"));
        row2.add(greenTextField);
        row2.add(greenScrollBar);

        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        row3.add(new JLabel("Blue:"));
        row3.add(blueTextField);
        row3.add(blueScrollBar);

        add(row0);
        add(row1);
        add(row2);
        add(row3);


        redTextField.addActionListener(e -> {
            R = Integer.parseInt(redTextField.getText());
            if (R < 0 || R > 255) {
                throw new IllegalArgumentException("Error! Red value must be between 0 and 255!");
            }
            redScrollBar.setValue(R);
            updateColorPanel();
        });

        blueTextField.addActionListener(e -> {
            B = Integer.parseInt(blueTextField.getText());
            if (B < 0 || B > 255) {
                throw new IllegalArgumentException("Error! Blue value must be between 0 and 255!");
            }
            blueScrollBar.setValue(B);
            updateColorPanel();
        });

        greenTextField.addActionListener(e -> {
            G = Integer.parseInt(greenTextField.getText());
            if (G < 0 || G > 255) {
                throw new IllegalArgumentException("Error! Green value must be between 0 and 255!");
            }
            greenScrollBar.setValue(G);
            updateColorPanel();
        });

        redScrollBar.addChangeListener(e -> {
            R = redScrollBar.getValue();
            redTextField.setText("" + R);
            updateColorPanel();
        });

        greenScrollBar.addChangeListener(e -> {
            G = greenScrollBar.getValue();
            greenTextField.setText("" + G);
            updateColorPanel();
        });

        blueScrollBar.addChangeListener(e -> {
            B = blueScrollBar.getValue();
            blueTextField.setText("" + B);
            updateColorPanel();
        });


    }
    @Override
    public void update() {

        Color color = this.colorPanel.getColor();

        int R1 = color.getRed();
        int G1 = color.getGreen();
        int B1 = color.getBlue();


        if (R != R1) {
            R = R1;
            redScrollBar.setValue(R);
            redTextField.setText("" + R);

        }
        if (G1 != G) {
            G = G1;
            greenTextField.setText("" + G);
            greenScrollBar.setValue(G);
        }

        if (B1 != B) {
            B = B1;
            blueScrollBar.setValue(B);
            blueTextField.setText("" + B);
        }
    }

    @Override
    public boolean is_change_source() {
        return is_source;
    }

    void updateColorPanel() {

        // this.colorPanel.removeObserver(this);
        is_source = true;
        Color color = new Color(R, G, B);
        colorPanel.setColor(color);
        colorPanel.repaint();
        colorPanel.color_changed();
        is_source = false;
        //colorPanel.registerObserver(this);
        //if (this.colorPanel.is_currently_observer((Observer) this)) {}


    }
}
