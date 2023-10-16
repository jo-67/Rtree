import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TiempoBusquedaTest {
    List<Rectangle> randomList;
    Rectangle r1;
    @Before
    public void prep() {
        randomList = RandomRectangleGenerator.crearRectangulos(10, 100, 0);
        r1 = randomList.get(500);
    }

    public void Nearest(int p) {
        randomList = RandomRectangleGenerator.crearRectangulos(p, 100, 0);
        Nearest nearest = new Nearest(randomList, 3);
        RTree rtree = nearest.createRTree();

        long startTime = System.nanoTime();
        for (int i = 0; i < randomList.size(); i++) {
            SearchResult result = rtree.search(r1);
        }
        long endTime = System.nanoTime();

        long searchTime = endTime - startTime;
        long searchAverageTime = searchTime / randomList.size();
        System.out.println(searchAverageTime);
    }

    @Test
    public void Tiempos() {
        for (int i = 10; i <= 25; i++) {
            Nearest(i);
        }
    }
}
