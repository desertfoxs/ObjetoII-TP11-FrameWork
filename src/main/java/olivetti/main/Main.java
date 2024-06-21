package olivetti.main;

import olivetti.framework.Accion;
import olivetti.framework.Start;
import olivetti.utilizacion.AccionDos;
import olivetti.utilizacion.AccionUno;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        //List<Accion> accions = List.of(new AccionUno(), new AccionDos());

        Start start = new Start("/confg.properties");
        start.init();

    }
}
