package Objetos;

import org.xml.sax.SAXException;

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

        recetario = new CargadorDeRecetasXML("archivos/recetas.xml").cargar();
        inventario = new CargadorDeInventarioXML("archivos/inventario.xml", recetario).cargar();
    }

    public Inventario getInventario() {
        return inventario;
    }

    //TODO DEFINIR UNA UNIDAD DE TIEMPO PARA EL TIEMPO DE CRAFTEO
    public void ingredientesNecesarios(String objeto){
        int i=1;

        //verificar si tiene recetas o es basico antes de operar
        for(Receta rec : recetario.buscarRecetas(objeto)){
            System.out.println("Ingredientes para Receta " + i + ":");
            for(Map.Entry<Objeto, Integer> elem : rec.getIngredientes().entrySet()){
                Objeto ingrediente = elem.getKey();
                int cantRequerida = elem.getValue();

                System.out.println(ingrediente.getNombre() + " x " + cantRequerida);
            }
            System.out.println("\nTiempo de crafteo: " + rec.getTiempoBase() + "\n");
            i++;
        }
    }

    public void ingBasicosNecesarios(String objeto){
        int i=1;
        //verificar si tiene recetas o es basico antes de operar
        for(Receta rec : recetario.buscarRecetas(objeto)){
            System.out.println("Posibles ingredientes basicos para Receta " + i + ":");
            ArrayList<PosibleReceta> posiblesRecetas = rec.getIngredientesBasicos();
            for(PosibleReceta posRec : posiblesRecetas){
                System.out.println(posRec);
                if(posiblesRecetas.size() > 1){System.out.println("\nO\n");}
            }
            System.out.println('\n');
            i++;
        }
    }

    public void ingFaltantesParaCraftear(String objeto, int cantACraftear){
        if(cantACraftear == 0){
            System.out.println("La cantidad a craftear es 0.");
            return;
        }

        ArrayList<Receta> recetasObj;
        recetasObj = recetario.buscarRecetas(objeto);
        if(recetasObj.isEmpty()){
            System.out.println("El objeto no tiene recetas");
            return;
        }

        int i=1;
        boolean hayFaltantes = false;

        for(Receta rec: recetasObj){
            System.out.println("Ingredientes faltantes para Receta " + i + ":");
            for (Map.Entry<Objeto, Integer> entry : rec.getIngredientes().entrySet()) {
                Objeto ing = entry.getKey();
                int cantReq = entry.getValue() * cantACraftear;

                boolean estaEnInventario = inventario.getObjetos().containsKey(ing);

                if (!estaEnInventario || inventario.getObjetos().get(ing) < cantReq) {
                    hayFaltantes = true;
                    System.out.println('-' + ing.getNombre() + " x " + (cantReq - inventario.getObjetos().getOrDefault(ing, 0)));
                }
            }
            if(!hayFaltantes){
                System.out.println("No hay ingredientes faltantes");
            }

            System.out.println();
            if(rec.getMesaRequerida() != null) {
                if (!inventario.getObjetos().containsKey(rec.getMesaRequerida())) {
                    System.out.println("Mesa faltante: " + rec.getMesaRequerida().getNombre());
                }
            }

            System.out.println("Tiempo de crafteo: " + rec.getTiempoBase() * cantACraftear + '\n');

            i++;
        }
    }

    public void ingBasicosFaltantesParaCraftear(String objeto, int cantACraftear){
        if(cantACraftear == 0){
            System.out.println("La cantidad a craftear es 0.");
            return;
        }

        ArrayList<PosibleFaltante> posibleFaltantes = obtenerPosiblesFaltantes(objeto, cantACraftear, inventario);

        if(!posibleFaltantes.isEmpty()) {
            PosibleFaltante menorFaltanteDeObj = null;

            for (PosibleFaltante posFaltanteObj : posibleFaltantes) {
                if (menorFaltanteDeObj == null) {
                    menorFaltanteDeObj = posFaltanteObj;
                } else if (Util.compararPosRecetas(posFaltanteObj.faltantes, menorFaltanteDeObj.faltantes) < 0) {
                    menorFaltanteDeObj = posFaltanteObj;
                }
            }

            if (menorFaltanteDeObj != null) {
                System.out.println("La menor cantidad de ingredientes basicos faltantes para craftear " + cantACraftear + ' ' + objeto +
                        " desde cero, incluyendo el costo de las mesas faltantes, es la siguiente:");
                System.out.println(menorFaltanteDeObj);
            }
        } else{
            System.out.println("El objeto no tiene recetas");
        }
    }

    public void cantidadCrafteable(String objeto){
        int cant = obtenerCantidadCrafteable(objeto);

        if (cant >= 0) {
            System.out.println("Se pueden craftear " + cant + ' ' + objeto + '.');
        } else {
            System.out.println("El objeto no tiene recetas.");
        }
    }

    //TODO: PREGUNTAR SI DEBERIA DEVOLVER TIEMPO TAMBIEN
    private int obtenerCantidadCrafteable(String objeto){
        int i=0;
        PosibleFaltante menorFaltanteDeObj = obtenerMinimoFaltante(objeto, i+1, inventario);

        if (menorFaltanteDeObj != null) {
            while(menorFaltanteDeObj.faltantes.getIngredientes().isEmpty()){
                i++;
                menorFaltanteDeObj = obtenerMinimoFaltante(objeto, i+1, inventario);
            }

            return i;
        } else {
            return -1;
        }
    }

    public void craftearObjeto(String objeto, int cantACraftear){
        if(cantACraftear == 0){
            System.out.println("La cantidad a craftear es 0.");
            return;
        }

        int cantCrafteable = obtenerCantidadCrafteable(objeto);
        if(cantCrafteable < cantACraftear){
            if(cantCrafteable >= 0){
                System.out.println("No hay suficientes materiales para craftear " + cantACraftear + ' ' + objeto);
            } else {
                System.out.println("El objeto no tiene receta.");
            }
            return;
        }

        System.out.println("Inventario antes de craftear: ");
        System.out.println(inventario);

        PosibleFaltante invDespuesDeCraftear = obtenerMinimoFaltante(objeto, cantACraftear, inventario);
        invDespuesDeCraftear.estadoDeInv.agregarObjeto(
                new Objeto(objeto, recetario.buscarRecetas(objeto)), cantACraftear);

        inventario = invDespuesDeCraftear.estadoDeInv;
        historialDeCrafteo = invDespuesDeCraftear.historialActual;

        System.out.println("El crafteo tardo " + invDespuesDeCraftear.faltantes.getTiempoCrafteo() + " minutos."); //para la consigna solo necesitas esto
        System.out.println("Inventario despues de craftear: ");
        System.out.println(inventario);
        System.out.println();

        System.out.println("Historial de crafteo: ");
        System.out.println(historialDeCrafteo);
    }

    //Lo unico que no puede hacer esta funcion es predecir, al elegir una receta por sobre otra al intentar craftear uno de los ing. faltantes,
    //si quedaria un sobrante de este ultimo que se pudiera utilizar para cubrir el crafteo de uno de los otros faltantes y abaratar el costo. Esto
    //depende del orden en el que esten los ingredientes a la hora de leer la receta y de la receta que se termine eligiendo para el ing. faltante,
    //que el programa consideraria como la mas eficiente.
    private ArrayList<PosibleFaltante> obtenerPosiblesFaltantes(String objeto, int cantACraftear, Inventario invActual){
        ArrayList<PosibleFaltante> listaPosiblesFaltantes = new ArrayList<>();

        for(Receta rec : recetario.buscarRecetas(objeto)){
            PosibleFaltante faltantesDeRec = new PosibleFaltante(new PosibleReceta(), invActual.crearCopia());
            PosibleReceta faltantes = new PosibleReceta();
            int crafteosNecesarios = (cantACraftear + rec.getCantidadProducida() - 1) / rec.getCantidadProducida();

            //si requiere mesa de crafteo veo si la tengo o si la puedo craftear, si no, tambien incluyo su costo
            if(rec.getMesaRequerida() != null) {
                if (!faltantesDeRec.estadoDeInv.estaEnInventario(rec.getMesaRequerida())) {
                    PosibleFaltante menorFaltanteDeMesa = obtenerMinimoFaltante(rec.getMesaRequerida().getNombre(), 1, faltantesDeRec.estadoDeInv);

                    if(menorFaltanteDeMesa != null){
                        faltantesDeRec.estadoDeInv = menorFaltanteDeMesa.estadoDeInv;
                        faltantesDeRec.faltantes = menorFaltanteDeMesa.faltantes;
                        //en este caso agrego la mesa independientemente de si se pudo craftear o no porque si no, el resto de faltantes
                        //no se enteraran que ya se tuvo en cuenta el costo de craftear la mesa
                        faltantesDeRec.estadoDeInv.agregarObjeto(rec.getMesaRequerida());
                        faltantesDeRec.historialActual.getRegistros().addAll(menorFaltanteDeMesa.historialActual.getRegistros());
                    }

                    faltantesDeRec.faltantes.agregarMesaRequerida(rec.getMesaRequerida());
                }
            }

            faltantesDeRec.faltantes.setCantProducida(rec.getCantidadProducida());

            for(Map.Entry<Objeto, Integer> elem : rec.getIngredientes().entrySet()){
                Objeto ing = elem.getKey();
                int cantReq = elem.getValue() * crafteosNecesarios;
                int cantEnInv = faltantesDeRec.estadoDeInv.getObjetos().getOrDefault(ing, 0);

                if(cantEnInv >= cantReq){
                    faltantesDeRec.estadoDeInv.getObjetos().replace(ing, cantEnInv - cantReq);
                } else{
                    faltantesDeRec.estadoDeInv.getObjetos().replace(ing, 0);
                    faltantes.getIngredientes().put(ing, cantReq - cantEnInv);
                }

                faltantesDeRec.estadoDeInv.getObjetos().remove(ing, 0);
            }

            if(!faltantes.getIngredientes().isEmpty()) {
                ArrayList<PosibleFaltante> menoresFaltantesPorObj = new ArrayList<>();

                for (Map.Entry<Objeto, Integer> elem : faltantes.getIngredientes().entrySet()) {
                    Objeto faltante = elem.getKey();
                    int cantFalt = elem.getValue();

                    if (faltante.esBasico()) {
                        faltantesDeRec.faltantes.getIngredientes().put(faltante, cantFalt);
                    } else {
                        PosibleFaltante menorFaltanteDeObj = obtenerMinimoFaltante(faltante.getNombre(), cantFalt, faltantesDeRec.estadoDeInv);

                        menoresFaltantesPorObj.add(menorFaltanteDeObj);
                        faltantesDeRec.estadoDeInv = menorFaltanteDeObj.estadoDeInv;
                        faltantesDeRec.historialActual.getRegistros().addAll(menorFaltanteDeObj.historialActual.getRegistros());
                    }
                }

                for (PosibleFaltante menorFaltanteObj : menoresFaltantesPorObj) {
                    faltantesDeRec.faltantes.combinarConPosReceta(menorFaltanteObj.faltantes);
                }
            } else {
                if((rec.getCantidadProducida() * crafteosNecesarios) - cantACraftear != 0)
                    faltantesDeRec.estadoDeInv.agregarObjeto(
                        new Objeto(objeto, recetario.buscarRecetas(objeto)),
                        (rec.getCantidadProducida() * crafteosNecesarios) - cantACraftear
                );
            }
            faltantesDeRec.faltantes.sumarTiempo(rec.getTiempoBase() * cantACraftear);
            listaPosiblesFaltantes.add(faltantesDeRec);
        }

        return listaPosiblesFaltantes;
    }

    private PosibleFaltante obtenerMinimoFaltante(String objeto, int cantACraftear, Inventario invActual){
        ArrayList<PosibleFaltante> faltantesDeObj = obtenerPosiblesFaltantes(objeto, cantACraftear, invActual);
        PosibleFaltante menorFaltanteDeObj = null;

        if (!faltantesDeObj.isEmpty()) {

            for (PosibleFaltante posFaltanteObj : faltantesDeObj) {
                if (menorFaltanteDeObj == null) {
                    menorFaltanteDeObj = posFaltanteObj;
                } else if (Util.compararPosRecetas(posFaltanteObj.faltantes, menorFaltanteDeObj.faltantes) < 0) {
                    menorFaltanteDeObj = posFaltanteObj;
                }
            }

            if(menorFaltanteDeObj.faltantes.getIngredientes().isEmpty()){
                menorFaltanteDeObj.historialActual.agregarRegistro(
                        new Objeto(objeto, recetario.buscarRecetas(objeto)),
                        menorFaltanteDeObj.faltantes.getCantProducida() *
                                (cantACraftear + menorFaltanteDeObj.faltantes.getCantProducida() - 1) / menorFaltanteDeObj.faltantes.getCantProducida());
            }
        }
        return menorFaltanteDeObj;
    }
}



