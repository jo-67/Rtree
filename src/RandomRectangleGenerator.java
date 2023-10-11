import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//while (lista.lengh != cantridad de cuadrrados que quiero)
        //generedor de numeros randoms
        //solo se agregan a la lista si x1-x2 entre 0 y 100 y1 - y2 entre 0 y 100 o inverso con x2 - x1 .. etc
public class RandomRectangleGenerator {
    public static void main(String[] args){
        //random object
        Random ran = new Random();
        int n = (int)Math.pow(2,10); //cantidad de rectangulos que se quieren generar
        int hilbert = 16; //número de la curva (log2 punto maximo)
        List<Rectangle> listaDeRectangulos = new ArrayList<>(); //lista para guardar los rectangulos
        int k = 0;
        while (listaDeRectangulos.size() != n) {

            int max = (500000/1000)*(k+1); //rangos más pequeños para que demore menos
            int min = (500000/1000)*k; //se podria cambiar hasta dividirlo en 5000

            //generando numero entre 0 y 500000 des comentar esto para probar si se demora más o menos
            //int x1 = ran.nextInt(500000);
            //int x2 = ran.nextInt(500000);
            //int y1 = ran.nextInt(500000);
            //int y2 = ran.nextInt(500000);

            int x1 = min + ran.nextInt(max-min + 1);
            int x2 = min + ran.nextInt(max-min + 1);
            int y1 = min + ran.nextInt(max-min + 1);
            int y2 = min + ran.nextInt(max-min + 1);

            //vemos si los numeros sirven
            if (
                    ((x1-x2)<=100 && (x1-x2)>=-100) && ((y1-y2)<=100 && (y1-y2)>=-100)
            )
            {
                Rectangle r = new Rectangle(x1,y1,x2,y2,hilbert);
                listaDeRectangulos.add(r);
                System.out.println(listaDeRectangulos.size());
            }

            if (k == 1000){
                k = 0;
            }else{
                k = k + 1;
            }
            System.out.println(k);

        }
        System.out.println("Se generaron los rectangulos");
        System.out.println(listaDeRectangulos.get(765).x1);
        System.out.println(listaDeRectangulos.get(765).y1);
        System.out.println(listaDeRectangulos.get(765).x2);
        System.out.println(listaDeRectangulos.get(765).y2);

        HilbertCurve nearest = new HilbertCurve(listaDeRectangulos, 100);
        nearest.orderByHilbertCurve(listaDeRectangulos);
        List<Rectangle> nuevaLista = nearest.getRectangleList();
        System.out.println(nuevaLista);
        for (int i = 0; i<nuevaLista.size(); i++ ){
            System.out.println(nuevaLista.get(i).getHilbertCurvePosition());
        }

        RTree rtree = nearest.createRTreeH();
        System.out.println(rtree);
    }

}
