package ad.regex.ejemploArchivos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Archivos {
    public static void main(String[] args) {
        Path ruta = Paths.get(new Scanner(System.in).next());
        String nombreBuscado = new Scanner(System.in).next();

        try {
            Files.walk(ruta).forEach(archivo->{
                if (archivo.getFileName().equals(nombreBuscado)) {

                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
