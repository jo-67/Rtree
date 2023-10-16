import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class TiempoBusquedaTest {
    //List<Rectangle> randomList;
    List<Rectangle> randomListQ; //lista de consultas tiene que ser igual para todas
    List<List<Rectangle>> randomList; //tienen que ser las mismas para todos asi que hay que generarlas antes
    Rectangle r1;
    @Before
    public void prep() {

        randomListQ = RandomRectangleGenerator.crearRectangulos(7,100000, 0); //lista de rectangulos de consultas Q (en vez de 100 son 128)
        for (int i = 10; i <= 15; i++){ //cambiar el 15 a 25 despues
          List<Rectangle>  r = RandomRectangleGenerator.crearRectangulos(i,100,0);
          randomList.add(r);
          }
        //System.out.println(randomList.size());
        }

    public void Nearest(int k) {
        List<Rectangle> r = null; // esto esta para que no se queje intellij
        if (randomList != null) {
            r = randomList.get(k); //sacamos de la lista la lista de rectangulos
        }else{
            System.out.println("ocurrio un error");
        }

        Nearest nearest = new Nearest(r, 3);

        RTree rtree = nearest.createRTree();

        long startTime = System.nanoTime();
        for (int i = 0; i < randomListQ.size(); i++) {
            SearchResult result = rtree.search(randomListQ.get(i)); //buscamos por cada rectangulo de Q
        }
        long endTime = System.nanoTime();

        long searchTime = endTime - startTime;
        long searchAverageTime = searchTime / randomListQ.size();
        System.out.println(searchAverageTime);
    }

    public void STR(int k){
        List<Rectangle> r = null; // esto esta para que no se queje intellij
        if (randomList != null) {
            r = randomList.get(k); //sacamos de la lista la lista de rectangulos
        }else{
            System.out.println("ocurrio un error");
        }

        STR str = new STR(r, 3);

        RTree rtree = str.createRTree();

        long startTime = System.nanoTime();
        for (int i = 0; i < randomListQ.size(); i++) {
            SearchResult result = rtree.search(randomListQ.get(i)); //buscamos por cada rectangulo de Q
        }
        long endTime = System.nanoTime();

        long searchTime = endTime - startTime;
        long searchAverageTime = searchTime / randomListQ.size();
        System.out.println(searchAverageTime);
    }

    public void Hilbert(int k){
        List<Rectangle> r = null; // esto esta para que no se queje intellij
        if (randomList != null) {
            r = randomList.get(k); //sacamos de la lista la lista de rectangulos
        }else{
            System.out.println("ocurrio un error");
        }

        //recorremos la lista y le agregamos a cada rectangulo el numero de hilbert log_base_2 = 500.000
        //probablemte exista un metodo más eficiente
        int hc = (int)Math.pow(2,19); //no estoy seguro que sea el numero correcto
        for (int i = 0; i< Objects.requireNonNull(r).size(); i++){
            r.get(i).n = hc;
        }

        for (int i = 0; i< randomListQ.size(); i++){
            randomListQ.get(i).n = hc;
        }

        HilbertCurve hilbert = new HilbertCurve(r, 3);

        RTree rtree = hilbert.createRTreeH();

        long startTime = System.nanoTime();
        for (int i = 0; i < randomListQ.size(); i++) {
            SearchResult result = rtree.search(randomListQ.get(i)); //buscamos por cada rectangulo de Q
        }
        long endTime = System.nanoTime();

        long searchTime = endTime - startTime;
        long searchAverageTime = searchTime / randomListQ.size();
        System.out.println(searchAverageTime);
    }

    @Test
    public void Tiempos() {
        for (int i = 0; i <= 15; i++) {
            Nearest(i); //iteramos por cada lista de 2_10 a 2_25
            STR(i);
            Hilbert(i);
        }
    }

}
