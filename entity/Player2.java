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

        screenX = gp.getScreenWidth()/2 - (gp.tileSize/2); // koordinat dimana background akan digambarkan
        screenY = gp.getScreenHeight()/2 - (gp.tileSize/2);

        collisionArea = new Rectangle(8, 16, 16, 16);

        solidArea = new Rectangle(8, 16, 16, 16);

        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValue();
        getPlayerImage();
        getPlayerAttackImage();
    }
    public void setDefaultValue() {
        worldX = gp.tileSize * 26; // posisi karakter
        worldY = gp.tileSize * 24;
        speed = 4;
        direction = "down";

        maxLife2 = 6;
        life2 = maxLife2;
    }
    public void getPlayerImage() {
            up1 = setup("/Player_des/orc_walk1.png", gp.tileSize, gp.tileSize);
            up2 = setup("/Player_des/orc_walk2_up.png", gp.tileSize, gp.tileSize);
            down1 = setup("/Player_des/orc_walk1_down.png", gp.tileSize, gp.tileSize);
            down2 = setup("/Player_des/orc_walk2_down.png", gp.tileSize, gp.tileSize);
            right1 = setup("/Player_des/orc_walk1_right.png", gp.tileSize, gp.tileSize);
            right2 = setup("/Player_des/orc_walk2_right.png", gp.tileSize, gp.tileSize);
            left1 = setup("/Player_des/orc_walk1_left.png", gp.tileSize, gp.tileSize);
            left2 = setup("/Player_des/orc_walk2_left.png", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage(){
        attackUp1 = setup("/Player_des/orc_attack_up.png", gp.tileSize, gp.tileSize);
        attackUp2 = setup("/Player_des/orc_attack_up.png", gp.tileSize, gp.tileSize);
        attackDown1 = setup("/Player_des/orc_attack_down.png", gp.tileSize, gp.tileSize);
        attackDown2 = setup("/Player_des/orc_attack_down.png", gp.tileSize, gp.tileSize);
        attackLeft1 = setup("/Player_des/orc_attack_left.png", gp.tileSize, gp.tileSize);
        attackLeft2 = setup("/Player_des/orc_attack_left.png", gp.tileSize, gp.tileSize);
        attackRight1 = setup("/Player_des/orc_attack_right.png", gp.tileSize, gp.tileSize);
        attackRight2 = setup("/Player_des/orc_attack_right.png", gp.tileSize, gp.tileSize);
        dying2 = setup("/Player_des/orc-die.png", gp.tileSize, gp.tileSize);
    }

    @Override
    public void update() {

        if (attacking == true){
            attacking2();
            return;
        }

        if (keyH.ePressed == true) {
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
        if (life2 <= 0) {
            direction = "dying2";
            spriteNum = 1;
            attacking = false;
            return;
        }

    }

    public void attacking2() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            int attackX = worldX;
            int attackY = worldY;

            switch(direction) {
                case "up": attackY -= attackArea.height; break;
                case "down": attackY += attackArea.height; break;
                case "left": attackX -= attackArea.width; break;
                case "right": attackX += attackArea.width; break;
            }

            Rectangle attackBox = new Rectangle(attackX, attackY, attackArea.width, attackArea.height);

            Rectangle targetBox = new Rectangle(
                    gp.player.worldX + gp.player.solidArea.x,
                    gp.player.worldY + gp.player.solidArea.y,
                    gp.player.solidArea.width,
                    gp.player.solidArea.height
            );

            if (attackBox.intersects(targetBox) && !playerHitRegistered) {
                gp.player.life -= 1;
                playerHitRegistered = true;

                if (gp.player.life <= 0) {
                    gp.player.direction = "dying2";
                }
            }


        }

        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
            playerHitRegistered = false;
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
                image = dying;
                break;
            case "dying2":
                image = dying2;
                break;

        }

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (direction.equals("dying2")) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        } else if (attacking) {
            switch (direction) {
                case "up":
                    g2.drawImage(image, screenX, screenY - gp.tileSize, gp.tileSize, gp.tileSize, null);
                    break;
                case "down":
                    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                    break;
                case "left":
                    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                    break;
                case "right":
                    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                    break;
                case "no":
                    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                    break;
            }
        } else {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
