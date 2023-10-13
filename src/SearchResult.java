import java.util.List;

public class SearchResult {
    int counter;
    List<Rectangle> rectangleList;

    public SearchResult(List<Rectangle> rectangleList,int counter) {
        this.rectangleList = rectangleList;
        this.counter = counter;
    }
    public int getCounter() {
        return counter;
    }

    public List<Rectangle> getRectangleList() {
        return rectangleList;
    }
}
