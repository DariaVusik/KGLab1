import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HSVSwatch extends JPanel implements Observer{
    JSlider HueComponent;
    JSlider SaturationComponent;
    JSlider ValueComponent;

    JTextField hue;
    JTextField saturation;
    JTextField value;

    ColorPanel ownColorPanel;
    Color ownColor;



    static final int MAX_VALUE = 100;
    static final int MIN_VALUE = 100;
    public HSVSwatch(Color color, ColorPanel colorPanel){
        super();
        ownColor = color;
        ownColorPanel = colorPanel;

        HueComponent = new JSlider(JSlider.HORIZONTAL,360, 0);
        SaturationComponent = new JSlider(JSlider.HORIZONTAL,MAX_VALUE, MIN_VALUE);
        ValueComponent = new JSlider(JSlider.HORIZONTAL,MAX_VALUE, MIN_VALUE);

        hue = new JTextField(3);
        saturation = new JTextField(3);
        value = new JTextField(3);
        update(color);



        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel row0 = new JPanel();
        row0.setLayout(new BoxLayout(row0, BoxLayout.X_AXIS));
        JLabel name = new JLabel("HSV:");
        row0.add(name);
        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        JLabel hue_label = new JLabel("Hue");
        row1.add(hue_label);
        row1.add(HueComponent);
        row1.add(hue);
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        JLabel saturation_label = new JLabel("Saturation");
        row2.add(saturation_label);
        row2.add(SaturationComponent);
        row2.add(saturation);
        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        JLabel value_label = new JLabel("Value");
        row3.add(value_label);
        row3.add(ValueComponent);
        row3.add(value);
        add(row0);
        add(row1);
        add(row2);
        add(row3);

        HueComponent.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                hue.setText("" + HueComponent.getValue());

                update_color();

            }
        });
         SaturationComponent.addChangeListener(new ChangeListener() {
             @Override
             public void stateChanged(ChangeEvent e) {
                 saturation.setText("" + SaturationComponent.getValue());
                 update_color();

             }
         });

         ValueComponent.addChangeListener(new ChangeListener() {
             @Override
             public void stateChanged(ChangeEvent e) {
                 value.setText("" + ValueComponent.getValue());
                 update_color();

             }
         });

         hue.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 HueComponent.setValue(Integer.parseInt(hue.getText()));
                 update_color();

             }
         });

         saturation.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 SaturationComponent.setValue(Integer.parseInt(saturation.getText()));
                 update_color();

             }
         });

         value.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 ValueComponent.setValue(Integer.parseInt(value.getText()));
                 update_color();

             }
         });


    }

    @Override
    public void update(Color color) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        // Вычисление максимального и минимального значений RGB
        float max = Math.max(r, Math.max(g, b));
        float min = Math.min(r, Math.min(g, b));

        // Вычисление значения оттенка
        float h;
        if (max == min) {
            h = 0;  // Если все значения RGB одинаковые, оттенок равен 0
        } else if (max == r) {
            h = (60 * ((g - b) / (max - min)) + 360) % 360;
        } else if (max == g) {
            h = (60 * ((b - r) / (max - min)) + 120) % 360;
        } else {
            h = (60 * ((r - g) / (max - min)) + 240) % 360;
        }

        // Вычисление значения насыщенности
        float s;
        if (max == 0) {
            s = 0;  // Если максимальное значение RGB равно 0, насыщенность равна 0
        } else {
            s = 100 * (max - min) / max;
        }

        // Вычисление значения значения
        float v = 100 * max;
        hue.setText("" + (int) h);
        saturation.setText("" + (int) s);
        value.setText(("" + (int) v));

        HueComponent.setValue((int) h);
        SaturationComponent.setValue((int) s);
        ValueComponent.setValue((int) v);


    }
     private void update_color() {
        double h = HueComponent.getValue() / 360f;
        double s = SaturationComponent.getValue() / 100f;
        double v = ValueComponent.getValue() / 100f;

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

        // Приведение значений RGB в диапазон от 0 до 255
        int red = (int) (r * 255);
        int green = (int) (g * 255);
        int blue = (int) (b * 255);
        ownColorPanel.removeObserver(this);
        ownColor = new Color(red, green, blue);
        ownColorPanel.removeObserver(this);
        ownColorPanel.setColor(ownColor);
        ownColorPanel.repaint();
        ownColorPanel.registerObserver(this);
        ownColorPanel.registerObserver(this);



    }
}
