
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
    Font arial_40;
    public int commandNum = 0;


    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);

        SuperObjek heart = new Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        if (gp.gameState == gp.playState || gp.gameState == gp.pauseState) {
            drawPlayerLife();
        }
    }

    public void drawTitleScreen() {
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
        String text = "JUDUL GAME";
        int x = centeredX(text);
        int y = gp.tileSize*3;

        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

        text = "NEW GAME";
        x = centeredX(text);
        y += gp.tileSize*4.5;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT";
        x = centeredX(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x-gp.tileSize, y);
        }
    }

    public int centeredX(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
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
        int lifeNow2 = gp.player2.life2;

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
