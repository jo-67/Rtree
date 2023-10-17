import java.lang.annotation.Inherited;
import java.util.ArrayList;
import java.util.List;

public class Leaf extends AbstractNode implements INode{

    public Leaf(Rectangle rectangle) {
        super(rectangle);
    }

    @Override
    public SearchResult search(Rectangle r, int counter) {
        List<Rectangle> rl = new ArrayList<>();
        if (rectangle.intersect(r)) {
            rl.add(rectangle);
        }
        return new SearchResult(rl, 1);
    }

    public byte[] toByteArray() {
        return rectangle.toByteArray();
    }

    public boolean equals(Leaf l) {
        return this.rectangle.equals(l.rectangle);
    }
}
