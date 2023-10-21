import javax.swing.*;

import java.awt.*;


public class CMYKSwatch extends JPanel implements Observer {

    private final ColorPanel colorPanel;
    private int C;
    private int M;
    private int Y;
    private int K;

    private final JTextField cyanTextField;
    private final JTextField magentaTextField;
    private final JTextField yellowTextField;
    private final JTextField keyTextField;

    private final JSlider cyanSlider;
    private final JSlider magentaSlider;
    private final JSlider yellowSlider;
    private final JSlider keySlider;

    private boolean is_source;
    public CMYKSwatch(ColorPanel cp) {
        colorPanel = cp;
        Color color = colorPanel.getColor();
        update_values(color);

        cyanTextField = new JTextField("" +C);
        magentaTextField = new JTextField("" + M);
        yellowTextField = new JTextField("" + Y);
        keyTextField = new JTextField("" + K);

        int MAX_VAL = 100;
        cyanSlider = new JSlider(JSlider.HORIZONTAL, 0, MAX_VAL, C);
        magentaSlider = new JSlider(JSlider.HORIZONTAL, 0, MAX_VAL, M);
        yellowSlider = new JSlider(JSlider.HORIZONTAL, 0, MAX_VAL, Y);
        keySlider = new JSlider(JSlider.HORIZONTAL, 0, MAX_VAL, K);

        is_source = false;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel row0 = new JPanel();
        row0.setLayout(new BoxLayout(row0, BoxLayout.X_AXIS));
        row0.add(new JLabel("CMYK:"));

        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        row1.add(new JLabel("Cyan: "));
        row1.add(cyanTextField);
        row1.add(cyanSlider);

        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        row2.add(new JLabel("Magenta: "));
        row2.add(magentaTextField);
        row2.add(magentaSlider);

        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        row3.add(new JLabel("Yellow: "));
        row3.add(yellowTextField);
        row3.add(yellowSlider);

        JPanel row4 = new JPanel();
        row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));
        row4.add(new JLabel("Key: "));
        row4.add(keyTextField);
        row4.add(keySlider);

        add(row0);
        add(row1);
        add(row2);
        add(row3);
        add(row4);

        cyanTextField.addActionListener(e -> {
            C = Integer.parseInt(cyanTextField.getText());
            if (C < 0 || C > 100) {
                throw new IllegalArgumentException("Error! Cyan value must be between 0 and 100!");
            }
            cyanSlider.setValue(C);
            updateColorPanel();
        });

        magentaTextField.addActionListener(e -> {
            M= Integer.parseInt(magentaTextField.getText());
            if (M < 0 || M > 100) {
                throw new IllegalArgumentException("Error! Magenta value must be between 0 and 100!");
            }
            magentaSlider.setValue(M);
            updateColorPanel();
        });

        yellowTextField.addActionListener(e -> {
            Y = Integer.parseInt(yellowTextField.getText());
            if (Y < 0 || Y > 100) {
                throw new IllegalArgumentException("Error! Yellow value must be between 0 and 100!");
            }
            yellowSlider.setValue(Y);
            updateColorPanel();
        });

        keyTextField.addActionListener(e -> {
            K = Integer.parseInt(keyTextField.getText());
            if (K < 0 || K > 100) {
                throw new IllegalArgumentException("Error! Yellow value must be between 0 and 100!");
            }
            keySlider.setValue(K);
            updateColorPanel();
        });

        cyanSlider.addChangeListener(e -> {
            C = cyanSlider.getValue();
            cyanTextField.setText("" + C);
            updateColorPanel();
        });

        magentaSlider.addChangeListener(e -> {
            M = magentaSlider.getValue();
            magentaTextField.setText("" + M);
            updateColorPanel();
        });

        yellowSlider.addChangeListener(e -> {
            Y = yellowSlider.getValue();
            yellowTextField.setText("" + Y);
            updateColorPanel();
        });

        keySlider.addChangeListener(e -> {
            K = keySlider.getValue();
            keyTextField.setText("" + K);
            updateColorPanel();
        });


    }
    @Override
    public void update() {
        Color color = this.colorPanel.getColor();
        double R = color.getRed();
        double B = color.getBlue();
        double G = color.getGreen();
        double K1 = Math.min(1 - R/255d, Math.min(1 - G/ 255d, 1 - B/255d)) * 100;
        double C1 = ((1 - R/255 - K1/100) / (1 - K1/100)) * 100;
        double M1 = ((1 - G/255 - K1/100) / (1 - K1/100)) * 100;
        double Y1 = ((1 - B/255 -K1/100) / (1 - K1/100)) * 100;


        if((int)K1 != K) {
            K = (int)K1;
            keySlider.setValue(K);
            keyTextField.setText("" + K);
        }

        if ((int)C1 != C) {
            C =(int) C1;
            cyanSlider.setValue(C);
            cyanTextField.setText("" + C);
        }
        if ((int)M1 != M) {
            M = (int)M1;
            magentaSlider.setValue(M);
            magentaTextField.setText("" + M);
        }
        if ((int) Y1 != Y) {
            Y = (int) Y1;
            yellowSlider.setValue(Y);
            yellowTextField.setText("" + Y);
        }
        if ((int) K1 != K) {
            K = (int) K1;
            keySlider.setValue(K);
            keyTextField.setText("" + K);
        }

    }

    @Override
    public boolean is_change_source() {
        return is_source;
    }

    void update_values(Color color) {
        double R = color.getRed();
        double B = color.getBlue();
        double G = color.getGreen();
        double K1 =Math.min(1 - R/255, Math.min(1 - G/ 255, 1 - B/255)) * 100;
        double C1 = ((1 - R/255 - K/100d) / (1 - K/100d)) * 100;
        double M1 = ((1 - G/255 - K/100d) / (1 - K/100d)) * 100;
        double Y1 = ((1 - B/255 -K/100d) / (1 - K/100d)) * 100;
        K = (int) K1;
        C = (int) C1;
        M = (int) M1;
        Y = (int) Y1;
    }

    void updateColorPanel() {
        //this.colorPanel.removeObserver(this);
        is_source = true;
        int R = (int)(255 * (1 - C / 100d) * (1 - K/100d));
        int G = (int)(255 * (1 - M/100d) * (1 - K/100d));
        int B = (int)(255 * (1 - Y/100d) * (1 - K/100d));


        Color color = new Color(R, G, B);
        colorPanel.setColor(color);
        colorPanel.repaint();
        colorPanel.color_changed();
        is_source = false;
        // colorPanel.registerObserver(this);
        // if (this.colorPanel.is_currently_observer(this)) {}

    }
}
