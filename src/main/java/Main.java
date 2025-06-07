import Objetos.*;

import jakarta.xml.bind.JAXBException;

public class Main {
    public static void main(String[] args) {
        SistemaDeCrafteo sistemaDeCrafteo= new SistemaDeCrafteo();
        String objeto = "Engranaje";
        int cantACraftear = 9;

        sistemaDeCrafteo.ingBasicosFaltantesParaCraftear(objeto, cantACraftear);
        System.out.println();
        sistemaDeCrafteo.cantidadCrafteable(objeto);
        System.out.println();
        sistemaDeCrafteo.craftearObjeto(objeto, cantACraftear);
        sistemaDeCrafteo.craftearObjeto("Maquina", 1);

        try{
            sistemaDeCrafteo.exportarInventarioEnXML();
        }catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
