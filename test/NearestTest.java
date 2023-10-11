import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;


public class NearestTest {
    List<Rectangle> listaDeRectangulos = new ArrayList<>();

    @Before
    public void prep() {
        Rectangle r1 = new Rectangle(1, 2, 3, 4,0);
        Rectangle r2 = new Rectangle(5, 3, 7, 6,0);
        Rectangle r3 = new Rectangle(2, 1, 4, 5,0);
        Rectangle r4 = new Rectangle(3, 4, 6, 8,0);
        Rectangle r5 = new Rectangle(2, 2, 5, 5,0);
        Rectangle r6 = new Rectangle(6, 2, 8, 4,0);
        Rectangle r7 = new Rectangle(1, 5, 3, 7,0);
        Rectangle r8 = new Rectangle(4, 1, 6, 3,0);
        Rectangle r9 = new Rectangle(7, 5, 9, 8,0);
        Rectangle r10 = new Rectangle(2, 6, 5, 9,0);
        listaDeRectangulos.add(r1);
        listaDeRectangulos.add(r2);
        listaDeRectangulos.add(r3);
        listaDeRectangulos.add(r4);
        listaDeRectangulos.add(r5);
        listaDeRectangulos.add(r6);
        listaDeRectangulos.add(r7);
        listaDeRectangulos.add(r8);
        listaDeRectangulos.add(r9);
        listaDeRectangulos.add(r10);

    }

    @Test
    public void orderByCenterXTest() {
        Nearest nearest = new Nearest(listaDeRectangulos, 3);
        nearest.orderByCenterX(listaDeRectangulos);
        List<Rectangle> nuevaLista = nearest.getRectangleList();
        System.out.println(nuevaLista);

        RTree rtree = nearest.createRTree();
        System.out.println(rtree);

    }

    @Test
    public void searchNearest() {
        Nearest nearest = new Nearest(listaDeRectangulos, 3);
        nearest.orderByCenterX(listaDeRectangulos);
        List<Rectangle> nuevaLista = nearest.getRectangleList();
        System.out.println(nuevaLista);

        RTree rtree = nearest.createRTree();
        Rectangle r = new Rectangle(2,2,3,3);
        List<Rectangle> rectangleList = rtree.search(r);
        System.out.println(rectangleList);
    }
}