import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccessTest {
    List<Rectangle> randomListQ; //lista de consultas tiene que ser igual para todas
    List<List<Rectangle>> randomList; //tienen que ser las mismas para todos asi que hay que generarlas antes
    @Before
    public void prep() {
        randomList = new ArrayList<>();
        randomListQ = RandomRectangleGenerator.crearRectangulos(7,100000, 0); //lista de rectangulos de consultas Q (en vez de 100 son 128)
        for (int i = 10; i <= 15; i++){ //cambiar el 15 a 25 despues
            List<Rectangle>  r = RandomRectangleGenerator.crearRectangulos(i,100,0);
            randomList.add(r);
        }
        //System.out.println(randomList.size());
    }

    public long Nearest(int k) {
        List<Rectangle>r = randomList.get(k); //+1 porque si no no calza
        Nearest nearest = new Nearest(r, 128);
        RTree rtree = nearest.createRTree();

        long accesos = 0;
        for (int i = 0; i < randomListQ.size(); i++) {
            SearchResult result = rtree.search(randomListQ.get(i));
            accesos = accesos + result.getCounter();
        }
        long accessAverageTime = accesos / randomList.size();
        //System.out.println(accessAverageTime);
        return accessAverageTime;
    }

    public long STR(int k) {
        List<Rectangle> r = randomList.get(k);
        STR str = new STR(r, 128);
        RTree rtree = str.createRTree();

        long accesos = 0;
        for (int i = 0; i < randomListQ.size(); i++) {
            SearchResult result = rtree.search(randomListQ.get(i));
            accesos = accesos + result.getCounter();
        }
        long accessAverageTime = accesos / randomList.size();
        //System.out.println(accessAverageTime);
        return accessAverageTime;
    }

    public long Hilbert(int k) {
        List<Rectangle>r = randomList.get(k);

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
        long accessAverageTime = accesos / randomList.size();
        //System.out.println(accessAverageTime);
        return accessAverageTime;
    }



    @Test
    public void Access() {
        for (int i = 10; i <= 15; i++) { //cambiar de 0 a 15 despues
            System.out.println("Para el valor de 2^" + i + "\nEl numero de accesos promedio de busqueda fueron:");
            long n = Nearest(i-10); //iteramos por cada lista de 2_10 a 2_25
            System.out.println("Nearest: " + n);
            long s = STR(i-10);
            System.out.println("STR: " + s);
            long h = Hilbert(i-10);
            System.out.println("Hilbert: " + h);
        }
    }

}
