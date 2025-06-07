package Objetos;

import java.util.ArrayList;
import java.util.HashMap;
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

//            recetario = new RecetaXML("archivos/recetas.xml").cargar();
//            inventario = new InventarioXML("archivos/inventario.xml", recetario).cargar();
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

//    public void ingBasicosNecesarios(String objeto){
//        int i=1;
//
//        for(Receta rec : recetario.buscarRecetas(objeto)){
//            System.out.println("Posibles ingredientes basicos para Receta " + i + ":");
//            ArrayList<PosibleReceta> posiblesRecetas = rec.getIngredientesBasicos();
//            for(PosibleReceta posRec : posiblesRecetas){
//                System.out.println(posRec);
//                if(posiblesRecetas.size() > 1){System.out.println("\nO\n");}
//            }
//            System.out.println('\n');
//        }
//    }


    public Recetario getRecetario() {return recetario; }

    public Map<Objeto, Integer> ingredientesBasicos(Objeto obj) {
        Map<Objeto, Integer> basicos = new HashMap<>();

        if (obj.esBasico()) {
            basicos.put(obj,1);
        }
        else {
            Receta receta = recetario.buscarRecetaPorObjetoProducido(obj);
            //le va a pedir a cada uno de sus ingredientes los objetos basicos que lo conforman
            for (Map.Entry<Objeto, Integer> elemento : receta.getIngredientes().entrySet()) {
                Objeto ingrediente = elemento.getKey();
                int cantidadRequerida = elemento.getValue();

                Map<Objeto, Integer> subBasicos = ingredientesBasicos(ingrediente);

                for (Map.Entry<Objeto, Integer> subEntry : subBasicos.entrySet()) {
                    Objeto subIngredienteBasico = subEntry.getKey();
                    int cantidadProducidaPorSubIngrediente = subEntry.getValue();

                    // La cantidad final del sub-ingrediente básico es la cantidad
                    // que se necesita del ingrediente intermedio * la cantidad que produce el sub-ingrediente básico
                    int cantidadTotalParaEsteBasico = cantidadRequerida * cantidadProducidaPorSubIngrediente;

                    // Fusionar en el mapa 'basicos'. Si ya existe, suma las cantidades.
                    basicos.merge(subIngredienteBasico, cantidadTotalParaEsteBasico, Integer::sum);
                }
            }
        }

        return basicos;
    }

    public boolean craftear(Receta receta) {
        if (receta == null) {
            System.out.println("Error: No se proporcionó una receta válida.");
            return false;
        }

        System.out.println("\nIntentando craftear: " + receta.getObjetoProducido().getNombre());

        // 1. Verificar si hay suficientes ingredientes en el inventario
        for (Map.Entry<Objeto, Integer> entry : receta.getIngredientes().entrySet()) {
            Objeto ingrediente = entry.getKey();
            int cantidadRequerida = entry.getValue();

            if (!inventario.tieneSuficientes(ingrediente, cantidadRequerida)) {
                System.out.println("No hay suficientes " + ingrediente.getNombre() + " en el inventario.");
                return false; // No se puede craftear
            }
        }

        // 2. Consumir ingredientes del inventario
        for (Map.Entry<Objeto, Integer> entry : receta.getIngredientes().entrySet()) {
            Objeto ingrediente = entry.getKey();
            int cantidadRequerida = entry.getValue();
            inventario.consumirObjeto(ingrediente, cantidadRequerida);
            System.out.println("Consumidos " + cantidadRequerida + " de " + ingrediente.getNombre());
        }

        // 3. Añadir el objeto producido al inventario
        inventario.agregarObjeto(receta.getObjetoProducido(), receta.getCantidadProducida());
        System.out.println("Producidos " + receta.getCantidadProducida() + " de " + receta.getObjetoProducido().getNombre());

        // 4. Calcular el tiempo total de crafteo (incluyendo recursión para intermedios)
        int tiempoTotalCrafteo = calcularTiempoTotalCrafteo(receta);

        System.out.println("Crafteo exitoso: " + receta.getObjetoProducido().getNombre() +
                " (cantidad: " + receta.getCantidadProducida() +
                ", tiempo total: " + tiempoTotalCrafteo + " unidades)");

        // Opcional: Registrar en el historial de crafteo
        // historialDeCrafteo.registrarCrafteo(receta.getObjetoProducido(), receta.getCantidadProducida(), tiempoTotalCrafteo);

        return true;
    }

    private int calcularTiempoTotalCrafteo(Receta receta) {
        int tiempoAcumulado = receta.getTiempoBase(); // Tiempo base de la receta actual

        for (Map.Entry<Objeto, Integer> entry : receta.getIngredientes().entrySet()) {
            Objeto ingrediente = entry.getKey();
            int cantidadIngrediente = entry.getValue(); // Cantidad de este ingrediente que pide la receta

            if (!ingrediente.esBasico()) {
                // Si el ingrediente es intermedio, necesitamos su propia receta
                Receta subReceta = recetario.buscarRecetaPorObjetoProducido((ObjetoIntermedio) ingrediente);
                if (subReceta != null) {
                    // Calculamos recursivamente el tiempo de producción para el sub-ingrediente
                    // y multiplicamos por la cantidad necesaria de ese sub-ingrediente.
                    tiempoAcumulado += (cantidadIngrediente * calcularTiempoTotalCrafteo(subReceta));
                } else {
                    // Si un ingrediente intermedio no tiene receta, no podemos calcular su tiempo de producción.
                    // Podría ser un error en la configuración del recetario o un objeto no crafteable.
                    System.err.println("Advertencia: Ingrediente intermedio '" + ingrediente.getNombre() + "' no tiene receta definida. No se sumará su tiempo.");
                }
            }
            // Si es ObjetoBasico, no suma tiempo adicional de "producción" en este contexto.
        }
        return tiempoAcumulado;
    }
}
