package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, breath1, breath2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int silenceCounter = 0;
    public int silenceNum = 1;

    public Rectangle collisionArea;
    public boolean collisionOn = false;

    public int maxLife;
    public int life;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;


}
