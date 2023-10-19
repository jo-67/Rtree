import java.util.List;

public abstract class AbstractNode implements INode{
    public Rectangle rectangle;

    public AbstractNode(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    public AbstractNode() {
    }
    public Integer getMinX() {
        return rectangle.getMinX();
    }
    public Integer getMaxX() {
        return rectangle.getMaxX();
    }
    public Integer getMinY() {
        return rectangle.getMinY();
    }
    public Integer getMaxY() {
        return rectangle.getMaxY();
    }
    public Integer centerX() {
        return rectangle.centerX();
    }

    public boolean intersect(Rectangle r) {
        return rectangle.intersect(r);
    }

    public Integer centerY() {
        return rectangle.centerY();
    }

    public int getHilbertCurvePosition() {
        return rectangle.getHilbertCurvePosition();
    }

    public int hasN() {
        return rectangle.hasN();
    }

    @Override
    public List<Rectangle> search(Rectangle r) {
        return null;
    }

    public void setRectangle(Rectangle r) {
        this.rectangle = r;
    }
}
