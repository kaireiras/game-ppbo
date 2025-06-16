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

    boolean playerHitRegistered = false;


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

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValue();
        getPlayerImage();
        getPlayerAttackImage();
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
            up1 = setup("/Player_des/boy_up_1.png", gp.tileSize, gp.tileSize);
            up2 = setup("/Player_des/boy_up_2.png", gp.tileSize, gp.tileSize);
            down1 = setup("/Player_des/boy_down_1.png", gp.tileSize, gp.tileSize);
            down2 = setup("/Player_des/boy_down_2.png", gp.tileSize, gp.tileSize);
            right1 = setup("/Player_des/boy_right_1.png", gp.tileSize, gp.tileSize);
            right2 = setup("/Player_des/boy_right_2.png", gp.tileSize, gp.tileSize);
            left1 = setup("/Player_des/boy_left_1.png", gp.tileSize, gp.tileSize);
            left2 = setup("/Player_des/boy_left_2.png", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage(){
        attackUp1 = setup("/Player_des/boy_attack_up_1.png", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/Player_des/boy_attack_up_2.png", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/Player_des/boy_attack_down_1.png", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/Player_des/boy_attack_down_2.png", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/Player_des/boy_attack_left_1.png", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/Player_des/boy_attack_left_2.png", gp.tileSize*2, gp.tileSize);
        attackRight1 = setup("/Player_des/boy_attack_right_1.png", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/Player_des/boy_attack_right_2.png", gp.tileSize*2, gp.tileSize);
    }

    public void update() {

        if (attacking == true){
            attacking2();
            return;
        }

        if (keyH.ePressed == true) {
            System.out.println("HIDOP JOKOWEEEE");
            attacking = true;
            keyH.ePressed = false;
        }

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

    public void attacking2() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            // SET ATTACK AREA
            int attackX = worldX;
            int attackY = worldY;

            switch(direction) {
                case "up": attackY -= attackArea.height; break;
                case "down": attackY += attackArea.height; break;
                case "left": attackX -= attackArea.width; break;
                case "right": attackX += attackArea.width; break;
            }

            Rectangle attackBox = new Rectangle(attackX, attackY, attackArea.width, attackArea.height);

            // CEK TABRAKAN DENGAN PLAYER 2
            // Cek apakah kena player1
            Rectangle targetBox = new Rectangle(
                    gp.player.worldX + gp.player.solidArea.x,
                    gp.player.worldY + gp.player.solidArea.y,
                    gp.player.solidArea.width,
                    gp.player.solidArea.height
            );

            if (attackBox.intersects(targetBox) && !playerHitRegistered) {
                gp.player2.life -= 1;
                playerHitRegistered = true;
                System.out.println(">> Player 2 terkena serangan Player 1!");
            }


        }

        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
            playerHitRegistered = false; // Reset biar bisa hit lagi nanti
        }

    }




    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if(attacking == false){
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }
                if(attacking == true){
//                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}
                }
                break;
            case "down":
                if(attacking == false){
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                }
                if(attacking == true){
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                }
                break;
            case "right":
                if(attacking == false){
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }
                if(attacking == true){
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }
                break;
            case "left":
                if(attacking == false){
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                }
                if(attacking == true){
//                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }
                break;
            case "no":
                if(attacking == true){
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                }else{
                    if (silenceNum == 1) {image = down1;}
                    if (silenceNum == 2) {image = down2;}
                }
                break;
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
