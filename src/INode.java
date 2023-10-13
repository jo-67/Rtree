import java.util.List;

public interface INode {
    Double getMinX();
    Double getMaxX();
    Double getMinY();
    Double getMaxY();
    Double centerX();

    SearchResult search(Rectangle r, int counter);

    boolean intersect(Rectangle r);

    Double centerY();

    int getHilbertCurvePosition();
    int hasN();
}
