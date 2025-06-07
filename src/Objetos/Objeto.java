package Objetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Objeto {
    private String nombre;
    //private ArrayList<Receta> recetas;

    public Objeto(String nombre) {
        this.nombre = nombre;
        //this.recetas = new ArrayList<Receta>();
    }

//    public Objeto(String nombre, ArrayList<Receta> recetas) {
//        this.nombre = nombre;
//        this.recetas = recetas;
//    }

//    public Objeto(String nombre, Receta receta) {
//        this.nombre = nombre;
//        this.recetas = new ArrayList<Receta>();
//        this.recetas.add(receta);
//    }

//    public ArrayList<Receta> getRecetas() {
//        return recetas;
//    }

    public String getNombre() {
        return nombre;
    }

//    public void agregarReceta(Receta receta){
//        recetas.add(receta);
//    }

    public abstract boolean esBasico();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objeto objeto = (Objeto) o;
        return Objects.equals(nombre, objeto.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }
}