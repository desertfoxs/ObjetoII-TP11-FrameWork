package olivetti.utilizacion;

import olivetti.framework.Accion;

public class AccionTres implements Accion {
    @Override
    public void ejecutar() {
        System.out.println("Ejecutando AccionTres...");

        for (int i = 0; i < 4; i++) {
            System.out.println(i + " desde tercera accion");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @Override
    public String nombreItemMenu() {
        return "Accion 3";
    }
    @Override
    public String descripcionItemMenu() {
        return "Esto trae las primeras tres personas de la BD...";
    }
}
