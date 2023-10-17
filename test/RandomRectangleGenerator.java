import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomRectangleGenerator {
    public static List<Rectangle> crearRectangulos(int cantidad,int tamano, int hilbert){
        //random object
        Random ran = new Random();
        int n = (int)Math.pow(2,cantidad); //cantidad de rectangulos que se quieren generar
        List<Rectangle> listaDeRectangulos = new ArrayList<>(); //lista para guardar los rectangulos
        int k = 0;
        while (listaDeRectangulos.size() != n) {

            int max = (500000 / 5000) * (k + 1);
            int min = (500000 / 5000) * k;

            int kmax = 1000;

            if (tamano > 100) {
                max = (500000 / 5) * (k + 1); //rangos más pequeños para que demore menos
                min = (500000 / 5) * k; //se podria cambiar hasta dividirlo en 5000
                kmax = 5;
            }


            int x1 = min + ran.nextInt(max-min + 1);
            int x2 = min + ran.nextInt(max-min + 1);
            int y1 = min + ran.nextInt(max-min + 1);
            int y2 = min + ran.nextInt(max-min + 1);

            if (
                    ((x1-x2)<=tamano && (x1-x2)>=-tamano) && ((y1-y2)<=tamano && (y1-y2)>=-tamano)
            )
            {
                Rectangle r = new Rectangle(x1,y1,x2,y2,hilbert);
                listaDeRectangulos.add(r);
            }

            if (k == kmax){
                k = 0;
            }else{
                k = k + 1;
            }

        }

        return listaDeRectangulos;
    }

    public static void main(String[] args){
        crearRectangulos(10,100,12);
        crearRectangulos(10, 100000, 0);
    }

}
