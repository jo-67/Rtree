public abstract class AbstractNode implements INode{
    public Rectangle rectangle;

    public AbstractNode(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    public AbstractNode() {
    }
    public Double getMinX() {
        return rectangle.getMinX();
    }
    public Double getMaxX() {
        return rectangle.getMaxX();
    }
    public Double getMinY() {
        return rectangle.getMinY();
    }
    public Double getMaxY() {
        return rectangle.getMaxY();
    }
    public Double centerX() {
        return rectangle.centerX();
    }

    public Double centerY() {
        return rectangle.centerY();
    }

    public int getHilbertCurvePosition() {
        return rectangle.getHilbertCurvePosition();
    }

    public int hasN() {
        return rectangle.hasN();
    }

}
