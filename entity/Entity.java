package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    public GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public int attackSpeed1 = 3;
    public int attackSpeed2 = 2;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, breath1, breath2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2,
            attackRight1, attackRight2, attackleft1, attackleft2;
    public String direction;

    public String name;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int silenceCounter = 0;
    public int silenceNum = 1;

    public Rectangle collisionArea;
    public boolean collisionOn = false;
    public boolean attacking = false;


    public int actionLockCounter = 0;

    public int maxLife;
    public int life;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public Entity(GamePanel gp){
        this.gp = gp;
        collisionArea = new Rectangle(8, 16, 32, 32); // atau nilai lain sesuai ukuran entitas

    }

    public BufferedImage setup(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction() {}
    public void update(){
        setAction();

        collisionOn = false;
        gp.cCol.checkTile(this);

        if(collisionOn == false){
            switch (direction){
                case "up": worldY -=speed; break;
                case "down": worldY +=speed; break;
                case "left": worldX -=speed; break;
                case "right": worldX +=speed; break;
            }
        }

        spriteCounter++;
        if(spriteCounter >12){
            if(spriteNum==1){
                spriteNum = 2;
            } else if(spriteNum == 2){
                spriteNum=1;
            }
            spriteCounter = 0;
        }

    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch(direction) {
            case "up": image = up1; break;
            case "down": image = down1; break;
            case "left": image = left1; break;
            case "right": image = right1; break;
        }

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(image != null)
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
