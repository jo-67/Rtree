import java.util.List;

public interface INode {
    Double getMinX();
    Double getMaxX();
    Double getMinY();
    Double getMaxY();
    Double centerX();

    List<Rectangle> search(Rectangle r);

    boolean intersect(Rectangle r);

    Double centerY();

    int getHilbertCurvePosition();
    int hasN();
}
