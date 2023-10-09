import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HilbertCurve {

    private List<Rectangle> rectangleList;

    private int maxChildren;

    private int HilbertSize; //tiene que ser potencia de 2

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
                Comparator.comparing(INode);
        lRec.sort(comparatorByD);
    }
    public RTree createRTreeH() {

    }

    public RTree groupNodesH(List<INode> nodeList) {
        if (nodeList.size() < maxChildren){
            Node root = new Node(nodeList);
            return new RTree(root, maxChildren);
        }
    }


}
