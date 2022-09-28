package ad.regex.ejemploAjedrez;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ajedrez {
    public static void main(String[] args) {
        Pattern patronMovimientos = Pattern.compile("([a-h]\\d)(x)?([a-h]\\d)(\\++)?");
        Path ficheroPartida = Paths.get("src\\ad\\regex\\ejemploAjedrez\\partida.txt");
        int nJugada = 1;
        if (Files.exists(ficheroPartida)) {
            try {
                for (String jugada : Files.readAllLines(ficheroPartida)) {
                    Matcher matcherJugada = patronMovimientos.matcher(jugada.trim());
                    if (matcherJugada.matches()) {
                        String posicionInicial = matcherJugada.group(1);
                        String capturando = matcherJugada.group(2);
                        if (capturando == null) {
                            capturando = " se mueve a ";
                        } else {
                            capturando = " captura ";
                        }
                        String poscionFinal = matcherJugada.group(3);
                        String jaque = matcherJugada.group(4);
                        if (jaque == null) {
                            jaque = "";
                        } else if (jaque.equals("++")) {
                            jaque = " y hace jaque mate";
                        } else if (matcherJugada.group(4).equals("+")) {
                            jaque = " y hace jaque";
                        } else {
                            jaque = "";
                        }

                        System.out.printf("Jugada " + posicionInicial.toUpperCase() + ". La pieza en la casilla " + capturando + posicionInicial + " a la pieza de la casilla "
                                + poscionFinal.toUpperCase() + jaque + "\n"
                        );
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("ERROR: No existe el fichero especificado");
        }
    }
}
