package AI;

import Entity.Entity;
import Main.GamePanel;

import java.util.ArrayList;

public class Pathfinder {

    GamePanel gp;
    Node[][] node;
    public ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();

    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public Pathfinder(GamePanel gp){
        this.gp = gp;
        instantiateNodes();
    }

    public void instantiateNodes(){
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){

            node[col][row] = new Node(col,row);

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes(){

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;

            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
            //reset settings
            openList.clear();
            pathList.clear();
            goalReached = false;
            step = 0;
        }

    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow,Entity entity) {

        resetNodes();

        //Set start and goal nodes
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

            //Setting solid nodes
            // check tiles
            int tileNum = gp.tileManager.mapTileNum[gp.currentMap][col][row];
            if (gp.tileManager.tile[tileNum].collision == true) {
                node[col][row].solid = true;
            }
            //check interactive tiles
            for (int i = 0; i < gp.interactiveTile[1].length; i++) {
                if (gp.interactiveTile[gp.currentMap][i] != null && gp.interactiveTile[gp.currentMap][i].destructible == true) {
                    int itCol = gp.interactiveTile[gp.currentMap][i].worldX / gp.tileSize;
                    int itRow = gp.interactiveTile[gp.currentMap][i].worldY / gp.tileSize;
                    node[itCol][itRow].solid = true;

                }
            }
            //SET COST
            getCost(node[col][row]);
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }

        }
    }

    public void getCost(Node node){
        //G COST
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        //H COST
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        //F COST
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search(){
        while(goalReached == false && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;
            //CHECK CURRENT NODE
            currentNode.checked = true;
            openList.remove(currentNode);
            //OPEN UP NODE
            if(row - 1 >= 0){
                openNode(node[col][row-1]);
            }
            //OPEN DOWN NODE
            if(row + 1 < gp.maxWorldRow){
                openNode(node[col][row+1]);
            }
            //OPEN LEFT NODE
            if(col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }
            //OPEN RIGHT NODE
            if(col + 1 < gp.maxWorldCol){
                openNode(node[col+1][row]);
            }
            //FIND BEST NODE
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for(int i = 0; i < openList.size(); i++){
                //check node's f cost
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                //IF F COST is equal, check the G COST
                else if(openList.get(i).fCost  == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            //If no node in list, end looop
            if(openList.size() == 0){break;}
            //after loop, set current node to best index
            currentNode = openList.get(bestNodeIndex);
            if(currentNode == goalNode){
                goalReached = true;
                trackPath();
            }
            step++;
        }
            return goalReached;
        }

    public void openNode(Node node){
        if(node.open == false && node.checked == false && node.solid == false){
            node.open = true;
            node.Parent = currentNode;
            openList.add(node);
        }
    }

    public void trackPath(){
        Node current = goalNode;
        while(current != startNode){
            pathList.add(0,current);
            current = current.Parent;
        }
    }

}
