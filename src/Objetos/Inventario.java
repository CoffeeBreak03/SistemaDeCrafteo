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

    public void cargarInventario() throws Exception {/*carga desde el archivo inventario.xml*/}

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
}
