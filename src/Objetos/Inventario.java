package Objetos;

import java.util.HashMap;
import java.util.Map;

public class Inventario {
    private Map<Objeto, Integer> objetos;

    public Inventario(){
        objetos = new HashMap<>();
    }

    public Inventario(Map<Objeto, Integer> objetos){
        this.objetos = objetos;
    }

    public void agregarObjeto(Objeto objeto, int cant){
        objetos.put(objeto, cant);
    }

    public void quitarObjeto(Objeto objeto){
        objetos.remove(objeto);
    }

    public boolean estaEnInventario(Objeto objeto){
        return objetos.containsKey(objeto);
    }

    public Map<Objeto, Integer> getObjetos() {
        return objetos;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Inventario:\n");


        for(Map.Entry<Objeto, Integer> elem : objetos.entrySet()){
            Objeto ing = elem.getKey();
            int cant = elem.getValue();

            str.append(ing.getNombre()).append(" x ").append(cant).append('\n');
        }
        return str.toString();
    }
}
