package main;

import enemy.enemy_comando;
import objek.Heart;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjek() {

    }

    public void setEnemy(){
        int count = 0;
        for(int row = 0; row < gp.maxWorldRow; row++){
            for(int col = 0; col < gp.maxWorldCol; col++) {
                if (gp.tileM.mapTileNum[col][row] == 2) { // kalau nilainya 2
                    if (count < gp.enemy.length) {
                        gp.enemy[count] = new enemy_comando(gp);
                        gp.enemy[count].worldX = gp.tileSize * col;
                        gp.enemy[count].worldY = gp.tileSize * row;
                        count++;
                    }
                }
            }
    }   }
}
/*    public void setEnemy(){
        gp.enemy[0] = new enemy_comando(gp);
        gp.enemy[0].worldX = gp.player.worldX + 50;
        gp.enemy[0].worldX = gp.player.worldX + 50;

        gp.enemy[1] = new enemy_comando(gp);
        gp.enemy[1].worldX = gp.tileSize*23;
        gp.enemy[1].worldX = gp.tileSize*36;
    }*/