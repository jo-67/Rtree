import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Leaf extends AbstractNode implements INode, Serializable {

    private Rectangle rectangle;
    public Leaf(Rectangle rectangle) {
        super(rectangle);
        this.rectangle = rectangle;
    }

    @Override
    public SearchResult search(Rectangle r, long counter) {
        List<Rectangle> rl = new ArrayList<>();
        if (rectangle.intersect(r)) {
            rl.add(rectangle);
        }
        return new SearchResult(rl, 1);
    }

    public boolean equals(Leaf l) {
        return rectangle.equals(l.rectangle);
    }

    public void write() {
        try {
            MemoryHandler.writeLeaf(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }
}
