package main;

import entity.Entity;

public class CollisionCheck {
    GamePanel gp;

    public CollisionCheck(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // Reset collision flag dulu
        entity.collisionOn = false;

        int entityLeftX = entity.worldX + entity.collisionArea.x;
        int entityRightX = entity.worldX + entity.collisionArea.x + entity.collisionArea.width;
        int entityTopY = entity.worldY + entity.collisionArea.y;
        int entityBottomY = entity.worldY + entity.collisionArea.y + entity.collisionArea.height;

        int leftCol, rightCol, topRow, bottomRow;
        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                topRow = (entityTopY - entity.speed) / gp.tileSize;
                leftCol = entityLeftX / gp.tileSize;
                rightCol = entityRightX / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[leftCol][topRow];
                tileNum2 = gp.tileM.mapTileNum[rightCol][topRow];
                break;
            case "down":
                bottomRow = (entityBottomY + entity.speed) / gp.tileSize;
                leftCol = entityLeftX / gp.tileSize;
                rightCol = entityRightX / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[leftCol][bottomRow];
                tileNum2 = gp.tileM.mapTileNum[rightCol][bottomRow];
                break;
            case "left":
                leftCol = (entityLeftX - entity.speed) / gp.tileSize;
                topRow = entityTopY / gp.tileSize;
                bottomRow = entityBottomY / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[leftCol][topRow];
                tileNum2 = gp.tileM.mapTileNum[leftCol][bottomRow];
                break;
            case "right":
                rightCol = (entityRightX + entity.speed) / gp.tileSize;
                topRow = entityTopY / gp.tileSize;
                bottomRow = entityBottomY / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[rightCol][topRow];
                tileNum2 = gp.tileM.mapTileNum[rightCol][bottomRow];
                break;
            default:
                return;
        }

        // Cek tile apakah punya collision
        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }
}
