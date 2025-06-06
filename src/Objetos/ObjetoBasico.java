package Objetos;

public class ObjetoBasico extends Objeto {
    public ObjetoBasico(String nombre) {
        super(nombre);
    }

    @Override
    public boolean esBasico() {
        return true;
    }

    @Override
    public String toString() {
        return "ObjetoBásico: " + getNombre();
    }
}
