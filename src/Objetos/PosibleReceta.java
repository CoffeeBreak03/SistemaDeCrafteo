package Objetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PosibleReceta {
    private HashMap<Objeto, Integer> ingredientes;
    private HashSet<Objeto> mesasRequeridas;
    private int tiempoCrafteo;
    private int cantProducida;

    public PosibleReceta(){
        ingredientes = new HashMap<>();
        mesasRequeridas = new HashSet<>();
        tiempoCrafteo = 0;
    }

    public void agregarIng(Objeto ing, int cant){
        ingredientes.put(ing, cant);
    }

    public void agregarMesaRequerida(Objeto mesa){
        mesasRequeridas.add(mesa);
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
        this.mesasRequeridas.addAll(otro.mesasRequeridas);
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

    public HashSet<Objeto> getMesasRequeridas() {
        return mesasRequeridas;
    }

    public int getCantProducida() {
        return cantProducida;
    }

    public void setMesasRequeridas(HashSet<Objeto> mesasRequeridas) {
        this.mesasRequeridas = mesasRequeridas;
    }

    public void setIngredientes(HashMap<Objeto, Integer> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setTiempoCrafteo(int tiempoCrafteo) {
        this.tiempoCrafteo = tiempoCrafteo;
    }

    public void setCantProducida(int cantProducida) {
        this.cantProducida = cantProducida;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if(ingredientes.isEmpty()){
            sb.append("No hay ingredientes faltantes.\n");
        } else{
            for (Map.Entry<Objeto, Integer> entry : ingredientes.entrySet()) {
                sb.append(entry.getKey().getNombre()).append(" x ").append(entry.getValue()).append("\n");
            }
        }


        sb.append("\nMesas requeridas: ");
        for(Objeto mesa : mesasRequeridas){
            sb.append("\n-").append(mesa.getNombre());
        }
        sb.append("\nTiempo de crafteo total: ").append(tiempoCrafteo).append(" minutos");

        return sb.toString();
    }
}
