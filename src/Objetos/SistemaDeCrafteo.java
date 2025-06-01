package Objetos;

public class SistemaDeCrafteo {
    private HistorialDeCrafteo historialDeCrafteo;
    private Inventario inventario;
    private Recetario recetario;

    public SistemaDeCrafteo(){
        historialDeCrafteo = new HistorialDeCrafteo();
        inventario = new Inventario();
        recetario = new Recetario();
    }


}
