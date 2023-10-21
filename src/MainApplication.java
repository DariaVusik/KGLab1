import javax.swing.*;
import java.awt.*;


public class MainApplication extends JFrame {
    public static void main(String[] args) {
        MainApplication app = new MainApplication("Demo");
        app.setSize(900, 400);
        app.setVisible(true);
    }

    public MainApplication(String tittle) {
        super(tittle);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ColorPanel colorPanel = new ColorPanel(Color.orange);
        JPanel swatches = new JPanel();
        swatches.setLayout(new GridLayout(1, 3));
        RGBSwatch rgbSwatch = new RGBSwatch(colorPanel);
        CMYKSwatch cmykSwatch = new CMYKSwatch(colorPanel);
        HSVSwatch hsvSwatch = new HSVSwatch(colorPanel);
        JButton changeColor = new JButton("change color");
        swatches.add(rgbSwatch);
        swatches.add(cmykSwatch);
        swatches.add(hsvSwatch);
        colorPanel.registerObserver(rgbSwatch);
        colorPanel.registerObserver(cmykSwatch);
        colorPanel.registerObserver(hsvSwatch);
        setLayout(new BorderLayout());
        getContentPane().add(colorPanel, BorderLayout.CENTER);
        getContentPane().add(swatches, BorderLayout.SOUTH);

        changeColor.addActionListener(e -> {
            Color color = colorPanel.getColor();
            color = JColorChooser.showDialog(null, tittle, color);
            colorPanel.setColor(color);
            colorPanel.repaint();
            colorPanel.color_changed();

        });

        getContentPane().add(changeColor, BorderLayout.NORTH);




    }
}
