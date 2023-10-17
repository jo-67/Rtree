import java.io.Serializable;
import java.lang.annotation.Inherited;
import java.util.ArrayList;
import java.util.List;

public class Leaf extends AbstractNode implements INode, Serializable {

    private Rectangle rectangle;
    public Leaf(Rectangle rectangle) {
        super(rectangle);
        this.rectangle = rectangle;
    }

    @Override
    public SearchResult search(Rectangle r, int counter) {
        List<Rectangle> rl = new ArrayList<>();
        if (rectangle.intersect(r)) {
            rl.add(rectangle);
        }
        return new SearchResult(rl, 1);
    }

    public boolean equals(Leaf l) {
        return rectangle.equals(l.rectangle);
    }
}
