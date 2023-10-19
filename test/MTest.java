import org.junit.Before;
import org.junit.Test;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MTest {
    RTree rtree;
    List<Rectangle> randomList;
    List<Rectangle> randomListQ;
    int hc = (int) Math.pow(2,19);
    @Before
    public void prep() {
        randomList = RandomRectangleGenerator.crearRectangulos2(100, 10, hc);
        randomListQ = RandomRectangleGenerator.crearRectangulos2(100000, 5, hc); //lista de rectangulos de consultas Q (en vez de 100 son 128)
    }

    public long mTest(int maxChildren) {
        Nearest nearest = new Nearest(randomList, maxChildren);
        RTree rtree = nearest.createRTree();
        String rootPath = MemoryHandler.writeRTree(rtree);

        long startTime = System.nanoTime();
        for (int i = 0; i < randomListQ.size(); i++) {
            SearchResult result = MemoryHandler.dSearch(randomListQ.get(i), rootPath, 0);
        }
        long endTime = System.nanoTime();

        long searchTime = endTime - startTime;
        long searchAverageTime = searchTime / randomListQ.size();
        //System.out.println("El tiempo de busqueda fue de: " + searchAverageTime);
        return searchAverageTime;
    }

    public long mTestAccess(int maxChildren) {
        Nearest nearest = new Nearest(randomList, maxChildren);
        RTree rtree = nearest.createRTree();
        String rootPath = MemoryHandler.writeRTree(rtree);

        int nAccess = 0;
        for (int i = 0; i < randomListQ.size(); i++) {
            SearchResult result = MemoryHandler.dSearch(randomListQ.get(i), rootPath, 0);
            nAccess += result.getCounter();
        }

        long accessAvarage = nAccess / randomListQ.size();
        //System.out.println("El tiempo de busqueda fue de: " + searchAverageTime);
        return accessAvarage;
    }

    @Test
    public void timeM() {
        Map<Integer, Long> diccionario = new HashMap<>();
        for (int i = 50; i < 1000; i+=100) {
            System.out.println("El tiempo de busqueda promedio para M = " + i);
            long time = mTest(i);
            printTime(time);
            diccionario.put(i, time);
        }
    }
    @Test
    public void accessM() {
        for (int i = 50; i < 1500; i+=100) {
            System.out.println("El numero de accesos promedio para M = " + i);
            long nAccess = mTestAccess(i);
            System.out.println(nAccess);
        }
    }

    public void printTime(long n) {
        // Calcula minutos, segundos, milisegundos y microsegundos
        long minutos = n / 60000000000L;
        long segundos = (n % 60000000000L) / 1000000000L;
        long milisegundos = (n % 1000000000L) / 1000000L;
        long microsegundos = (n % 1000000L) / 1000L;
        long nanosegundos = n % 1000L;

        // Imprime el resultado
        System.out.println(minutos + " minutos " + segundos + " segundos " + milisegundos + " milisegundos " + microsegundos + " microsegundos " + nanosegundos + " nanosegundos");
    }

}
