import javax.swing.*;
import java.awt.*;


public class HSVSwatch extends JPanel implements Observer {

    private int H;
    private int S;
    private int V;

    private final ColorPanel colorPanel;

    private final JTextField hueTextField;
    private final JTextField saturationTextField;
    private final JTextField valueTextField;

    private final JSlider hueSlider;
    private final JSlider saturationSlider;
    private final JSlider valueSlider;

    private boolean is_source;


    public HSVSwatch(ColorPanel cp) {
        colorPanel = cp;
        update_values();

        hueTextField = new JTextField("" + H);
        saturationTextField = new JTextField("" + S);
        valueTextField = new JTextField("" + V);

        int HUE_MAX_VAL = 360;
        hueSlider = new JSlider(JSlider.HORIZONTAL, 0, HUE_MAX_VAL, H);
        int MAX_VAL = 100;
        saturationSlider = new JSlider(JSlider.HORIZONTAL, 0, MAX_VAL, S);
        valueSlider = new JSlider(JSlider.HORIZONTAL, 0, MAX_VAL, V);

        is_source = false;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel row0 = new JPanel();
        row0.setLayout(new BoxLayout(row0, BoxLayout.X_AXIS));
        row0.add(new JLabel("HSV:"));

        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        row1.add(new JLabel("Hue:"));
        row1.add(hueTextField);
        row1.add(hueSlider);

        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        row2.add(new JLabel("Saturation:"));
        row2.add(saturationTextField);
        row2.add(saturationSlider);


        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        row3.add(new JLabel("Value:"));
        row3.add(valueTextField);
        row3.add(valueSlider);

        add(row0);
        add(row1);
        add(row2);
        add(row3);

        hueTextField.addActionListener(e -> {
            H = Integer.parseInt(hueTextField.getText());
            if (H < 0 || H > 360) {
                throw new IllegalArgumentException("Error! Hue value must be between 0 and 360!");
            }
            hueSlider.setValue(H);
            updateColorPanel();
        });

        saturationTextField.addActionListener(e -> {
            S = Integer.parseInt(saturationTextField.getText());
            if (S < 0 || S > 100) {
                throw new IllegalArgumentException("Error! Saturation value must be between 0 and 100!");
            }
            saturationSlider.setValue(S);
            updateColorPanel();

        });

        valueTextField.addActionListener(e -> {
            V = Integer.parseInt(valueTextField.getText());
            if (V < 0 || V > 100) {
                throw new IllegalArgumentException("Error! Value  must be between 0 and 100!");
            }
            valueSlider.setValue(V);
            updateColorPanel();
        });

        hueSlider.addChangeListener(e -> {
            H = hueSlider.getValue();
            hueTextField.setText("" + H);
            updateColorPanel();
        });

        saturationSlider.addChangeListener(e -> {
            S = saturationSlider.getValue();
            saturationTextField.setText("" + S);
            updateColorPanel();
        });

        valueSlider.addChangeListener(e -> {
            V = valueSlider.getValue();
            valueTextField.setText("" + V);
            updateColorPanel();
        });


    }


    @Override
    public void update() {
        int H1 = H;
        int S1 = S;
        int V1 = V;
        update_values();
        if (H != H1) {
            hueSlider.setValue(H);
            hueTextField.setText("" + H);
        }

        if (S != S1) {
            saturationSlider.setValue(S);
            saturationTextField.setText("" + S);
        }

        if (V != V1) {
            valueSlider.setValue(V);
            valueTextField.setText("" + V);
        }
    }

    @Override
    public boolean is_change_source() {
        return is_source;
    }

    private void updateColorPanel() {
        //this.colorPanel.removeObserver(this);
        is_source = true;
        double h = H / 360f;
        double s = S / 100f;
        double v = V / 100f;

        // Вычисление промежуточных значений
        int i = (int) Math.floor(h * 6);
        double f = h * 6 - i;
        double p = v * (1 - s);
        double q = v * (1 - f * s);
        double t = v * (1 - (1 - f) * s);

        // Вычисление значений RGB
        double r, g, b;
        switch (i % 6) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;
            case 1:
                r = q;
                g = v;
                b = p;
                break;
            case 2:
                r = p;
                g = v;
                b = t;
                break;
            case 3:
                r = p;
                g = q;
                b = v;
                break;
            case 4:
                r = t;
                g = p;
                b = v;
                break;
            default:
                r = v;
                g = p;
                b = q;
                break;
        }

        int red = (int) (r * 255);
        int green = (int) (g * 255);
        int blue = (int) (b * 255);
        Color color = new Color(red, green, blue);
        colorPanel.setColor(color);
        colorPanel.repaint();
        colorPanel.color_changed();
        //colorPanel.registerObserver(this);
        is_source = false;

        // if (this.colorPanel.is_currently_observer(this)) {};
    }

    void update_values() {
        Color color = colorPanel.getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));

        float h;
        if (max == min) {
            h = 0;
        } else if (max == r) {
            h = (60 * ((g - b) / (max - min)) + 360) % 360;
        } else if (max == g) {
            h = (60 * ((b - r) / (max - min)) + 120) % 360;
        } else {
            h = (60 * ((r - g) / (max - min)) + 240) % 360;
        }

        float s;
        if (max == 0) {
            s = 0;
        } else {
            s = 100 * (max - min) / max;
        }

        float v = 100 * max;

        H = (int) h;
        S = (int) s;
        V = (int) v;
    }
}
