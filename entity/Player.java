package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2); // koordinat dimana background akan digambarkan
        screenY = gp.screenHight/2 - (gp.tileSize/2);

        collisionArea = new Rectangle(8, 16, 32, 32);

        solidArea = new Rectangle(8, 16, 32, 32);

        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;

        setDefaultValue();
        getPlayerImage();
    }
    public void setDefaultValue() {
        worldX = gp.tileSize * 42; // posisi karakter
        worldY = gp.tileSize * 45;
        speed = 4;
        direction = "down";

        maxLife = 6;
        life = maxLife;
    }
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Player_des/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Player_des/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Player_des/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Player_des/down2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Player_des/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Player_des/right2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Player_des/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Player_des/left4.png"));
            breath1 = ImageIO.read(getClass().getResourceAsStream("/Player_des/stop1.png"));
            breath2 = ImageIO.read(getClass().getResourceAsStream("/Player_des/stop2.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.rightPressed) {
                direction = "right";
            } else if (keyH.leftPressed) {
                direction = "left";

            }

            collisionOn = false;
            gp.cCol.checkTile(this);

            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 15) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else {
            direction = "no";

            silenceCounter++;
            if (silenceCounter > 20) {
                if (silenceNum == 1) {
                    silenceNum = 2;
                }
                else if (silenceNum == 2) {
                    silenceNum = 1;
                }
                silenceCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "no":
                if (silenceNum == 1) {
                    image = breath1;
                }
                if (silenceNum == 2) {
                    image = breath2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
