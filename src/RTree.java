public class RTree {
    private Node root;
    private int maxChildren;
    public RTree(Node rootNode, int MaxChildren) {
        root = rootNode;
        maxChildren = MaxChildren ;
    }
    public int getMaxChildren() {
        return maxChildren;
    }

}