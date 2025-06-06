package Objetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventario {
    private HashMap<Objeto, Integer> objetos;

    public Inventario(){
        objetos = new HashMap<>();
    }

    public Inventario(HashMap<Objeto, Integer> objetos){
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

//No se que carajo quise hacer con esto pero lo dejo porque algo de la logica es medio util asjda

//    public ArrayList<PosibleInventario> descomponerInvEnBasicos(){
//        ArrayList<PosibleInventario> posibleInvFinal = new ArrayList<>();
//        PosibleReceta basicos = new PosibleReceta();
//
//        for(Map.Entry<Objeto, Integer> elem : objetos.entrySet()){
//            Objeto item = elem.getKey();
//            int cant = elem.getValue();
//
//            if(item.esBasico()){
//                basicos.getIngredientes().put(item, cant);
//            } else {
//
//                ArrayList<PosibleInventario> posibleInvActual = new ArrayList<>();
//                for(Receta rec: item.getRecetas()){
//                    posibleInvActual.add(new PosibleInventario(rec.getIngredientesBasicos()));
//                }
//
//                for(PosibleInventario posiblesBasicos : posibleInvActual){
//                    posiblesBasicos.multiplicarCantidades(cant);
//                }
//
//                posibleInvFinal = combinarInventarios(posibleInvActual, posibleInvFinal);
//            }
//        }
//
//        if(posibleInvFinal.isEmpty()){
//            ArrayList<PosibleReceta> contenedor = new ArrayList<>();
//            contenedor.add(basicos);
//            posibleInvFinal.add(new PosibleInventario(contenedor));
//        }else{
//            for (PosibleInventario posiblesBasicosExistentes : posibleInvFinal) {
//                basicos.combinarConListaPosReceta(posiblesBasicosExistentes.getPosiblesBasicos());
//            }
//        }
//
//        return posibleInvFinal;
//    }

//    private static ArrayList<PosibleInventario> combinarInventarios(ArrayList<PosibleInventario> posibleInvActual, ArrayList<PosibleInventario> posibleInvFinal) {
//        ArrayList<PosibleInventario> resultado = new ArrayList<>();
//
//        if(posibleInvFinal.isEmpty()) {
//            resultado.addAll(posibleInvActual);
//        }
//        else {
//            for (PosibleInventario posiblesBasicos : posibleInvActual) {
//                for (PosibleInventario posiblesBasicosExistentes : posibleInvFinal) {
//                    resultado.add(new PosibleInventario(
//                            Util.combinarListas(posiblesBasicosExistentes.getPosiblesBasicos(), posiblesBasicos.getPosiblesBasicos())
//                    ));
//                }
//            }
//        }
//
//        return resultado;
//    }

    public Inventario crearCopia(){
        Inventario copia = new Inventario();

        for(Map.Entry<Objeto,Integer> elem : objetos.entrySet()){
            Objeto item = elem.getKey();
            int cant = elem.getValue();

            copia.objetos.put(item, cant);
        }
        return copia;
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
