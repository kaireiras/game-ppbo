package enemy;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class enemy_comando extends Entity{
    public enemy_comando(GamePanel gp){
        super(gp);

        name = "Commando";
        speed = 4;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 10;
        solidArea.y = 24;
        solidArea.width = 28;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage(){
        up1 = setup("/enemy_des/comando_knife.png", gp.tileSize, gp.tileSize);
        up2 = setup("/enemy_des/comando_pistol.png", gp.tileSize, gp.tileSize);
        down1 = setup("/enemy_des/Commando_8.png", gp.tileSize, gp.tileSize);
        down2 = setup("/enemy_des/Commando_9.png", gp.tileSize, gp.tileSize);
        right1 = setup("/enemy_des/comando_walk_knife.png", gp.tileSize, gp.tileSize);
        right2 = setup("/enemy_des/comando_walk_pistol.png", gp.tileSize, gp.tileSize);
        left1 = setup("/enemy_des/comando_walk_pistol_left.png", gp.tileSize, gp.tileSize);
        left2 = setup("/enemy_des/comando_walk_knife_left.png", gp.tileSize, gp.tileSize);
    }

    public void setAction(){
        actionLockCounter ++;
        if (actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if (i <= 25){
                direction = "up";
            }

            if (i>25 && i <= 50){
                direction = "down";
            }

            if (i > 50 && i <= 75){
                direction = "left";
            }

            if (i > 75 && i <= 100){
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }
    @Override
    public void update() {
        // Misalnya musuh mengejar player
        if (gp.player.worldX < worldX) {
            direction = "left";
        } else if (gp.player.worldX > worldX) {
            direction = "right";
        }

        // Panggil update() dari Entity
        super.update();
    }
}
