package Objetos;

public class PosibleFaltante {
    public PosibleReceta faltantes;
    public Inventario estadoDeInv;
    public HistorialDeCrafteo historialActual;

    //clase auxiliar porque Map queda mal, no se me ocurrio otra manera de obtener el estado del inventario al detectar la minima
    //lista de faltantes dado un objeto con varias recetas. (el estado podia ser distinto si se elegia otra receta)
    public PosibleFaltante(PosibleReceta faltantes, Inventario estadoDeInv) {
        this.faltantes = faltantes;
        this.estadoDeInv = estadoDeInv;
        historialActual = new HistorialDeCrafteo();
    }

    @Override
    public String toString() {
        return faltantes.toString().replace("requeridas", "faltantes");
    }
}
