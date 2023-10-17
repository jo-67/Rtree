import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HilbertCurve {

    private List<Rectangle> rectangleList;

    private int maxChildren;

    public HilbertCurve(List<Rectangle> rectangleList, int MaxChildren) {
        this.rectangleList = rectangleList;
        this.maxChildren = MaxChildren;
    }

    public static int xy2d(int n, int x, int y) {
        int rx, ry, s, d = 0;
        for (s = n / 2; s > 0; s /= 2) {
            rx = (x & s) > 0 ? 1 : 0;
            ry = (y & s) > 0 ? 1 : 0;
            d += s * s * ((3 * rx) ^ ry);
            rot(n, new int[]{x}, new int[]{y}, rx, ry);
        }
        return d;
    }

    public static void d2xy(int n, int d, int[] x, int[] y) {
        int rx, ry, s, t = d;
        x[0] = y[0] = 0;
        for (s = 1; s < n; s *= 2) {
            rx = 1 & (t/2);
            ry = 1 & (t ^ rx);
            rot(s, x, y, rx, ry);
            x[0] += s * rx;
            y[0] += s * ry;
            t /= 4;
        }
    }

    public static void rot(int n, int[] x, int[] y, int rx, int ry) {
        int t;
        if (ry == 0) {
            if (rx == 1) {
                x[0] = n - 1 - x[0];
                y[0] = n - 1 - y[0];
            }
            t = x[0];
            x[0] = y[0];
            y[0] = t;
        }
    }

    public void orderByHilbertCurve(List<Rectangle> lRec){
        Comparator<Rectangle> compareByD =
                Comparator.comparing
                        (Rectangle::getHilbertCurvePosition);
        lRec.sort(compareByD);
    }

    public void orderByNodeHilbert(List<INode> lRec){
        Comparator<INode> comparatorByD =
                Comparator.comparing(INode::getHilbertCurvePosition);
        //System.out.println(comparatorByD);
        lRec.sort(comparatorByD);
    }

    public List<Rectangle> getRectangleList() {return rectangleList;}
    public RTree createRTreeH() {
        // n rectangulos, M cantidad maxima de hijos
        // ordena rectangulos en base a x del centro
        // junta en grupos de tamaño M
        // se forman n/m nodos
        // de cada grupo (nodo) de toma el centro y se ordena
        // se llega hasta tener 1 nodo padre central
        orderByHilbertCurve(rectangleList);

        List<INode> nodes = new ArrayList<>();

        Node current = null;
        int m = 0;
        for(int i = 0; i < rectangleList.size(); i++) {
            if (m == maxChildren){
                m = 0;
                current.createMbr(); //crea el rectangulo MBR
                nodes.add(current);
            }
            if (m==0){
                current = new Node();
            }
            current.addLeaf(rectangleList.get(i));
            m++;
        }
        current.createMbr();
        nodes.add(current); //añade el ultimo

        if (rectangleList.size()<maxChildren){
            //todos estan dentro de un nodo
            return new RTree(current,maxChildren);
        }
        //sino, debe seguir agrupando a los nodos
        return groupNodesH(nodes);
    }

    public RTree groupNodesH(List<INode> nodeList) {
        if (nodeList.size() < maxChildren){
            //todos estan dentro de un nodo
            Node root = new Node(nodeList);
            return new RTree(root, maxChildren);
        }

        orderByNodeHilbert(nodeList);
        List<INode> children = new ArrayList<>();
        Node current = null;
        int m = 0;
        for (int i = 0; i<nodeList.size(); i++){
            if (m==maxChildren){
                m = 0;
                current.createMbr(); //crea el rectangulo MBR
                children.add(current);
            }
            if (m==0){
                current = new Node();
            }
            current.addChild(nodeList.get(i));
            m++;
        }
        if (current != null){
            current.createMbr();
            children.add(current); //añade el ultimo
        }
        return groupNodesH(children);
    }


}
