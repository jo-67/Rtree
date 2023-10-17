import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class dicoTest {


    @Test
    public void LeafDisk() throws IOException, ClassNotFoundException {
        Rectangle r1 = new Rectangle(1, 2, 3, 4,16);
        Leaf l1 = new Leaf(r1);
        byte[] bytes = l1.toByteArray();
        Rectangle r2 = DiskHandler.bytesToRectangle(r1.toByteArray());
        Leaf l2 = new Leaf(r2);
        Leaf l3 = DiskHandler.bytesToLeaf(bytes);
        assertTrue(l1.equals(l2));
        assertTrue(l1.equals(l3));
    }
}
