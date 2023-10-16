import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AccessTest {
    List<Rectangle> randomList;
    Rectangle r1;
    @Before
    public void prep() {
        randomList = RandomRectangleGenerator.generateRecangles(10);
        r1 = randomList.get(500);
    }
    @Test
    public void Nearest() {
        Nearest nearest = new Nearest(randomList, 3);
        RTree rtree = nearest.createRTree();

        int accesos = 0;
        for (int i = 0; i < randomList.size(); i++) {
            SearchResult result = rtree.search(r1);
            accesos = accesos + result.getCounter();
        }
        long accessAverageTime = accesos / randomList.size();
        System.out.println(accessAverageTime);
    }

}
