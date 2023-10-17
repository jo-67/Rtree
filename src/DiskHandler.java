import java.io.*;
import java.util.List;

public class DiskHandler {

    public static Rectangle bytesToRectangle(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        double[] recList = (double[]) objectInputStream.readObject();
        return new Rectangle(recList[0], recList[1], recList[2], recList[3], (int) recList[4]);
    }

    public static Leaf bytesToLeaf(byte[] bytes) throws IOException, ClassNotFoundException {
        return new Leaf(bytesToRectangle(bytes));
    }

    public static Node bytesToNode() {
        return null;
    }

    public static byte[] intToByteArray(List<Integer> list) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

            objectOutputStream.writeObject(list);
            objectOutputStream.flush();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
