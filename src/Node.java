import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Node extends AbstractNode implements INode{
    private List<INode> children;
    //private Rectangle rectangle;

    public Node() {
        super();
        children = new ArrayList<>();
    }
    public Node(List<INode> children) {
        super();
        this.children = children;
        createMbr();
    }
    public void addLeaf(Rectangle rec) {
        Leaf leaf = new Leaf(rec);
        children.add(leaf);
    }
    public void addChild(INode node) {
        children.add(node);
    }
    public void createMbr() {
        int n = 0; //valor para construir curva
        Comparator<INode> compareByMinX =
                Comparator.comparing(INode::getMinX);
        children.sort(compareByMinX);
        Double minX = children.get(0).getMinX();
        Comparator<INode> compareByMaxX =
                Comparator.comparing(INode::getMaxX);
        children.sort(compareByMaxX);
        double maxX = children.get(children.size()-1).getMaxX();

        Comparator<INode> compareByMinY =
                Comparator.comparing(INode::getMinY);
        children.sort(compareByMinY);
        double minY = children.get(0).getMinY();
        Comparator<INode> compareByMaxY =
                Comparator.comparing(INode::getMaxY);
        children.sort(compareByMaxY);
        double maxY = children.get(children.size()-1).getMaxY();

        if (children.get(0).hasN() != 0){ //si tiene un n lo agregamos
            n = children.get(0).hasN();
        }

        rectangle = new Rectangle(minX,minY, maxX, maxY, n);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public List<INode> getChildren() {
        return children;
    }

    @Override
    public SearchResult search(Rectangle r, int counter) {
        List<Rectangle> rectangleList = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).intersect(r)) {
                SearchResult add = children.get(i).search(r,0);
                rectangleList.addAll(add.getRectangleList());
                counter = counter + add.getCounter(); // añade numero de nodos accedidos en add
            }
                counter = counter + 1; // añade haber accedido al nodo
        }

        return new SearchResult(rectangleList,counter);
    }

    public byte[] toByteArray(int maxChildren) {
        // id [ id , id, ... 0,0,0]
        int id = this.hashCode();
        List<Integer> array = new ArrayList<>();
        array.add(id);
        for (int i = 0; i < children.size(); i++) {
            array.add(children.get(i).hashCode());
        }
        for (int i = 0; i < maxChildren - children.size(); i++) {
            array.add(-1);
        }
        return DiskHandler.intToByteArray(array);
    }

    public boolean equals(Node node) {
        if (this.rectangle.equals(node.rectangle) &&
                this.children.size() == node.children.size()) {
            for (int i = 0; i < children.size(); i++) {
                if ( ! this.children.get(i).equals(node.children.get(i))) {
                    return false;
                }
            }
            return true;

        } else {
            return false;
        }
    }
}