package Objetos;

import java.util.HashMap;
import java.util.Map;

public class Inventario {
    private Map<Objeto, Integer> objetos;

    public Inventario(){
        objetos = new InventarioXML("archivos/inventario2.xml").cargar();
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
        StringBuilder sb = new StringBuilder("=== INVENTARIO ===\n");
        for (Map.Entry<Objeto, Integer> entry : objetos.entrySet()) {
            sb.append(entry.getKey())  // Usa el toString() de Objeto
                    .append(" x")
                    .append(entry.getValue())
                    .append("\n");
        }
        return sb.toString();
    }
}
