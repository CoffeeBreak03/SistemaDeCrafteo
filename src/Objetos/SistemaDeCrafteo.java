package Objetos;

import java.util.ArrayList;
import java.util.Map;

public class SistemaDeCrafteo {
    private HistorialDeCrafteo historialDeCrafteo;
    private Inventario inventario;
    private Recetario recetario;

    public SistemaDeCrafteo(){
        historialDeCrafteo = new HistorialDeCrafteo();
        inventario = new Inventario();
        recetario = new Recetario();
    }

    public void ingredientesNecesarios(String objeto){
        int i=1;

        for(Receta rec : recetario.buscarRecetas(objeto)){
            System.out.println("Ingredientes para Receta " + i + ":\n");
            for(Map.Entry<Objeto, Integer> elem : rec.getIngredientes().entrySet()){
                Objeto ingrediente = elem.getKey();
                int cantRequerida = elem.getValue();

                System.out.println(ingrediente.getNombre() + " x " + cantRequerida);
            }
            System.out.println("\nTiempo de crafteo" + rec.getTiempoBase() + "\n");
        }
    }

    public void ingBasicosNecesarios(String objeto){
        int i=1;

        for(Receta rec : recetario.buscarRecetas(objeto)){
            System.out.println("Posibles ingredientes basicos para Receta " + i + ":\n");
            for(PosibleReceta posRec : rec.getIngredientesBasicos()){
                System.out.println(posRec);
                System.out.println("\nO\n");
            }
            System.out.println('\n');
        }
    }


}
