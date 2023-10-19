import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rectangle implements Serializable {
    int x1, y1, x2, y2;
    int n; //valor para construir curva

    public Rectangle(int x1, int y1, int x2, int y2, int n) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.n = n;
    }
    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.n = n;
    }
    public Integer centerX() {
        return  (x1+x2) /2;
    }


    public Integer centerY() {
        return (y1 + y2) / 2;
    }
    public int getHilbertCurvePosition() {
        return HilbertCurve.xy2d(n,(int)((x1+x2)/2),(int)((y1+y2)/2));

    }

    public Integer getMinX() {
        return Integer.min(x1,x2);
    }
    public Integer getMaxX() {
        return Integer.max(x1,x2);
    }
    public Integer getMinY() {
        return Integer.min(y1,y2);
    }
    public Integer getMaxY() {
        return Integer.max(y1,y2);
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

    public int hasN() {
        return n;

    }

    public boolean equals(Rectangle r) {
        return getMinX().equals(r.getMinX()) &&
                getMaxX().equals(r.getMaxX()) &&
                getMinY().equals(r.getMinY()) &&
                getMaxY().equals(r.getMaxY());
    }
}