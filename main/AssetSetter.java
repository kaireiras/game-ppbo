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
        gp.enemy[0] = new enemy_comando(gp);
        gp.enemy[0].worldX = gp.tileSize*23;
        gp.enemy[0].worldX = gp.tileSize*36;

        gp.enemy[1] = new enemy_comando(gp);
        gp.enemy[1].worldX = gp.tileSize*23;
        gp.enemy[1].worldX = gp.tileSize*36;
    }
}
/*    public void setEnemy(){
        gp.enemy[0] = new enemy_comando(gp);
        gp.enemy[0].worldX = gp.player.worldX + 50;
        gp.enemy[0].worldX = gp.player.worldX + 50;

        gp.enemy[1] = new enemy_comando(gp);
        gp.enemy[1].worldX = gp.tileSize*23;
        gp.enemy[1].worldX = gp.tileSize*36;
    }*/