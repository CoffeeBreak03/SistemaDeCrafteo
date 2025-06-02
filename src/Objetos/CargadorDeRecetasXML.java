package Objetos;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class CargadorDeRecetasXML extends DefaultHandler {
    private final String rutaArchivo;
    private Recetario recetario;

    private Receta recetaActual;
    private Map<Objeto, Integer> ingredientesActuales;
    private StringBuilder contenido;

    public CargadorDeRecetasXML(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.recetario = new Recetario();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("receta")) {
            recetaActual = new Receta();
        } else if (qName.equalsIgnoreCase("ingredientes")){
            ingredientesActuales = new HashMap<>();
        } else if (qName.equalsIgnoreCase("ingrediente")){
            String nombre = attributes.getValue("nombre");
            int cantidad = Integer.parseInt(attributes.getValue("cantidad"));

            //Una posible optimizacion tal vez seria mantener registro de los objetos que se van creando y ,no estar duplicandolos cada vez que
            //una receta lo usa como ingrediente, sino guardar la direccion de ese objeto en ingredientesActuales
            Objeto ingredienteActual = new Objeto(nombre, recetario.buscarRecetas(nombre));
            ingredientesActuales.put(ingredienteActual, cantidad);
        }

        contenido = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("objetoProducido")) {
            String objProducido = contenido.toString().trim();
            recetaActual.setObjetoProducido(objProducido);

        } else if (qName.equalsIgnoreCase("cantidadProducida")) {
            int cantidadProd = Integer.parseInt(contenido.toString().trim());
            recetaActual.setCantidadProducida(cantidadProd);

        } else if (qName.equalsIgnoreCase("tiempoBase")) {
            int tiempo = Integer.parseInt(contenido.toString().trim());
            recetaActual.setTiempoBase(tiempo);

        } else if(qName.equalsIgnoreCase("mesaRequerida")){
            //misma posible optimizacion que antes
            String mesaRequerida = contenido.toString().trim();
            if(!mesaRequerida.equals("Ninguna")){
                Objeto mesa = new Objeto(mesaRequerida, recetario.buscarRecetas(mesaRequerida));
                recetaActual.setMesaRequerida(mesa);
            }

        } else if(qName.equalsIgnoreCase("ingredientes")){
            recetaActual.setIngredientes(ingredientesActuales);

        } else if (qName.equalsIgnoreCase("receta")) {
            recetario.agregarReceta(recetaActual);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        contenido.append(ch, start, length);
    }

    public Recetario cargar(){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File(rutaArchivo), this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return recetario;
    }
}
