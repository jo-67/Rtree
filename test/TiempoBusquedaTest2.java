import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TiempoBusquedaTest2 {
    //List<Rectangle> randomList;
    List<Rectangle> randomListQ; //lista de consultas tiene que ser igual para todas
    List<List<Rectangle>> randomList; //tienen que ser las mismas para todos asi que hay que generarlas antes
    int maxChildren = 128;
    int hc = (int)Math.pow(2,19);// numero de hilbert log_base_2 = 500.000
    @Before
    public void prep() {
        randomList = new ArrayList<>();
        randomListQ = RandomRectangleGenerator.crearRectangulos2(100000, 7, hc); //lista de rectangulos de consultas Q (en vez de 100 son 128)
    }

    public long Nearest(List<Rectangle> r) {
        Nearest nearest = new Nearest(r, maxChildren);
        RTree rtree = nearest.createRTree();
        String rootPath = MemoryHandler.writeRTree(rtree);

        long startTime = System.nanoTime();
        for (int i = 0; i < randomListQ.size(); i++) {
            //SearchResult result = rtree.search(randomListQ.get(i)); //buscamos por cada rectangulo de Q
            SearchResult result = MemoryHandler.dSearch(randomListQ.get(i), rootPath, 0);
        }
        long endTime = System.nanoTime();

        long searchTime = endTime - startTime;
        long searchAverageTime = searchTime / randomListQ.size();
        //System.out.println("El tiempo de busqueda fue de: " + searchAverageTime);
        return searchAverageTime;
    }

    public long STR(List<Rectangle> r){
        STR str = new STR(r, maxChildren);
        RTree rtree = str.createRTree();
        String rootPath = MemoryHandler.writeRTree(rtree);

        long startTime = System.nanoTime();
        for (int i = 0; i < randomListQ.size(); i++) {
            //SearchResult result = rtree.search(randomListQ.get(i)); //buscamos por cada rectangulo de Q
            SearchResult result = MemoryHandler.dSearch(randomListQ.get(i), rootPath, 0);
        }
        long endTime = System.nanoTime();

        long searchTime = endTime - startTime;
        long searchAverageTime = searchTime / randomListQ.size();
        //System.out.println(searchAverageTime);
        return searchAverageTime;
    }

    public long Hilbert(List<Rectangle> r){
        HilbertCurve hilbert = new HilbertCurve(r, maxChildren);
        RTree rtree = hilbert.createRTreeH();
        String rootPath = MemoryHandler.writeRTree(rtree);

        long startTime = System.nanoTime();
        for (int i = 0; i < randomListQ.size(); i++) {
            //SearchResult result = rtree.search(randomListQ.get(i)); //buscamos por cada rectangulo de Q
            SearchResult result = MemoryHandler.dSearch(randomListQ.get(i), rootPath, 0);
        }
        long endTime = System.nanoTime();

        long searchTime = endTime - startTime;
        long searchAverageTime = searchTime / randomListQ.size();
        //System.out.println(searchAverageTime);
        return searchAverageTime;
    }

    @Test
    public void Tiempos() {
        List<Rectangle> rl = new ArrayList<>();
        for (int i = 10; i <= 15; i++) { //cambiar de 0 a 15 despues
            rl.clear();
            rl = RandomRectangleGenerator.crearRectangulos2(100,i, hc);
            System.out.println("Para el valor de 2^" + i + "\nLos tiempos promedio de busqueda fueron:");
            long n = Nearest(rl); //iteramos por cada lista de 2_10 a 2_25
            System.out.println("Nearest: " + n);
            long s = STR(rl);
            System.out.println("STR: " + s);
            long h = Hilbert(rl);
            System.out.println("Hilbert: " + h);
        }
    }

}
