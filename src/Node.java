import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Node extends AbstractNode implements INode, Serializable {
    private List<INode> children;
    private Rectangle rectangle;

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
        Integer minX = children.get(0).getMinX();
        Comparator<INode> compareByMaxX =
                Comparator.comparing(INode::getMaxX);
        children.sort(compareByMaxX);
        int maxX = children.get(children.size()-1).getMaxX();

        Comparator<INode> compareByMinY =
                Comparator.comparing(INode::getMinY);
        children.sort(compareByMinY);
        int minY = children.get(0).getMinY();
        Comparator<INode> compareByMaxY =
                Comparator.comparing(INode::getMaxY);
        children.sort(compareByMaxY);
        int maxY = children.get(children.size()-1).getMaxY();

        if (children.get(0).hasN() != 0){ //si tiene un n lo agregamos
            n = children.get(0).hasN();
        }

        rectangle = new Rectangle(minX,minY, maxX, maxY, n);
        super.setRectangle(rectangle);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public List<INode> getChildren() {
        return children;
    }

    @Override
    public SearchResult search(Rectangle r, long counter) {
        List<Rectangle> rectangleList = new ArrayList<>();
        if (! this.rectangle.intersect(r)) { // si no intersecta, no se busca en los nodos
            return new SearchResult(rectangleList, counter + 1);
        }
        for (int i = 0; i < children.size(); i++) {
            SearchResult add = children.get(i).search(r,0);
            rectangleList.addAll(add.getRectangleList());
            counter = counter + add.getCounter(); // añade numero de nodos accedidos en add
        }

        return new SearchResult(rectangleList,counter + 1); // +1 al acceder a este nodo
    }


    public void write() {
        try {
            MemoryHandler.writeNode(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}