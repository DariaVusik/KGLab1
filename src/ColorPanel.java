import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ColorPanel extends JPanel implements Subject{

    private Color color;

    private final ArrayList<Object> observers;


    public Color getColor() {
        return color;
    }

    public ColorPanel(Color color) {
        this.color = color;
        setPreferredSize(new Dimension(200, 200));
        observers = new ArrayList<>();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    public void setColor(Color color) {

        this.color = color;
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
        for (Object o : observers) {
            Observer observer = (Observer) o;
            if (observer.is_change_source()) {
                continue;
            }
            observer.update();
        }
    }

    public void color_changed() {
        notifyObservers();

    }



}
