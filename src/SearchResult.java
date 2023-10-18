import java.util.List;

public class SearchResult {
    long counter;
    List<Rectangle> rectangleList;

    public SearchResult(List<Rectangle> rectangleList,long counter) {
        this.rectangleList = rectangleList;
        this.counter = counter;
    }
    public long getCounter() {
        return counter;
    }

    public List<Rectangle> getRectangleList() {
        return rectangleList;
    }
}
