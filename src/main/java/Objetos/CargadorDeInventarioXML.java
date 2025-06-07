package Objetos;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class CargadorDeInventarioXML extends DefaultHandler {
    private final String rutaArchivo;
    private Inventario inventario;
    private Recetario recetario;


    public CargadorDeInventarioXML(String rutaArchivo, Recetario recetario) {
        this.rutaArchivo = rutaArchivo;
        this.recetario = recetario;
        this.inventario = new Inventario();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("objeto")) {
            String nombre = attributes.getValue("nombre");
            int cantidad = Integer.parseInt(attributes.getValue("cantidad"));

            Objeto ingredienteActual = new Objeto(nombre, recetario.buscarRecetas(nombre));
            inventario.agregarObjeto(ingredienteActual, cantidad);
        }
    }

    public Inventario cargar(){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File(rutaArchivo), this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return inventario;
    }
}
