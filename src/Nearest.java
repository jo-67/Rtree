import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Nearest {
    private List<Rectangle> rectangleList;
    private int maxChildren;
    public Nearest(List<Rectangle> rectangleList, int MaxChildren) {
        this.rectangleList = rectangleList;
        this.maxChildren = MaxChildren;
    }

    public RTree createRTree() {
        // n rectangulos, M cantidad maxima de hijos
        // ordena rectangulos en base a x del centro
        // junta en grupos de tamaño M
        // se forman n/m nodos
        // de cada grupo (nodo) de toma el centro y se ordena
        // se llega hasta tener 1 nodo padre central

        orderByCenterX(rectangleList);

        List<INode> nodes = new ArrayList<>();
        Node current = null;
        int m = 0;
        for (int i = 0; i < rectangleList.size(); i++) {
            if (m == maxChildren) {
                m = 0;
                current.createMbr(); // Crea el rectangulo MBR
                nodes.add(current);
            }
            if (m==0) {
                current = new Node();

            }
            current.addLeaf(rectangleList.get(i));
            m++;
        }
        current.createMbr();
        nodes.add(current); // añade el ultimo

        if (rectangleList.size() < maxChildren) {
            // todos estan dentro de un nodo
            return new RTree(current, maxChildren);
        }
        // sino, debe seguir agrupando a los nodos

        return groupNodes(nodes);
    }
    public RTree groupNodes(List<INode> nodeList) {
        if (nodeList.size() < maxChildren) {
            // todos estan dentro de un nodo
            Node root = new Node(nodeList);
            return new RTree(root, maxChildren);
        }

        orderByNodeCenterX(nodeList);
        List<INode> children = new ArrayList<>();
        Node current = null;
        int m = 0;
        for (int i = 0; i < nodeList.size(); i++) {
            if (m == maxChildren) {
                m = 0;
                current.createMbr(); // Crea el rectangulo MBR
                children.add(current);
            }
            if (m==0) {
                current = new Node();
            }
            current.addChild(nodeList.get(i));
            m++;
        }
        if (current != null) {
            current.createMbr();
            children.add(current); // añade el ultimo
        }
        return groupNodes(children);
    }
    public void orderByCenterX(List<Rectangle> lRec) {
        Comparator<Rectangle> compareByCenterX =
                Comparator.comparing(Rectangle::centerX);
        // (Rectangle r1, Rectangle r2) -> r1.centerX().compareTo( r2.centerX() );
        Collections.sort(lRec, compareByCenterX);
    }
    public void orderByNodeCenterX(List<INode> lRec) {
        Comparator<INode> compareByCenterX =
                Comparator.comparing(INode::centerX);
        lRec.sort(compareByCenterX);
    }

    public List<Rectangle> getRectangleList() {
        return rectangleList;
    }
}
