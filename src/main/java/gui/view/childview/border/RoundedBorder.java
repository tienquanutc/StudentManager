package gui.view.childview.border;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {
    private int radius;
    private Color color;

    public RoundedBorder(Color color, int radius) {
        this.color = color;
        this.radius = radius;
    }

    public RoundedBorder(int radius) {
        this(null, radius);
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius - 1, this.radius-1, this.radius, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        if (color != null) {
            g2d.setColor(color);
        }
        g2d.drawRoundRect(x, y, width - 1 ,height - 1, radius, radius);
    }
}