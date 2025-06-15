package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player2 extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player2(GamePanel gp, KeyHandler keyH) {
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
        worldX = gp.tileSize * 25; // posisi karakter
        worldY = gp.tileSize * 25;
        speed = 4;
        direction = "down";

        maxLife = 6;
        life = maxLife;
    }
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Player_des/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Player_des/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Player_des/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Player_des/boy_down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Player_des/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Player_des/boy_right_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Player_des/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Player_des/boy_left_2.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {
        if (keyH.wPressed || keyH.aPressed || keyH.sPressed || keyH.dPressed) {
            if (keyH.wPressed) {
                direction = "up";
            } else if (keyH.aPressed) {
                direction = "left";
            } else if (keyH.sPressed) {
                direction = "down";
            } else if (keyH.dPressed) {
                direction = "right";

            }

            collisionOn = false;
            gp.cCol.checkTile(this);
            gp.eHand.checkEvent();

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
                    image = up1;
                }
                if (silenceNum == 2) {
                    image = up2;
                }
                break;
        }

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);


    }
}
