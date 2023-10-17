import java.io.Serializable;
import java.util.List;

public class RTree implements Serializable {
    private Node root;
    private int maxChildren;
    public RTree(Node rootNode, int MaxChildren) {
        root = rootNode;
        maxChildren = MaxChildren ;
    }
    public int getMaxChildren() {
        return maxChildren;
    }

    public SearchResult search(Rectangle r) {
        return root.search(r,1);
    }

    public boolean equals(RTree rTree) {
        return root.equals(rTree.root);
    }
}