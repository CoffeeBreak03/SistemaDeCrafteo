import Objetos.Objeto;
import Objetos.PosibleReceta;
import Objetos.Receta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main
{
    public static void main(String[] args) {
        //Casa objeto intermedio
        //ingredientes 1 antorcha intermedio, 4 paredes intermedio, 1 techo intermedio, 3 alfombra basico    
        Objeto madera = new Objeto("Madera");
        Objeto carbonMin = new Objeto("Carbón Mineral");
        Map<Objeto, Integer> ingredientesAntorcha = new HashMap<>();
        ingredientesAntorcha.put(madera, 3);
        ingredientesAntorcha.put(carbonMin, 5);
        Receta recAntorchaMin = new Receta(ingredientesAntorcha, 1, 1, "Antorcha");
        Objeto antorcha = new Objeto("Antorcha", recAntorchaMin);

        Objeto carbonNat = new Objeto("Carbón Natural");
        Objeto resina = new Objeto("Resina");
        Map<Objeto, Integer> ingredientesAntorchaNat = new HashMap<>();
        ingredientesAntorchaNat.put(madera, 3);
        ingredientesAntorchaNat.put(carbonNat, 3);
        ingredientesAntorchaNat.put(resina, 2);
        Receta recAntorchaNat = new Receta(ingredientesAntorchaNat, 1, 1, "Antorcha");
        antorcha.agregarReceta(recAntorchaNat);

        Objeto arena = new Objeto("Arena");
        Objeto calcita = new Objeto("Calcita");
        Objeto agua = new Objeto("Agua");
        Map<Objeto, Integer> ingredientesMezcla = new HashMap<>();
        ingredientesMezcla.put(arena, 3);
        ingredientesMezcla.put(calcita, 1);
        ingredientesMezcla.put(agua, 1);
        Receta recCemento = new Receta(ingredientesMezcla, 1, 1, "Cemento");
        Objeto cemento = new Objeto("Cemento", recCemento);

        Objeto premezcla = new Objeto("Premezcla");
        Map<Objeto, Integer> ingredientesPreMezcla = new HashMap<>();
        ingredientesPreMezcla.put(premezcla, 1);
        ingredientesPreMezcla.put(agua, 1);
        Receta recCementoPreMez = new Receta(ingredientesPreMezcla, 1, 1, "Cemento");
        cemento.agregarReceta(recCementoPreMez);

        Objeto ladrillo = new Objeto("Ladrillo");
        Map<Objeto, Integer> ingredientesPared = new HashMap<>();
        ingredientesPared.put(cemento, 5);
        ingredientesPared.put(ladrillo, 100);
        Receta recPared = new Receta(ingredientesPared, 1, 1, "Pared");
        Objeto pared = new Objeto("Pared", recPared);

        Objeto hierro = new Objeto("Hierro");
        Map<Objeto, Integer> ingredientesTecho = new HashMap<>();
        ingredientesTecho.put(hierro, 2);
        ingredientesTecho.put(cemento, 1);
        Receta recTecho = new Receta(ingredientesTecho, 1, 1, "Techo");
        Objeto techo = new Objeto("Techo", recTecho);

        Objeto alfombra = new Objeto("Alfombra");

        Map<Objeto, Integer> ingredientesCasa = new HashMap<>();
        ingredientesCasa.put(antorcha, 1);
        ingredientesCasa.put(pared, 4);
        ingredientesCasa.put(techo, 1);
        ingredientesCasa.put(alfombra, 3);
        Receta recCasa = new Receta(ingredientesCasa,1 , 1, "Casa");
        Objeto casa = new Objeto("Casa",recCasa);

        System.out.println("Ingredientes de [" + casa.getNombre() + "] :");
        ArrayList<PosibleReceta> resultado = casa.getRecetas().getFirst().getIngredientesBasicos();

        for (int i = 0; i < resultado.size(); i++) {
            System.out.println(resultado.get(i));
            if (i < resultado.size() - 1) {
                System.out.println("\nO\n"); // separador entre recetas
            }
        }

    }
}
