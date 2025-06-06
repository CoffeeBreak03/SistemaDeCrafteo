package Objetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RecetaXML extends ManejadorXML<List<Receta>> {

    private Receta recetaActual;
    private ObjetoIntermedio objetoProducidoActual;
    private Map<Objeto, Integer> ingredientesActuales;
    private StringBuilder contenido;

    public RecetaXML(String rutaArchivo) {
        super(rutaArchivo);
        datos = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        contenido = new StringBuilder(); // Limpiar el contenido al inicio de cada elemento

        if (qName.equalsIgnoreCase("receta")) {
            ingredientesActuales = new HashMap<>(); // Inicializar un nuevo mapa para cada receta
            recetaActual = null; // Resetear la receta actual
            objetoProducidoActual = null; // Resetear el objeto producido
        } else if (qName.equalsIgnoreCase("objetoProducido")) {
            String nombre = attributes.getValue("nombre");
            // El objetoProducido de una receta SIEMPRE es ObjetoIntermedio
            objetoProducidoActual = new ObjetoIntermedio(nombre);
        } else if (qName.equalsIgnoreCase("ingrediente")) {
            String nombre = attributes.getValue("nombre");
            String tipo = attributes.getValue("tipo");
            int cantidad = Integer.parseInt(attributes.getValue("cantidad"));

            Objeto ingrediente;
            if ("basico".equalsIgnoreCase(tipo)) {
                ingrediente = new ObjetoBasico(nombre);
            } else if ("intermedio".equalsIgnoreCase(tipo)) {
                ingrediente = new ObjetoIntermedio(nombre);
            } else {
                throw new SAXException("Tipo de objeto desconocido para ingrediente: " + tipo);
            }
            ingredientesActuales.put(ingrediente, cantidad);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String textoElemento = contenido.toString().trim();

        if (qName.equalsIgnoreCase("cantidadProducida")) {
            int cantidad = Integer.parseInt(textoElemento);
            if(recetaActual == null) {
                recetaActual = new Receta(objetoProducidoActual, ingredientesActuales, cantidad, 0);
            } else { // Si ya tenemos una instancia parcial, la actualizamos
                recetaActual = new Receta(objetoProducidoActual, ingredientesActuales, cantidad, recetaActual.getTiempoBase());
            }
        } else if (qName.equalsIgnoreCase("tiempoBase")) {
            int tiempo = Integer.parseInt(textoElemento);
            if(recetaActual == null) {
                // Esto no debería ocurrir si el XML está bien formado y cantidadProducida viene antes de tiempoBase
                throw new SAXException("Error: tiempoBase encontrado antes que cantidadProducida.");
            } else {
                recetaActual = new Receta(objetoProducidoActual, ingredientesActuales, recetaActual.getCantidadProducida(), tiempo);
            }
        } else if (qName.equalsIgnoreCase("receta")) {
            // Cuando termina la etiqueta <receta>, la receta ya debería estar completa
            if (recetaActual != null) {
                datos.add(recetaActual);
            } else {
                throw new SAXException("Receta incompleta encontrada.");
            }
            // Resetear para la siguiente receta
            recetaActual = null;
            objetoProducidoActual = null;
            ingredientesActuales = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // Solo acumular contenido para los elementos que tienen texto interno
        // como <cantidadProducida> y <tiempoBase>
        contenido.append(ch, start, length);
    }

    @Override
    protected void generarDocumento(Document doc, List<Receta> recetas) {
        Element rootElement = doc.createElement("recetas");
        doc.appendChild(rootElement);

        for (Receta receta : recetas) {
            Element recetaElement = doc.createElement("receta");
            rootElement.appendChild(recetaElement);

            // Objeto producido
            Element objProdElement = doc.createElement("objetoProducido");
            objProdElement.setAttribute("nombre", receta.getObjetoProducido().getNombre());
            recetaElement.appendChild(objProdElement);

            // Ingredientes
            Element ingredientesElement = doc.createElement("ingredientes");
            recetaElement.appendChild(ingredientesElement);

            for (Map.Entry<Objeto, Integer> entry : receta.getIngredientes().entrySet()) {
                Element ingredienteElement = doc.createElement("ingrediente");
                Objeto obj = entry.getKey();

                ingredienteElement.setAttribute("nombre", obj.getNombre());
                ingredienteElement.setAttribute("tipo", obj instanceof ObjetoBasico ? "basico" : "intermedio");
                ingredienteElement.setAttribute("cantidad", entry.getValue().toString());

                ingredientesElement.appendChild(ingredienteElement);
            }

            // Cantidad producida
            Element cantElement = doc.createElement("cantidadProducida");
            cantElement.appendChild(doc.createTextNode(String.valueOf(receta.getCantidadProducida())));
            recetaElement.appendChild(cantElement);

            // Tiempo base
            Element tiempoElement = doc.createElement("tiempoBase");
            tiempoElement.appendChild(doc.createTextNode(String.valueOf(receta.getTiempoBase())));
            recetaElement.appendChild(tiempoElement);
        }
    }
}