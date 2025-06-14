
package main;

import objek.Heart;
import objek.SuperObjek;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    BufferedImage heart_full, heart_half, heart_blank;

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
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // Step 1: Gambar heart kosong sebanyak maxLife / 2
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // Step 2: Gambar isi heart (full dan half) sesuai life
        x = gp.tileSize / 2;
        i = 0;
        int lifeNow = 5;

        while (lifeNow > 0) {
            if (lifeNow >= 2) {
                g2.drawImage(heart_full, x, y, null);
                lifeNow -= 2;
            } else {
                g2.drawImage(heart_half, x, y, null);
                lifeNow -= 1;
                System.out.println("HIDUP JOKOWI");
            }
            x += gp.tileSize;
        }
    }
}
