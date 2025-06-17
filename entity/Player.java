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

    boolean playerHitRegistered = false;


    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2); // koordinat dimana background akan digambarkan
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

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
        worldX = gp.tileSize * 22; // posisi karakter
        worldY = gp.tileSize * 24;
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
        dying = setup("/Player_des/down1.png", gp.tileSize, gp.tileSize);
    }

    public void update() {

        if (attacking == true){
            attacking();
            return;
        }

        if (keyH.kPressed == true) {
            attacking = true;
            keyH.kPressed = false;
        }

        else if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
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
        if (life <= 0) {
            direction = "dying";
            spriteNum = 1;
            attacking = false; // biar gak nyerang lagi
            return;
        }


    }

    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            // Tentukan area serang
            int attackX = worldX;
            int attackY = worldY;

            switch(direction) {
                case "up": attackY -= attackArea.height; break;
                case "down": attackY += attackArea.height; break;
                case "left": attackX -= attackArea.width; break;
                case "right": attackX += attackArea.width; break;
            }

            Rectangle attackBox = new Rectangle(attackX, attackY, attackArea.width, attackArea.height);

            // Cek apakah kena player2
            // Cek apakah kena player2
            Rectangle targetBox = new Rectangle(
                    gp.player2.worldX + gp.player2.solidArea.x,
                    gp.player2.worldY + gp.player2.solidArea.y,
                    gp.player2.solidArea.width,
                    gp.player2.solidArea.height
            );

            if (attackBox.intersects(targetBox) && !playerHitRegistered) {
                gp.player2.life2 -= 1;
                playerHitRegistered = true;
                System.out.println(">> Player 2 terkena serangan Player 1!");
                if (gp.player2.life2 <= 0) {
                    gp.player2.direction = "dying";
                    System.out.println(" PLAYER 2 MATIIIII");
                }
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

        switch (direction) {
            case "up":
                if(attacking == false){
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }
                if(attacking == true){
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
            case "dying":
                if (spriteNum == 1) {image = dying;}
                break;
            case "dying2":
                if (spriteNum == 1) {image = dying2;}
                break;


        }

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
                case "dying":
                    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
        } else {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
