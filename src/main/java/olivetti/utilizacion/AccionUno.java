package olivetti.utilizacion;

import olivetti.framework.Accion;

public class AccionUno implements Accion {
    @Override
    public void ejecutar() {
        System.out.println("Ejecutando AccionUno...");

        for (int i = 0; i < 4; i++) {
            System.out.println(i + " desde primera accion");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @Override
    public String nombreItemMenu() {
        return "Accion 1";
    }
    @Override
    public String descripcionItemMenu() {
        return "Esto es para traer los twitts de Maradona...";
    }
}
