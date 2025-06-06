package Objetos;

import java.util.ArrayList;
import java.util.List;

public class Recetario {
    private List<Receta> recetas;
    public Recetario() {
        recetas = new RecetaXML("archivos/recetas2.xml").cargar();
    }

    public Recetario(List<Receta> recetas) {
        this.recetas = recetas;
    }

    public void agregarReceta(Receta receta){
        recetas.add(receta);
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public ArrayList<Receta> buscarRecetas(String objeto){
        return new ArrayList<>(recetas.stream().filter(r -> r.getObjetoProducido().equals(objeto)).toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("=== RECETARIO ===\n");
        for (Receta receta : recetas) {
            sb.append(receta)  // Usa el toString() de Receta
                    .append("\n");
        }
        return sb.toString();
    }
}
