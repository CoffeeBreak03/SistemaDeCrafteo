package Objetos;

public class RegistroCrafteo {
    private Objeto objetoCrafteado;
    private int cantCrafteada;

    public RegistroCrafteo(Objeto objetoCrafteado, int cantCrafteada) {
        this.objetoCrafteado = objetoCrafteado;
        this.cantCrafteada = cantCrafteada;
    }

    @Override
    public String toString() {
        return "objetoCrafteado= " + objetoCrafteado.getNombre() +
                ", cantCrafteada= " + cantCrafteada;
    }
}
