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
        objetos.merge(objeto, cant, Integer::sum);
    }

    public void agregarObjeto(Objeto objeto){
        agregarObjeto(objeto, 1);
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
        StringBuilder str = new StringBuilder("=== INVENTARIO ===\n");
        for(Map.Entry<Objeto, Integer> elem : objetos.entrySet()){
            Objeto ing = elem.getKey();
            int cant = elem.getValue();

            str.append(ing).append(" x ").append(cant).append('\n');
        }
        return str.toString();
    }

    public void guardarInventario() {
        new InventarioXML("out/inventario_salida.xml").guardar(objetos);
    }

    public boolean tieneSuficientes(Objeto ingrediente, int cantidadRequerida) {
        return objetos.getOrDefault(ingrediente, 0) >= cantidadRequerida;
    }

    public void consumirObjeto(Objeto ingrediente, int cantidadRequerida) {
        if (!objetos.containsKey(ingrediente)) {
            throw new IllegalArgumentException("El objeto '" + ingrediente.getNombre() + "' no se encuentra en el inventario.");
        }

        Integer cantidadActual = objetos.get(ingrediente);

        if (cantidadActual < cantidadRequerida) {
            throw new IllegalArgumentException("No hay suficiente cantidad de '" + ingrediente.getNombre() + "'. Disponible: " + cantidadActual + ", Requerido: " + cantidadRequerida);
        }

        // Calcula la nueva cantidad
        int nuevaCantidad = cantidadActual - cantidadRequerida;

        if (nuevaCantidad <= 0) {
            // Si la cantidad llega a cero o menos, se elimina el objeto del mapa
            objetos.remove(ingrediente);
        } else {
            // Si la cantidad es positiva, se actualiza en el mapa
            objetos.put(ingrediente, nuevaCantidad);
        }
    }
}
