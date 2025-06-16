
package main;

import objek.Heart;
import objek.SuperObjek;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    BufferedImage heart_full, heart_half, heart_blank;
    public String currentDialogue = "";


    public UI(GamePanel gp) {
        this.gp = gp;

        SuperObjek heart = new Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if (gp.gameState == gp.playState || gp.gameState == gp.pauseState) {
            drawPlayerLife();
        }
    }

    public void drawPlayerLife() {
        int tile = gp.tileSize;
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;

        int x2 = gp.screenWidth - (tile * 3) - tile / 2;
        int y2 = gp.tileSize / 2;

        int i = 0;

        // Step 1: Gambar heart kosong sebanyak maxLife / 2
//        while (i < gp.player.maxLife / 2) {
//            g2.drawImage(heart_blank, x, y, null);
//            g2.drawImage(heart_blank, x2, y2, null);
//            i++;
//            x += gp.tileSize;
//            x2 += gp.tileSize;
//        }

        // Step 2: Gambar isi heart (full dan half) sesuai life
        i = 0;
        int lifeNow = gp.player.life;
        int lifeNow2 = gp.player2.life;

        while (lifeNow > 0) {
            if (lifeNow >= 2) {
                g2.drawImage(heart_full, x, y, null);
                lifeNow -= 2;
            } else {
                g2.drawImage(heart_half, x, y, null);
                lifeNow -= 1;

            }
            x += gp.tileSize;
        }
        while (lifeNow2 > 0) {
            if (lifeNow2 >= 2) {
                g2.drawImage(heart_full, x2, y2, null);
                lifeNow2 -= 2;
            } else {
                g2.drawImage(heart_half, x2, y2, null);
                lifeNow2 -= 1;

            }
            x2 += gp.tileSize;
        }
    }
}
