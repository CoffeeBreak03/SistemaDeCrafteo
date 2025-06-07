package Objetos;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement(name = "inventario")
public class Inventario {
    private HashMap<Objeto, Integer> objetos;

    public Inventario(){
        objetos = new HashMap<>();
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

    public Inventario crearCopia(){
        Inventario copia = new Inventario();

        for(Map.Entry<Objeto,Integer> elem : objetos.entrySet()){
            Objeto item = elem.getKey();
            int cant = elem.getValue();

            copia.objetos.put(item, cant);
        }
        return copia;
    }

    @XmlElement(name = "objeto")
    public List<ObjetoXML> getObjetosXML(){
        List<ObjetoXML> lista = new ArrayList<>();

        for(Map.Entry<Objeto, Integer> elem : objetos.entrySet()){
            lista.add(new ObjetoXML(elem.getKey().getNombre(), elem.getValue()));
        }

        return lista;
    }

    public HashMap<Objeto, Integer> getObjetos() {
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
