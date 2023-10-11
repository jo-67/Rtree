import java.util.List;

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

    public List<Rectangle> search(Rectangle r) {
        return root.search(r);
    }
}