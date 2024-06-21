package olivetti.framework;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public final class Menu{

    Scanner entrada = new Scanner(System.in);
    private List<Accion> acciones;
    public Menu(String path) {
        acciones = new ArrayList<>();

        Properties prop = new Properties();
        try (InputStream configFile = getClass().getResourceAsStream(path);) {
            prop.load(configFile);

            for (String key : prop.stringPropertyNames()) {
                String accionName1 = prop.getProperty(key);
                Class<?> c1 = Class.forName(accionName1);
                Accion accion = (Accion) c1.getDeclaredConstructor().newInstance();
                acciones.add(accion);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "No pude crear la instancia de TextoAImprimir... ", e);
        }
    }

    public final void print(){
        String texto = "";
        System.out.println("Bienvenido, estas son sus opciones:");

        while(!(texto.equals("Salir"))){
            armarMenu();
            texto = entrada.nextLine();
            for(Accion accion : acciones){
                if(accion.nombreItemMenu().equals(texto)){
                    accion.ejecutar();
                }
            }
        }
    }


    private void armarMenu(){
        int indice = 1;
        for(Accion accion : acciones){
            System.out.println( indice + ". " + accion.nombreItemMenu() + " (" + accion.descripcionItemMenu() + " )");
            indice++;
        }
        System.out.println( indice + ". Salir");
        System.out.println("Ingrese su opción: ");
    }
}
