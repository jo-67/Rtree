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
    public List<Rectangle> search(Rectangle r) {
        List<Rectangle> rectangleList = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).intersect(r)) {
                List<Rectangle> add = children.get(i).search(r);
                rectangleList.addAll(add);
            }
        }

        return rectangleList;
    }
}