package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends SuperObject {
    public OBJ_Door(){
        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/object_des/door4x1_1.gif"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
