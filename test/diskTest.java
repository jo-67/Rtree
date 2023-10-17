import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class diskTest {
    List<Rectangle> listaDeRectangulos = new ArrayList<>();
    RTree rtree;
    @Before
    public void prep() {
        Rectangle r1 = new Rectangle(1, 2, 3, 4, 0);
        Rectangle r2 = new Rectangle(5, 3, 7, 6, 0);
        Rectangle r3 = new Rectangle(2, 1, 4, 5, 0);
        Rectangle r4 = new Rectangle(3, 4, 6, 8, 0);
        Rectangle r5 = new Rectangle(2, 2, 5, 5, 0);
        Rectangle r6 = new Rectangle(6, 2, 8, 4, 0);
        Rectangle r7 = new Rectangle(1, 5, 3, 7, 0);
        Rectangle r8 = new Rectangle(4, 1, 6, 3, 0);
        Rectangle r9 = new Rectangle(7, 5, 9, 8, 0);
        Rectangle r10 = new Rectangle(2, 6, 5, 9, 0);
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
        Nearest nearest = new Nearest(listaDeRectangulos, 3);
        nearest.orderByCenterX(listaDeRectangulos);
        List<Rectangle> nuevaLista = nearest.getRectangleList();
        System.out.println(nuevaLista);

        rtree = nearest.createRTree();
    }

    @Test
    public void writeTest() {
        String path = "C:\\Users\\pepad\\IdeaProjects\\Rtree\\out\\file.bin";
        MemoryHandler m = new MemoryHandler(path);
        m.writeRTree(rtree);
    }

    @Test
    public void readTest() {
        String path = "C:\\Users\\pepad\\IdeaProjects\\Rtree\\out\\file.bin";
        MemoryHandler m = new MemoryHandler(path);
        RTree rTree  = m.readBinaryToRTree();
        System.out.println(rTree);
    }

    @Test
    public void transformationTest() throws IOException, ClassNotFoundException {

        byte[] bytes = MemoryHandler.rtreeToBytes(rtree);

        RTree rTree2 = MemoryHandler.bytesToRtree(bytes);
        boolean b = rTree2.equals(rtree);
        assertTrue(b);
    }

    @Test
    public void readSegmentTest() throws IOException, ClassNotFoundException {
        String path = "C:\\Users\\pepad\\IdeaProjects\\Rtree\\out\\file.bin";
        MemoryHandler m = new MemoryHandler(path);
        byte[] r = m.readFileSegment(0, 100);
        System.out.println(r);
        RTree rTree = MemoryHandler.bytesToRtree(r);
        System.out.println(rTree);
    }

}
