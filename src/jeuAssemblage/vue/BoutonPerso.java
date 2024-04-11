package jeuAssemblage.vue;

import java.awt.*;
import javax.swing.*;

public class BoutonPerso extends JPanel {

    private String content;
    private int width, height;
    private Color c;
    private static Font font = new Font("Arial", Font.PLAIN, 25);

    public BoutonPerso(String content, int width, int height) {
        this.content = content;
        this.setPreferredSize(new Dimension(width, height));
        this.width = width;
        this.height = height;
        this.c = Color.BLACK;

    }

    public void setColor(Color c) {
        this.c = c;
    }

    public String getName() {
        return this.content;
    }

    public void setText(String content) {
        this.content = content;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setStroke(new BasicStroke(3));
		g2.setColor(c);
        g2.setFont(font);
		g2.drawRect(0, 0, width, height);
		g2.drawString(content, width / 2 - (g2.getFontMetrics().stringWidth(content)) / 2, 3 * height / 4);
    }

}