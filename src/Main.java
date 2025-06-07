import Objetos.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            SistemaDeCrafteo sistemaDeCrafteo= new SistemaDeCrafteo();
            System.out.println("=====RECETARIO INICIAL========");
            System.out.println(sistemaDeCrafteo.getRecetario());
            System.out.println("=====INVENTARIO INICIAL=======");
            System.out.println(sistemaDeCrafteo.getInventario());
            System.out.println("=====INGREDIENTES BASICOS=====");
            for (Receta receta : sistemaDeCrafteo.getRecetario().getRecetas() ) {
                System.out.println("Prueba Ingredientes básicos: " + receta.getObjetoProducido().getNombre());
                System.out.println(sistemaDeCrafteo.ingredientesBasicos(receta.getObjetoProducido()));
            }
            System.out.println("=======PRUEBA CRAFTEO=========");
            for (Receta receta : sistemaDeCrafteo.getRecetario().getRecetas() ) {
                System.out.println("Prueba craftear: " + receta.getObjetoProducido().getNombre());
                sistemaDeCrafteo.craftear(receta);
                System.out.println();
            }
            System.out.println("=====INVENTARIO FINAL=========");
            System.out.println(sistemaDeCrafteo.getInventario());
            
            System.out.println("=====GUARDAR XML==============");
            sistemaDeCrafteo.getInventario().guardarInventario();
            sistemaDeCrafteo.getRecetario().guardarRecetario();
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }
}
