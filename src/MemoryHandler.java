import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemoryHandler {
    private static String path = "C:\\Users\\pepad\\IdeaProjects\\Rtree\\out\\nodes";

    public MemoryHandler(String path) {
        this.path = path;
    }

    public static String writeRTree(RTree rtree) {
        // to do limpiar carpeta
        clearFolder(path);
        String rootPath = null;
        try {
            rootPath = writeNode(rtree.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootPath;
    }

    public static String writeNode(Node node) throws IOException {
        // id x1 y1 x2 y2 [id children]
        List<Integer> array = new ArrayList<>();
        int id = node.hashCode();
        array.add(id);
        array.add(node.getRectangle().x1);
        array.add(node.getRectangle().y1);
        array.add(node.getRectangle().x2);
        array.add(node.getRectangle().y2);
        List<INode> lr = node.getChildren();
        for (int i=0; i< lr.size() ; i++) {
            array.add(lr.get(i).hashCode());
            lr.get(i).write();
        }
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bs);
        os.writeObject(array);
        os.close();
        byte[] bytes = bs.toByteArray(); // devuelve byte[]
        String nodePath = makePath(id);
        writeBytes(nodePath, bytes);
        return nodePath;
    }

    public static void writeBytes(String path, byte[] bytes) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String writeLeaf(Leaf leaf) throws IOException {
        // id x1 y1 x2 y2
        List<Integer> array = new ArrayList<>();
        int id = leaf.hashCode();
        array.add(id);
        array.add(leaf.getRectangle().x1);
        array.add(leaf.getRectangle().y1);
        array.add(leaf.getRectangle().x2);
        array.add(leaf.getRectangle().y2);

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bs);
        os.writeObject(array);
        os.close();
        byte[] bytes = bs.toByteArray(); // devuelve byte[]
        String nodePath =  makePath(id);
        writeBytes(nodePath, bytes);
        return nodePath;
    }

    public static List<Integer> readNodeInfo(String filepath) {
        byte[] bytes = readBinaryFileToByteArray(filepath);
        return bytesToList(bytes);
    }
    public static byte[] readBinaryFileToByteArray(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096]; // Tamaño del búfer de lectura

            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Integer> bytesToList(byte[] byteArray) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (List<Integer>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void clearFolder(String folderPath) {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Carpeta invalida");
            return;
        }

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();

                }
            }
        }
    }

    public static SearchResult dSearch(Rectangle r, String nodePath, long counter) {
        List<Integer> nodeInfo = readNodeInfo(nodePath);
        Rectangle nodeRectangle = new Rectangle(nodeInfo.get(1), nodeInfo.get(2), nodeInfo.get(3), nodeInfo.get(4));
        List<Rectangle> rectangleList = new ArrayList<>();

        if (nodeInfo.size() <= 5) { // caso de hoja (no tiene hijos)
            if (r.intersect(nodeRectangle)) {
                rectangleList.add(nodeRectangle);
            }
            return new SearchResult(rectangleList, counter + 1);// si no intersecciona, esta vacia
        }
        if (r.intersect(nodeRectangle)) { // nodo interno intersecciona
            for (int i=5 ; i < nodeInfo.size(); i++) {
                // Se añaden rectangulos de los hijos
                SearchResult s = dSearch(r, makePath(nodeInfo.get(i)), 0);
                rectangleList.addAll(s.getRectangleList());
                counter = counter + s.getCounter();
            }
        }
        return new SearchResult(rectangleList, counter + 1); // si no intersecciona, esta vacia
    }

    public static String makePath(int id) {
        return path + "\\" + id + ".bin";
    }
}
