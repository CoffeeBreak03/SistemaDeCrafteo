package Objetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PosibleReceta {
    private HashMap<Objeto, Integer> ingredientes;
    private int tiempoCrafteo;

    public PosibleReceta(){
        ingredientes = new HashMap<Objeto, Integer>();
        tiempoCrafteo = 0;
    }

    public void agregarIng(Objeto ing, int cant){
        ingredientes.put(ing, cant);
    }

    public void sumarTiempo(int tiempo){
        tiempoCrafteo += tiempo;
    }

    public void multCantidades(int cant){
       ingredientes.replaceAll((k,v) -> v * cant);
    }

    public void combinarConListaPosReceta(ArrayList<PosibleReceta> posiblesRecetas){
        if(posiblesRecetas.isEmpty()){
            posiblesRecetas.add(this);
            return;
        }

        for(PosibleReceta posRec : posiblesRecetas){
            posRec.combinarConPosReceta(this);
        }
    }

    public void combinarConPosReceta(PosibleReceta otro){
        for(Map.Entry<Objeto, Integer> elemento : otro.ingredientes.entrySet()){
            Objeto ingrediente = elemento.getKey();
            int cantidad = elemento.getValue();

            this.ingredientes.merge(ingrediente, cantidad, Integer::sum);
        }

        this.sumarTiempo(otro.tiempoCrafteo);
    }

    public PosibleReceta crearCopia(){
        PosibleReceta copia = new PosibleReceta();
        for(Map.Entry<Objeto, Integer> elem: this.ingredientes.entrySet()){
            Objeto ingrediente = elem.getKey();
            int cantidad = elem.getValue();

            copia.ingredientes.put(ingrediente, cantidad);
        }
        copia.tiempoCrafteo = this.tiempoCrafteo;

        return copia;
    }

    public int getTiempoCrafteo() {
        return tiempoCrafteo;
    }

    public HashMap<Objeto, Integer> getIngredientes() {
        return ingredientes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Objeto, Integer> entry : ingredientes.entrySet()) {
            sb.append(entry.getKey().getNombre()).append(" x ").append(entry.getValue()).append("\n");
        }

        sb.append("\nTiempo de crafteo = ").append(tiempoCrafteo);

        return sb.toString();
    }
}
