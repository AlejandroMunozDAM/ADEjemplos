package ad.regex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Por cada línea del log, si NO cumple el formato "mm/aa hh:mm:ss", mostrar
 * "Ignorando línea ...". Si SÍ lo cumple, mostrar un mensaje como
 * "La interfaz XXX se ha configurado con la IP YYY", excepto si la interfaz
 * ya estaba configurada, caso en el que en vez de "configurado" dirá
 * "reconfigurado".
 */
public class EjemploLog {

    public static void main(String[] args) {
        Pattern patron = Pattern.compile("\\d{2}\\/\\d{2} \\d{2}\\:\\d{2}\\:\\d{2}.+");
        LinkedList<String> interfaces = new LinkedList<>();
        try {
            for(String linea : Files.readAllLines(Paths.get("logfile.txt"))) {
                // Comprobar match
                Matcher matcher = patron.matcher(linea);
                // Si no hay match, mostrar mensaje
                // Si hay match:
                // - extraer nombre de interfaz y dirección
                // - comprobar si la interfaz apareció anteriormente
                // - mostrar mensaje
                if (matcher.matches()) {
                    String nombreInterfaz = linea.replaceAll(".+ interface ","").replaceAll("[ has] .+","");
                    String direccionInterfaz = linea.replaceAll(".+ address ","").replaceAll(", .+","");
                    if (!interfaces.contains(nombreInterfaz)) {
                        interfaces.add(nombreInterfaz);
                        System.out.println("La interfaz "+nombreInterfaz+" se ha configurado con la IP "+direccionInterfaz);
                    } else {
                        System.out.println("La interfaz "+nombreInterfaz+" se ha reconfigurado con la IP "+direccionInterfaz);
                    }
                } else {
                    System.out.println("Ignorando linea: \""+linea+"\"");
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer el fichero");
        }
    }

}
