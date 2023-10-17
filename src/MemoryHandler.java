import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class MemoryHandler {
    private String path;

    public MemoryHandler(String path) {
        this.path = path;
    }

    public static byte[] rtreeToBytes(RTree rtree) throws IOException {

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bs);
        os.writeObject(rtree);  // this es de tipo DatoUdp
        os.close();
        byte[] bytes = bs.toByteArray(); // devuelve byte[]
        return bytes;
    }

    public static RTree bytesToRtree(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bs2 = new ByteArrayInputStream(bytes); // bytes es el byte[]
        ObjectInputStream is = new ObjectInputStream(bs2);
        RTree rtree = (RTree) is.readObject();
        is.close();
        return rtree;
    }

    public void writeRTree(RTree rTree) {
        File file = new File(path);
        file.delete(); // limpia el contenido

        byte[] bytes = new byte[0];
        try {
            bytes = rtreeToBytes(rTree);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RTree readBinaryToRTree() {
        try {
            Path path = Path.of(this.path);
            byte[] bytes = Files.readAllBytes(path);
            return bytesToRtree(bytes);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}