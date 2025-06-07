import Objetos.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            SistemaDeCrafteo sistemaDeCrafteo= new SistemaDeCrafteo();

            for (Receta receta : sistemaDeCrafteo.getRecetario().getRecetas() ) {
                System.out.println("Prueba Ingredientes básicos: " + receta.getObjetoProducido().getNombre());
                System.out.println(sistemaDeCrafteo.ingredientesBasicos(receta.getObjetoProducido()));
            }
            System.out.println("==============================");
            for (Receta receta : sistemaDeCrafteo.getRecetario().getRecetas() ) {
                System.out.println("Prueba craftear: " + receta.getObjetoProducido().getNombre());
                sistemaDeCrafteo.craftear(receta);
                System.out.println();
            }

            System.out.println(sistemaDeCrafteo.getInventario());
            System.out.println(sistemaDeCrafteo.getRecetario());

            sistemaDeCrafteo.getInventario().guardarInventario();
            sistemaDeCrafteo.getRecetario().guardarRecetario();
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }
}
