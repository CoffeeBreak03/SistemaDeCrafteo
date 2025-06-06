import Objetos.*;

public class Main {
    public static void main(String[] args) {
        SistemaDeCrafteo sistemaDeCrafteo= new SistemaDeCrafteo();
        String objeto = "Engranaje";
        int cantACraftear = 4;

        sistemaDeCrafteo.ingBasicosFaltantesParaCraftear(objeto, cantACraftear);
        System.out.println();
        sistemaDeCrafteo.cantidadCrafteable(objeto);
        System.out.println();
        sistemaDeCrafteo.craftearObjeto(objeto, cantACraftear);
    }
}
