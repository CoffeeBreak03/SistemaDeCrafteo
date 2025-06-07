package Objetos;

import java.util.ArrayList;
import java.util.Objects;

public class Objeto {
    private String nombre;
    private ArrayList<Receta> recetas;


    public Objeto(String nombre, ArrayList<Receta> recetas) {
        this.nombre = nombre;
        this.recetas = recetas;
    }

    public ArrayList<Receta> getRecetas() {
        return recetas;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean esBasico(){
        return recetas.isEmpty();
    }

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