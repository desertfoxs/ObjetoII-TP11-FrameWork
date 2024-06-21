package olivetti.main;

import olivetti.framework.Accion;
import olivetti.framework.Start;
import olivetti.utilizacion.AccionDos;
import olivetti.utilizacion.AccionUno;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        //Start start = new Start("/confg.properties");
        Start start = new Start("C:\\Users\\desertfoxs\\eclipse-workspace2\\" +
                "Tp11-FrameWork\\src\\main\\resources\\confg.json");

        start.init();

    }
}
