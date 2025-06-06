package Objetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Util {

    //Este metodo realiza, dadas dos lista/conjuntos M y N: M x N (como en mate. discreta)
    public static ArrayList<PosibleReceta> combinarListas(ArrayList<PosibleReceta> m, ArrayList<PosibleReceta> n){
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

    public static int compararPosRecetas(PosibleReceta faltantes, PosibleReceta menorFaltante){
        double ratio1 = (double) faltantes.getIngredientes().values().stream().reduce(0, Integer::sum) / faltantes.getCantProducida();
        double ratio2 = (double) menorFaltante.getIngredientes().values().stream().reduce(0, Integer::sum) / menorFaltante.getCantProducida();

        //TODO: TALVEZ INCLUIR EL TIEMPO EN LA FORMULA MULTIPLICANDO POR EL RATIO DE PRODUCCION
        int comp = Double.compare(ratio1, ratio2);
        return comp != 0 ? comp : faltantes.getTiempoCrafteo() - menorFaltante.getTiempoCrafteo();
    }
}
