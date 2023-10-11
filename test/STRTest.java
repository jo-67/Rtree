import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class STRTest {

    List<Rectangle> listaDeRectangulos = new ArrayList<>();
    List<Rectangle> points = new ArrayList<>();
    RTree testRTree = null;
    @Before
    public void prep() {
        Rectangle r1 = new Rectangle(1, 2, 3, 4);
        Rectangle r2 = new Rectangle(5, 3, 7, 6);
        Rectangle r3 = new Rectangle(2, 1, 4, 5);
        Rectangle r4 = new Rectangle(3, 4, 6, 8);
        Rectangle r5 = new Rectangle(2, 2, 5, 5);
        Rectangle r6 = new Rectangle(6, 2, 8, 4);
        Rectangle r7 = new Rectangle(1, 5, 3, 7);
        Rectangle r8 = new Rectangle(4, 1, 6, 3);
        Rectangle r9 = new Rectangle(7, 5, 9, 8);
        Rectangle r10 = new Rectangle(2, 6, 5, 9);
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

        //Usare estos cuadrados para probar, que serán puntos
        Rectangle r11 = new Rectangle(1, 1, 1, 1);
        Rectangle r12 = new Rectangle(1, 2, 1, 2);
        Rectangle r13 = new Rectangle(1, 3, 1, 3);
        Rectangle r14 = new Rectangle(1, 4, 1, 4);
        Rectangle r21 = new Rectangle(2, 1, 2, 1);
        Rectangle r22 = new Rectangle(2, 2, 2, 2);
        Rectangle r23 = new Rectangle(2, 3, 2, 3);
        Rectangle r24 = new Rectangle(2, 4, 2, 4);
        Rectangle r31 = new Rectangle(3, 1, 3, 1);
        Rectangle r32 = new Rectangle(3, 2, 3, 2);
        Rectangle r33 = new Rectangle(3, 3, 3, 3);
        Rectangle r34 = new Rectangle(3, 4, 3, 4);
        Rectangle r41 = new Rectangle(4, 1, 4, 1);
        Rectangle r42 = new Rectangle(4, 2, 4, 2);
        Rectangle r43 = new Rectangle(4, 3, 4, 3);
        Rectangle r44 = new Rectangle(4, 4, 4, 4);
        points.add(r11);
        points.add(r12);
        points.add(r13);
        points.add(r14);
        points.add(r21);
        points.add(r22);
        points.add(r23);
        points.add(r24);
        points.add(r31);
        points.add(r32);
        points.add(r33);
        points.add(r34);
        points.add(r41);
        points.add(r42);
        points.add(r43);
        points.add(r44);

        //Crearé el Rtree a mano para ver que funcione correctamente:
        //Crearé uno de hijos máximo 4, con 16 nodos. Ya que son puntos es facil ver como deberia quedar.
        //Partimos creando los nodos que apuntan a cada rectángulo
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Node n4 = new Node();

        n1.addLeaf(r11); //Este rectangulo es el de abajo a la izquierda, debe tomar el 11, 12, 21, y 22
        n1.addLeaf(r12);
        n1.addLeaf(r21);
        n1.addLeaf(r22);
        n2.addLeaf(r13); //Este rectangulo es el de arriba a la izquierda, debe tomar el 13, 14, 23, y 24
        n2.addLeaf(r14);
        n2.addLeaf(r23);
        n2.addLeaf(r24);
        n3.addLeaf(r31); //Este rectangulo es el de abajo a la derecha, debe tomar el 31, 32, 41, y 42
        n3.addLeaf(r32);
        n3.addLeaf(r41);
        n3.addLeaf(r42);
        n4.addLeaf(r33); //Este rectangulo es el de arriba a la derecha, debe tomar el 33, 34, 43, y 44
        n4.addLeaf(r34);
        n4.addLeaf(r43);
        n4.addLeaf(r44);

        //finalmente, creamos un nuevo nodo que incluya solo estos 4 nodos. Para esto creamos una lista que
        // tenga estos nodos, y creamos el Rtree con el nodo que recibe esta lista de base
        List<INode> Nodos = new ArrayList<INode>();

        Nodos.add(n1);
        Nodos.add(n2);
        Nodos.add(n3);
        Nodos.add(n4);

        Node root = new Node();
        root.addChild(n1);
        root.addChild(n2);
        root.addChild(n3);
        root.addChild(n4);

        testRTree = new RTree(root, 4);



    }

    @Test
    public void orderByCenterXTest() {
        STR str = new STR(listaDeRectangulos, 3);
        str.orderByCenterY(listaDeRectangulos);
        List<Rectangle> nuevaLista = str.getRectangleList();
        System.out.println(nuevaLista);


        RTree rtree = str.createRTree();
        System.out.println(rtree);

    }

    @Test
    public void orderByCenterYTest() {
        STR str = new STR(listaDeRectangulos, 3);
        str.orderByCenterX(listaDeRectangulos);
        List<Rectangle> nuevaLista = str.getRectangleList();
        System.out.println(nuevaLista);
    }


    @Test
    public void createRTreeTest() {
        STR str = new STR(listaDeRectangulos, 3);
        RTree rtree = str.createRTree();
        System.out.println(rtree);

        str = new STR(points, 4);
        rtree = str.createRTree();
        //assertEquals(testRTree,rtree); //Crear un metodo para comparar dos RTrees

    }
}
