package Objetos;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Receta {
    private String objetoProducido;
    private int cantidadProducida;
    private int tiempoBase;
    private Map<Objeto, Integer> ingredientes;

    public Receta(Map<Objeto, Integer> ingredientes, int tiempoBase, int cantidadProducida, String objetoProducido) {
        this.ingredientes = ingredientes;
        this.tiempoBase = tiempoBase;
        this.cantidadProducida = cantidadProducida;
        this.objetoProducido = objetoProducido;
    }

    public ArrayList<PosibleReceta> getIngredientesBasicos() {
        //Aca estaran las posibles combinaciones de ing. basicos en la medida que aparezcan, si no, sera una sola
        ArrayList<PosibleReceta> posiblesRecetas = new ArrayList<PosibleReceta>();

        PosibleReceta basicos = new PosibleReceta();

        for (Map.Entry<Objeto, Integer> elemento : ingredientes.entrySet()) {
            Objeto ingrediente = elemento.getKey();
            int cantRequerida = elemento.getValue();

            if (ingrediente.esBasico()) {
                basicos.getIngredientes().put(ingrediente, cantRequerida);
            } else {
                //voy almacenando los basicos del ing. compuesto teniendo en cuenta que, si tiene mas de una receta,
                //listaAux va a tener igual cantidad de posiblesRecetas
                ArrayList<PosibleReceta> listaAux = new ArrayList<>();
                for (Receta receta : ingrediente.getRecetas()) {
                    listaAux.addAll(receta.getIngredientesBasicos());
                }

                //multiplico por la cantidad requerida a todas las posiblesRecetas, tanto a las cantidades como
                //al tiempo (como sumarTiempo hace una suma: en vez de hacer tiempo + tiempo * cant, que seria igual a
                //tiempo * (cant+1), hago tiempo + tiempo * (cant-1)
                for (PosibleReceta posRec : listaAux) {
                    posRec.multCantidades(cantRequerida);
                    posRec.sumarTiempo(posRec.getTiempoCrafteo() * (cantRequerida - 1));
                }

                //si el ing. compuesto solo tenia una receta, agrego los basicos obtenidos a la lista de basicos
                //si tenia mas de una receta, voy a tener que hacer la combinacion entre las posibles recetas de ese
                // compuesto y las que ya tenia.
                if (listaAux.size() > 1) {
                    posiblesRecetas = combinarListas(posiblesRecetas, listaAux);
                } else {
                    basicos.combinarConPosReceta(listaAux.getFirst());
                }
            }
        }

        basicos.sumarTiempo(this.tiempoBase);

        //agrego los basicos obtenidos a las posibles combinaciones de basicos. Si no habia, basicos sera la unica.
        basicos.combinarConListaPosReceta(posiblesRecetas);

        return posiblesRecetas;
    }

    //Este metodo realiza, dadas dos lista/conjuntos M y N: M x N (como en mate. discreta)
    private ArrayList<PosibleReceta> combinarListas(ArrayList<PosibleReceta> m, ArrayList<PosibleReceta> n){
        ArrayList<PosibleReceta> resultado = new ArrayList<>();

        if(!m.isEmpty()) {
            for(PosibleReceta posRecM: m){
                for(PosibleReceta posRecN : n){
                    PosibleReceta copiaM = posRecM.crearCopia();
                    copiaM.combinarConPosReceta(posRecN);
                    resultado.add(copiaM);
                }
            }
        }else{
            resultado.addAll(n);
        }
        return resultado;
    }
}

