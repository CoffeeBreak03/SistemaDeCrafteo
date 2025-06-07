package Objetos;

import java.util.ArrayList;
import java.util.List;

public class HistorialDeCrafteo {
    //Decidi usar List porque guardo el orden simplemente, con un map se le puede asignar fecha a cada registro,
    //o ponerlo como atributo del registro
    private List<RegistroCrafteo> registros;

    public HistorialDeCrafteo() {
        registros = new ArrayList<>();
    }

    public void agregarRegistro(Objeto objeto, int cantCrafteada){
        registros.addLast(new RegistroCrafteo(objeto, cantCrafteada));
    }

    public List<RegistroCrafteo> getRegistros() {
        return registros;
    }

    public RegistroCrafteo getRegistroIndex(int indice){
        return registros.get(indice);
    }

    public RegistroCrafteo getUltimoRegistro(){
        return registros.getLast();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        int i = 1;

        for(RegistroCrafteo reg : registros){
            string.append(i).append(" - ").append(reg.toString()).append("\n");
            i++;
        }

        return string.toString();
    }
}
