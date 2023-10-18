import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccessTest2 {
    List<Rectangle> randomListQ; //lista de consultas tiene que ser igual para todas
    List<List<Rectangle>> randomList; //tienen que ser las mismas para todos asi que hay que generarlas antes
    @Before
    public void prep() {
        randomList = new ArrayList<>();
        randomListQ = RandomRectangleGenerator.crearRectangulos2(100000, 7); //lista de rectangulos de consultas Q (en vez de 100 son 128)
    }

    public long Nearest(List<Rectangle> r) {
        Nearest nearest = new Nearest(r, 128);
        RTree rtree = nearest.createRTree();

        long accesos = 0;
        for (int i = 0; i < randomListQ.size(); i++) {
            SearchResult result = rtree.search(randomListQ.get(i));
            accesos = accesos + result.getCounter();
        }
        long accessAverageTime = accesos / r.size();
        //System.out.println(accessAverageTime);
        return accessAverageTime;
    }

    public long STR(List<Rectangle> r) {
        STR str = new STR(r, 128);
        RTree rtree = str.createRTree();

        long accesos = 0;
        for (int i = 0; i < randomListQ.size(); i++) {
            SearchResult result = rtree.search(randomListQ.get(i));
            accesos = accesos + result.getCounter();
        }
        long accessAverageTime = accesos / r.size();
        //System.out.println(accessAverageTime);
        return accessAverageTime;
    }

    public long Hilbert(List<Rectangle> r) {

        //recorremos la lista y le agregamos a cada rectangulo el numero de hilbert log_base_2 = 500.000
        //probablemte exista un metodo más eficiente
        int hc = (int)Math.pow(2,19); //no estoy seguro que sea el numero correcto
        for (int i = 0; i< Objects.requireNonNull(r).size(); i++){
            r.get(i).n = hc;
        }

        for (int i = 0; i< randomListQ.size(); i++){
            randomListQ.get(i).n = hc;
        }

        HilbertCurve hilbert = new HilbertCurve(r, 128);
        RTree rtree = hilbert.createRTreeH();

        long accesos = 0;
        for (int i = 0; i < randomListQ.size(); i++) {
            SearchResult result = rtree.search(randomListQ.get(i));
            accesos = accesos + result.getCounter();
        }
        long accessAverageTime = accesos / r.size();
        //System.out.println(accessAverageTime);
        return accessAverageTime;
    }



    @Test
    public void Access() {
        List<Rectangle> rl = new ArrayList<>();
        for (int i = 10; i <= 25; i++) { //cambiar de 0 a 15 despues
            rl.clear();
            rl = RandomRectangleGenerator.crearRectangulos2(100,i);
            System.out.println("Para el valor de 2^" + i + "\nEl numero de accesos promedio de busqueda fueron:");
            long n = Nearest(rl); //iteramos por cada lista de 2_10 a 2_25
            System.out.println("Nearest: " + n);
            long s = STR(rl);
            System.out.println("STR: " + s);
            long h = Hilbert(rl);
            System.out.println("Hilbert: " + h);
        }
    }

}
