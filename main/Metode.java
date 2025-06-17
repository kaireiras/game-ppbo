package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Metode {
    public BufferedImage setup(String imagePath, int width, int height);
    public void update();
    public void draw(Graphics2D g2);
}
