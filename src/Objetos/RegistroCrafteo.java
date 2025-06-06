package Objetos;

public class RegistroCrafteo {
    private final Objeto objetoCrafteado;
    private final int cantCrafteada;

    public RegistroCrafteo(Objeto objetoCrafteado, int cantCrafteada) {
        this.objetoCrafteado = objetoCrafteado;
        this.cantCrafteada = cantCrafteada;
    }

    @Override
    public String toString() {
        return "objetoCrafteado: " + objetoCrafteado.getNombre() +
                ", cantCrafteada: " + cantCrafteada;
    }
}
