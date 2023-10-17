import java.util.List;

public interface INode {
    Integer getMinX();
    Integer getMaxX();
    Integer getMinY();
    Integer getMaxY();
    Integer centerX();

    SearchResult search(Rectangle r, int counter);

    List<Rectangle> search(Rectangle r);

    boolean intersect(Rectangle r);

    Integer centerY();

    int getHilbertCurvePosition();
    int hasN();
}
