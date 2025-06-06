import Objetos.Objeto;
import Objetos.PosibleReceta;
import Objetos.Receta;
import Objetos.SistemaDeCrafteo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            SistemaDeCrafteo sistemaDeCrafteo= new SistemaDeCrafteo();
//            sistemaDeCrafteo.ingredientesNecesarios("Maquina");
//            sistemaDeCrafteo.ingBasicosNecesarios("Maquina");
            System.out.println(sistemaDeCrafteo.getInventario());
            System.out.println(sistemaDeCrafteo.getRecetario());
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }
}
