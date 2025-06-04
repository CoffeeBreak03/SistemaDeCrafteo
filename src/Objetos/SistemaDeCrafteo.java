package Objetos;

import java.util.ArrayList;
import java.util.Map;

public class SistemaDeCrafteo {
    private HistorialDeCrafteo historialDeCrafteo;
    private Inventario inventario;
    private Recetario recetario;

    public SistemaDeCrafteo() throws Exception{
        try{
            historialDeCrafteo = new HistorialDeCrafteo();
            inventario = new Inventario();
            recetario = new Recetario();

            recetario = new CargadorDeRecetasXML("archivos/recetas.xml").cargar();
            inventario = new CargadorDeInventarioXML("archivos/inventario.xml", recetario).cargar();
        }catch (Exception e){
            System.out.println("No se pudieron abrir los archivos");
        }
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void ingredientesNecesarios(String objeto){
        int i=1;

        for(Receta rec : recetario.buscarRecetas(objeto)){
            System.out.println("Ingredientes para Receta " + i + ":");
            for(Map.Entry<Objeto, Integer> elem : rec.getIngredientes().entrySet()){
                Objeto ingrediente = elem.getKey();
                int cantRequerida = elem.getValue();

                System.out.println(ingrediente.getNombre() + " x " + cantRequerida);
            }
            System.out.println("\nTiempo de crafteo: " + rec.getTiempoBase() + "\n");
        }
    }

    public void ingBasicosNecesarios(String objeto){
        int i=1;

        for(Receta rec : recetario.buscarRecetas(objeto)){
            System.out.println("Posibles ingredientes basicos para Receta " + i + ":");
            ArrayList<PosibleReceta> posiblesRecetas = rec.getIngredientesBasicos();
            for(PosibleReceta posRec : posiblesRecetas){
                System.out.println(posRec);
                if(posiblesRecetas.size() > 1){System.out.println("\nO\n");}
            }
            System.out.println('\n');
        }
    }




}
