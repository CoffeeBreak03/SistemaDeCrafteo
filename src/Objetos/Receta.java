package Objetos;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Receta {
    private String objetoProducido;
    private int cantidadProducida;
    private int tiempoBase;
    private Objeto mesaRequerida;
    private Map<Objeto, Integer> ingredientes;

    public Receta(Map<Objeto, Integer> ingredientes, int tiempoBase, int cantidadProducida, Objeto mesaRequerida, String objetoProducido) {
        this.ingredientes = ingredientes;
        this.tiempoBase = tiempoBase;
        this.cantidadProducida = cantidadProducida;
        this.objetoProducido = objetoProducido;
        this.mesaRequerida = mesaRequerida;
    }

    public Receta(){
    }

    public String getObjetoProducido() {
        return objetoProducido;
    }

    public Objeto getMesaRequerida() {
        return mesaRequerida;
    }

    public int getCantidadProducida() {
        return cantidadProducida;
    }

    public int getTiempoBase() {
        return tiempoBase;
    }

    public Map<Objeto, Integer> getIngredientes(){
        return ingredientes;
    }

    public ArrayList<PosibleReceta> getIngredientesBasicos() {
        //Aca estaran las posibles combinaciones de ing. basicos en la medida que aparezcan, si no, sera una sola
        ArrayList<PosibleReceta> posiblesRecetas = new ArrayList<>();

        PosibleReceta basicos = new PosibleReceta();

        for (Map.Entry<Objeto, Integer> elemento : ingredientes.entrySet()) {
            Objeto ingrediente = elemento.getKey();
            int cantRequerida = elemento.getValue();

            if (ingrediente.esBasico()) {
                basicos.getIngredientes().merge(ingrediente, cantRequerida, Integer::sum);
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
                    posiblesRecetas = Util.combinarListas(posiblesRecetas, listaAux);
                } else {
                    basicos.combinarConPosReceta(listaAux.getFirst());
                }
            }
        }

        basicos.sumarTiempo(this.tiempoBase);
        basicos.agregarMesaRequerida(this.mesaRequerida);

        //agrego los basicos obtenidos a las posibles combinaciones de basicos. Si no habia, basicos sera la unica.
        basicos.combinarConListaPosReceta(posiblesRecetas);

        return posiblesRecetas;
    }

    public void setObjetoProducido(String objetoProducido) {
        this.objetoProducido = objetoProducido;
    }

    public void setCantidadProducida(int cantidadProducida) {
        this.cantidadProducida = cantidadProducida;
    }

    public void setTiempoBase(int tiempoBase) {
        this.tiempoBase = tiempoBase;
    }

    public void setMesaRequerida(Objeto mesaRequerida) {
        this.mesaRequerida = mesaRequerida;
    }

    public void setIngredientes(Map<Objeto, Integer> ingredientes) {
        this.ingredientes = ingredientes;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Objeto producido: ").append(objetoProducido).append("\n");
        sb.append("Cantidad producida: ").append(cantidadProducida).append("\n");
        sb.append("Tiempo de crafteo: ").append(tiempoBase).append(" minutos\n");
        sb.append("Mesa requerida: ").append(mesaRequerida != null ? mesaRequerida.getNombre() : "Ninguna").append("\n");
        sb.append("Ingredientes:\n");

        for (Map.Entry<Objeto, Integer> entry : ingredientes.entrySet()) {
            Objeto obj = entry.getKey();
            int cantidad = entry.getValue();
            sb.append("    - ").append(obj.getNombre()).append(" x ").append(cantidad).append("\n");
        }

        return sb.toString();
    }
}

