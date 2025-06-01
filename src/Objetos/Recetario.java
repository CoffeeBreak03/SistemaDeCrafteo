package Objetos;

import java.util.ArrayList;
import java.util.List;

public class Recetario {
    private List<Receta> recetas;
    public Recetario() {
        recetas = new ArrayList<>();
    }

    public Recetario(List<Receta> recetas) {
        this.recetas = recetas;
    }

    public void cargarRecetario() throws Exception{/*carga desde archivo recetas.xml*/}

    public void agregarReceta(Receta receta){
        recetas.add(receta);
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public List<Receta> buscarRecetas(String objeto){
        List<Receta> recetasObj;

        recetasObj = recetas.stream().filter(r -> r.getObjetoProducido().equals(objeto)).toList();

        return recetasObj;
    }
}
