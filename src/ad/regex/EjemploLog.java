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
        Pattern patronLinea = Pattern.compile("\\d{2}\\/\\d{2} \\d{2}\\:\\d{2}\\:\\d{2}.+");
        Pattern patronNombreDireccion = Pattern.compile(".+interface (\\w+) .+ address (\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}),.+");
//        Pattern patronDireccion = Pattern.compile(".+address (\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}),.+");

        LinkedList<String> interfaces = new LinkedList<>();
        try {
            for(String linea : Files.readAllLines(Paths.get("logfile.txt"))) {
                // Comprobar match
                Matcher matcher = patronLinea.matcher(linea);
                if (matcher.matches()) {
                    // Si hay match:
                    // - extraer nombre de interfaz y dirección
                    Matcher comprobadorNombreDireccion = patronNombreDireccion.matcher(linea);

                    comprobadorNombreDireccion.matches();

                    String nombreInterfaz = comprobadorNombreDireccion.group(1);
                    String direccionInterfaz = comprobadorNombreDireccion.group(2);

                    // - comprobar si la interfaz apareció anteriormente
                    // - mostrar mensaje
                    if (!interfaces.contains(nombreInterfaz)) {
                        interfaces.add(comprobadorNombreDireccion.group(1));
                        System.out.println("La interfaz "+nombreInterfaz+" se ha configurado con la IP "+direccionInterfaz);
                    } else {
                        System.out.println("La interfaz "+nombreInterfaz+" se ha reconfigurado con la IP "+direccionInterfaz);
                    }
                    // Si no hay match, mostrar mensaje
                } else {
                    System.out.println("Ignorando linea: \""+linea+"\"");
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer el fichero");
        }
    }

}
