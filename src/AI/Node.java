package AI;

public class Node {

    Node Parent;
    public int col;
    public int row;
    int gCost;
    int hCost;
    int fCost;
    boolean solid;
    boolean open;
    boolean checked;

    public  Node(int col, int row){
        this.col = col;
        this.row = row;
    }
}
