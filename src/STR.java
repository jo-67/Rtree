import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.ceil;
import static java.lang.Math.sqrt;

public class STR {

    private List<Rectangle> rectangleList;

    private int maxChildren; // Es el tamaño máximo de cada nodo del árbol, y
                             // a la vez cantidad maxima de hijos de cada nodo

    public STR(List<Rectangle> rectangleList, int MaxChildren) {
        this.rectangleList = rectangleList;
        this.maxChildren = MaxChildren;
    }

    public RTree createRTree() {
        // n rectangulos, M cantidad maxima de hijos
        // ordena rectangulos con respecto a x del centro
        // junta en S grupos, con S = sqrt(S/M)
        // cada grupo se ordena con respecto a la Y del centro
        // Se agrupan en grupos de tamaño M (a excepción del último grupo)
        // Se obtienen S*S grupos de tamaño máximo M
        // Ahora teniendo los grupos, se crea una nueva lista de rectángulos con el MBR de cada grupo
        // Se repite el procedimiento hasta que se puedan agrupar todos los rectángulos en un solo nodo

        orderByCenterX(rectangleList);

        int S = (int) Math.ceil(Math.sqrt((double) rectangleList.size() /maxChildren)); //Calculamos la raiz y tomamos el ceiling

        // Para separar los rectangulos, lo haremos en lista de listas. Cada elemento de esta lista corresponderá
        // a uno de los subgrupos de puntos, con los que luego se crearan los nodos. Estas listas no son nodos ni
        // representan un MBR

        List<List<Rectangle>> subgroupsList = new ArrayList<List<Rectangle>>();
        List<Rectangle> currentSubgroup = new ArrayList<Rectangle>();
        int n=0; //Contador de cuantos rectángulos llevamos en cada lista
        for (int i=0; i < rectangleList.size();i++){
            if (n==S*maxChildren) { // Si llegamos al maximo de valores en un subgrupo
                orderByCenterY(currentSubgroup); //aprovecho de ordenarlos por Y antes de agregarlos a la lista de lista
                subgroupsList.add(currentSubgroup); //agregamos la lista actual a la lista de listas
                currentSubgroup = new ArrayList<Rectangle>(); //Reiniciamos la lista
                n=0; //reiniciamos el contador
            }
            currentSubgroup.add(rectangleList.get(i)); //Agregamos el valor rectangulo al grupo actual
            n++; //aumentamos 1 el contador de rectangulos en el grupo actual
        }
        // Nos faltaria agregar el ultimo grupo de rectangulos a la lista de listas, asi que los agregamos ahora

        orderByCenterY(currentSubgroup); //aprovecho de ordenarlos por Y antes de agregarlos a la lista de lista
        subgroupsList.add(currentSubgroup); //agregamos la lista actual a la lista de listas

        //Con esto ya tenemos los grupos separados, y ordenador por Y. Ahora corresponde crear los nodos por subgrupo.
        //Para esto recorreremos la lista de listas, creando nodos en cada sublista hasta que se recorra completa.


        List<INode> nodes = new ArrayList<>(); //Acá guardamos los nodos que se van creando
        Node current = new Node(); //Este sera el nodo actual
        int m = 0; //Número de rectangulos en el nodo actual


        //Este código es casi el mismo de antes, solo que recorre por subgrupos. Tambien es muy similar al de NearestX

        for (int i = 0; i< subgroupsList.size(); i++){ //recorremos cada sublista de la lista de grupos
            for (int j = 0; j < subgroupsList.get(i).size(); j++){ //recorremos toda la sublista
                if (m == maxChildren) {
                    m = 0;
                    current.createMbr(); // Crea el rectangulo MBR
                    nodes.add(current);
                    current = new Node();
                }
                if (m==0){
                    current = new Node();
                }
                current.addLeaf(subgroupsList.get(i).get(j));//Accedemos al rectangulo actual de la lista actual
                m++;
            }
            current.createMbr();
            nodes.add(current); // añade el ultimo
            m = 0;
        }


        if (rectangleList.size() <= maxChildren) {
            // Este es el caso base de la recursion
            return new RTree(current, maxChildren);
        }
        // sino, debe seguir agrupando a los nodos

        return groupNodes(nodes); //Este es el caso recursivo
    }
    public RTree groupNodes(List<INode> nodeList) {
        if (nodeList.size() <= maxChildren) {
            // todos estan dentro de un nodo
            Node root = new Node(nodeList);
            return new RTree(root, maxChildren);
        }


        orderByNodeCenterX(nodeList); //Ordenamos los nodos por el centro del MBR que representan
        //Ahora debemos repetir el procedimiento para los MBR de los nodos como se hizo para los rectángulos
        int S= (int) Math.ceil(Math.sqrt((double) nodeList.size() /maxChildren)); //Calculamos la raíz y tomamos el ceiling

        List<List<INode>> subgroupsList = new ArrayList<List<INode>>(); //Similar a lo de antes, creamos una lista de sublistas
        List<INode> currentSubgroup = new ArrayList<INode>();
        int n = 0;

        for (int i = 0; i< nodeList.size(); i++){
            if (n==S*maxChildren) { // Si llegamos al maximo de valores en un subgrupo
                orderByNodeCenterY(currentSubgroup); //aprovecho de ordenarlos por Y antes de agregarlos a la lista de lista
                subgroupsList.add(currentSubgroup); //agregamos la lista actual a la lista de listas
                currentSubgroup = new ArrayList<INode>(); //Reiniciamos la lista
                n=0; //reiniciamos el contador
            }
            currentSubgroup.add(nodeList.get(i)); //Agregamos el valor rectangulo al grupo actual
            n++; //aumentamos 1 el contador de rectangulos en el grupo actual
        }

        orderByNodeCenterY(currentSubgroup); //aprovecho de ordenarlos por Y antes de agregarlos a la lista de lista
        subgroupsList.add(currentSubgroup); //agregamos la lista actual a la lista de listas

        //Ahora creamos los nuevos nodos con la lista de nodos

        List<INode> parents = new ArrayList<>();
        Node current = new Node();
        int m = 0;
        for (int i = 0; i < subgroupsList.size(); i++) {
            for (int j = 0; j < subgroupsList.get(i).size(); j++){ //recorremos toda la sublista
                if (m == maxChildren) {
                    m = 0;
                    current.createMbr(); // Crea el rectangulo MBR
                    parents.add(current);
                    current = new Node();
                }
                if (m==0){
                    current = new Node();
                }
                current.addChild(subgroupsList.get(i).get(j));//Accedemos al rectangulo actual de la lista actual
                m++;
            }
            current.createMbr();
            parents.add(current); // añade el ultimo nodo que falta. Esto se hace en cada ciclo ya que normalmente sera un nodo de m=maxChildren que no hizo el ultimo ciclo.
                                  // a excepcion del ultimo nodo del ultimo grupo, que podria tener menos e igual se debe agregar.
            m = 0;
        }

        //Ya que el caso base esta arriba, que seria cuando el numero de nodos de este nivel cabe en uno solo, entonces podemos proceder a agregar
        return groupNodes(parents);
    }
    public void orderByCenterX(List<Rectangle> lRec) {
        Comparator<Rectangle> compareByCenterX =
                Comparator.comparing(Rectangle::centerX);
        // (Rectangle r1, Rectangle r2) -> r1.centerX().compareTo( r2.centerX() );
        Collections.sort(lRec, compareByCenterX);
    }
    public void orderByNodeCenterX(List<INode> lRec) {
        Comparator<INode> compareByCenterX =
                Comparator.comparing(INode::centerX);
        lRec.sort(compareByCenterX);
    }

    public void orderByCenterY(List<Rectangle> lRec) {
        Comparator<Rectangle> compareByCenterY =
                Comparator.comparing(Rectangle::centerY);
        // (Rectangle r1, Rectangle r2) -> r1.centerX().compareTo( r2.centerX() );
        Collections.sort(lRec, compareByCenterY);
    }
    public void orderByNodeCenterY(List<INode> lRec) {
        Comparator<INode> compareByCenterX =
                Comparator.comparing(INode::centerY);
        lRec.sort(compareByCenterX);
    }

    public List<Rectangle> getRectangleList() {
        return rectangleList;
    }


}
