package objek;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

public class Heart extends SuperObjek {
    GamePanel gp;

    public Heart(GamePanel gp) {
        this.gp = gp;
        name = "Heart";
        try {
            image = ImageIO.read(new File("Objek_des/heart_full.png"));
            image2 = ImageIO.read(new File("Objek_des/heart_half.png"));
            image3 = ImageIO.read(new File("Objek_des/heart_blank.png"));

            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
