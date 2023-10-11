import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
public class HilbertTest {
    List<Rectangle> listaDeRectangulos = new ArrayList<>();

    @Before
    public void prep () {
        Rectangle r1 = new Rectangle(1, 2, 3, 4,16);
        Rectangle r2 = new Rectangle(5, 3, 7, 6,16);
        Rectangle r3 = new Rectangle(2, 1, 4, 5,16);
        Rectangle r4 = new Rectangle(3, 4, 6, 8,16);
        Rectangle r5 = new Rectangle(2, 2, 5, 5,16);
        Rectangle r6 = new Rectangle(6, 2, 8, 4,16);
        Rectangle r7 = new Rectangle(1, 5, 3, 7,16);
        Rectangle r8 = new Rectangle(4, 1, 6, 3,16);
        Rectangle r9 = new Rectangle(7, 5, 9, 8,16);
        Rectangle r10 = new Rectangle(2, 6, 5, 9,16);
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
    public void orderByHilbertTest() {
        HilbertCurve Hilbert = new HilbertCurve(listaDeRectangulos, 3);
        Hilbert.orderByHilbertCurve(listaDeRectangulos);
        List<Rectangle> nuevaLista = Hilbert.getRectangleList();
        System.out.println(nuevaLista);

        System.out.println(nuevaLista.get(0).getHilbertCurvePosition());
        System.out.println(nuevaLista.get(5).getHilbertCurvePosition());;
        System.out.println(nuevaLista.get(9).getHilbertCurvePosition());

        RTree rtree = Hilbert.createRTreeH();
        System.out.println(rtree);

    }
}
