package objek;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;

import java.io.IOException;

public class Heart extends SuperObjek {
    GamePanel gp;
    public UtilityTool uTool = new UtilityTool();

    public Heart(GamePanel gp) {
        this.gp = gp;
        name = "Heart";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Objek_des/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/Objek_des/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/Objek_des/heart_blank.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
