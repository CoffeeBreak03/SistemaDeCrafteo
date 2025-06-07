package Objetos;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "objeto")
public class ObjetoXML {
    @XmlAttribute
    public String nombre;

    @XmlAttribute
    public int cantidad;

    public ObjetoXML(){}

    public ObjetoXML(String nombre, int cantidad){
        this.nombre = nombre;
        this.cantidad = cantidad;
    }
}
