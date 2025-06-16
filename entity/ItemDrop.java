package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

//buat item drop kek nambah 1 heart, damage lebih sakit
public class ItemDrop extends Entity{
    public enum ItemType {HEART, POWER}
    public ItemType type;
    public boolean pickedUp = false;

    public BufferedImage image;

    public ItemDrop(GamePanel gp, ItemType type, int WorldX, int WorldY) {
        super(gp);
        this.type = type;
        this.worldX = worldX;
        this.worldY = worldY;
        this.solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
        this.collisionOn = false;

        switch (type){
            case HEART:
                image = setup("/Objek_des/heart_full.png", gp.tileSize, gp.tileSize);
                break;
            case POWER:
                image = setup("/Objek_des/knife.png", gp.tileSize, gp.tileSize);
                break;
        }

    }
}
