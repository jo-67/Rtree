import java.util.ArrayList;
import java.util.List;

public class Rectangle {
    double x1, y1, x2, y2;

    public Rectangle(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Double centerX() {
        return  (x1+x2) /2;
    }

    public Double getMinX() {
        return Double.min(x1,x2);
    }
    public Double getMaxX() {
        return Double.max(x1,x2);
    }
    public Double getMinY() {
        return Double.min(y1,y2);
    }
    public Double getMaxY() {
        return Double.max(y1,y2);
    }

    public Boolean intersect(Rectangle r) {
        if (this.getMaxX() < r.getMinX() || this.getMinX() > r.getMaxX()) {
            // el maximo es que el minimo no intersecta
            return false;
        }
        if (this.getMaxY() < r.getMinY() || this.getMinY() > r.getMaxY()) {
            return false;
        }
        // ahora se sabe
        return true;
    }
}