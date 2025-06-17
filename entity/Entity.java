package entity;

import main.GamePanel;
import main.Metode;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity implements Metode {
    public GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public int attackSpeed1 = 11;
    public int attackSpeed2 = 12;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, dying, dying2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2,
            attackRight1, attackRight2;
    public String direction;

    public String name;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int silenceCounter = 0;
    public int silenceNum = 1;

    public Rectangle collisionArea;
    public boolean collisionOn = false;
    public boolean attacking = false;

    int dyingCounter = 0;
    public int actionLockCounter = 0;

    public int maxLife;
    public int life;

    public int maxLife2;
    public int life2;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    public Entity(GamePanel gp){
        this.gp = gp;
        collisionArea = new Rectangle(8, 16, 32, 32); // atau nilai lain sesuai ukuran entitas

    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction() {

    }

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

        if(attacking) {
            switch(direction) {
                case "up":
                    image = (spriteNum == 1) ? attackUp1 : attackUp2;
                    break;
                case "down":
                    image = (spriteNum == 1) ? attackDown1 : attackDown2;
                    break;
                case "left":
                    image = (spriteNum == 1) ? attackLeft1 : attackLeft2;
                    break;
                case "right":
                    image = (spriteNum == 1) ? attackRight1 : attackRight2;
                    break;
            }
        } else {
            switch(direction) {
                case "up": image = (spriteNum == 1) ? up1 : up2; break;
                case "down": image = (spriteNum == 1) ? down1 : down2; break;
                case "left": image = (spriteNum == 1) ? left1 : left2; break;
                case "right": image = (spriteNum == 1) ? right1 : right2; break;
            }
        }


        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(attacking) {
            switch (direction) {
                case "up":
                    g2.drawImage(image, screenX, screenY - gp.tileSize, gp.tileSize, gp.tileSize * 2, null);
                    break;
                case "down":
                    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize * 2, null);
                    break;
                case "left":
                    g2.drawImage(image, screenX - gp.tileSize, screenY, gp.tileSize * 2, gp.tileSize, null);
                    break;
                case "right":
                    g2.drawImage(image, screenX, screenY, gp.tileSize * 2, gp.tileSize, null);
                    break;
                case "no":
                    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize * 2, null);
                    break;
            }
        } else {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

    }
}
