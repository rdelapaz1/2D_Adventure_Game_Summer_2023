package Main;
import Entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.worldX + entity.solidArea.x; //most left position of world plus 8
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY =  entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;
        //Use temporary direction when knockback occurs
        String direction = entity.direction;
        if(entity.knockBack == true){
            direction = entity.knockBackDirection;
        }

        switch (direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.playerSpeed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 =   gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.playerSpeed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 =   gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }

                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.playerSpeed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 =   gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }

                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.playerSpeed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                tileNum2 =   gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }

                break;
        }
    }

    //check if player hits object, if true, return index of object
    public int checkObject(Entity entity, boolean player){
        int index = 999;

        String direction = entity.direction;
        if(entity.knockBack == true){
            direction = entity.knockBackDirection;
        }

        for(int i=0; i<gp.superObject[1].length; i++){
            if(gp.superObject[gp.currentMap][i] != null){
                //get entity's solid area position
                entity.solidArea.x =  entity.worldX + entity.solidArea.x;
                entity.solidArea.y =  entity.worldY + entity.solidArea.y;
                //objects solid area pos.
                gp.superObject[gp.currentMap][i].solidArea.x = gp.superObject[gp.currentMap][i].worldX + gp.superObject[gp.currentMap][i].solidArea.x;
                gp.superObject[gp.currentMap][i].solidArea.y = gp.superObject[gp.currentMap][i].worldY + gp.superObject[gp.currentMap][i].solidArea.y;
                //checking intersection
                switch (direction) {
                    case "up": entity.solidArea.y -= entity.playerSpeed; break;
                    case "down": entity.solidArea.y += entity.playerSpeed; break;
                    case "left": entity.solidArea.x -= entity.playerSpeed; break;
                    case "right": entity.solidArea.x += entity.playerSpeed; break;
                }
                //set x,y back to default values, else solid area would keep increasing
                if(entity.solidArea.intersects(gp.superObject[gp.currentMap][i].solidArea)){
                    //check if object is solid
                    entity.collisionOn = true;
                    index = i;
                }
                entity.solidArea.x = entity.defaultSolidAreaX;
                entity.solidArea.y = entity.defaultSolidAreaY;
                gp.superObject[gp.currentMap][i].solidArea.x = gp.superObject[gp.currentMap][i].defaultSolidAreaX;
                gp.superObject[gp.currentMap][i].solidArea.y = gp.superObject[gp.currentMap][i].defaultSolidAreaY;


            }
        }
        return  index;
    }

    //colllisions w entity or monster
    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;
        String direction = entity.direction;
        if(entity.knockBack == true){
            direction = entity.knockBackDirection;
        }
        for(int i=0; i<target[1].length; i++){
            if(target[gp.currentMap][i] != null){
                //get entity's solid area position
                entity.solidArea.x =  entity.worldX + entity.solidArea.x;
                entity.solidArea.y =  entity.worldY + entity.solidArea.y;
                //objects solid area pos.
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y =target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;
                //checking intersection
                switch (direction) {
                    case "up": entity.solidArea.y -= entity.playerSpeed; break;
                    case "down": entity.solidArea.y += entity.playerSpeed; break;
                    case "left": entity.solidArea.x -= entity.playerSpeed; break;
                    case "right": entity.solidArea.x += entity.playerSpeed; break;
                }
                //set x,y back to default values, else solid area would keep increasing
                if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)){
                    if(target[gp.currentMap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                entity.solidArea.x = entity.defaultSolidAreaX;
                entity.solidArea.y = entity.defaultSolidAreaY;
               target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].defaultSolidAreaX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].defaultSolidAreaY;


            }
        }
        return index;
    }

public boolean checkPlayer(Entity entity){
        boolean contactPlayer = false;

    //get entity's solid area position
    entity.solidArea.x =  entity.worldX + entity.solidArea.x;
    entity.solidArea.y =  entity.worldY + entity.solidArea.y;
    //objects solid area pos.
    gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
    gp.player.solidArea.y =gp.player.worldY + gp.player.solidArea.y;
    //checking intersection
    switch (entity.direction) {

        case "up": entity.solidArea.y -= entity.playerSpeed; break;
        case "down": entity.solidArea.y += entity.playerSpeed;break;
        case "left": entity.solidArea.x -= entity.playerSpeed;break;
        case "right": entity.solidArea.x += entity.playerSpeed;break;
    }
    //set x,y back to default values, else solid area would keep increasing
    if(entity.solidArea.intersects(gp.player.solidArea)){
        entity.collisionOn = true;
        contactPlayer = true;
    }
    entity.solidArea.x = entity.defaultSolidAreaX;
    entity.solidArea.y = entity.defaultSolidAreaY;
    gp.player.solidArea.x = gp.player.defaultSolidAreaX;
   gp.player.solidArea.y = gp.player.defaultSolidAreaY;

   return contactPlayer;
}

}


