import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.IntStream;

//проверить на виртуальной машине перед проверкой

class ColorPanel extends JPanel implements Subject {
    private Color color;
    private ArrayList observers;


    public ColorPanel(Color color) {
        this.color = color;
        setPreferredSize(new Dimension(200, 200));
        observers = new ArrayList<>();
    }

    public void color_changed() {
        notifyObservers();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    public void setColor(Color color) {

        this.color = color;
        color_changed();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }

    }

    @Override
    public void notifyObservers() {
        //IntStream.range(0, observers.size()).mapToObj(i -> (Observer) observers.get(i)).forEach(observer -> observer.update(color));
        for(int i = 0 ; i <  observers.size(); i++) {
            Observer o = (Observer) observers.get(i);
            o.update(color);
        }
    }
}
public class Main extends JFrame {

    ColorPanel color_panel;
    Color current_color;

    public static void main(String[] args) {
        Main application = new Main("trying stuff");
        application.setSize(800, 600);
        application.setVisible(true);
    }

    public  Main(String tittle) {
        super(tittle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // setLayout(new GridLayout(2, 2));
        current_color = new Color(0, 0, 0);
        color_panel = new ColorPanel(current_color);
        setLayout(new FlowLayout());
        JPanel swatchPanel = new JPanel();
        RGBSwatch rgbSwatch = new RGBSwatch(current_color, color_panel);
        HSVSwatch hsvSwatch = new HSVSwatch(current_color, color_panel);
        CMYKSwatch cmykSwatch = new CMYKSwatch(current_color, color_panel);
        swatchPanel.setLayout(new GridLayout(3, 0));
        swatchPanel.add(rgbSwatch);
        swatchPanel.add(hsvSwatch);
        swatchPanel.add(cmykSwatch);



        color_panel.registerObserver(rgbSwatch);
        color_panel.registerObserver(cmykSwatch);
        color_panel.registerObserver(hsvSwatch);

        getContentPane().add(color_panel);
        getContentPane().add(swatchPanel);
    }
}
