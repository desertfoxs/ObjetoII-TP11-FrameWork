package olivetti.framework;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
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
        if(path.endsWith(".properties")){
            cargarAccionesProperties(path);
        }
        else{
            cargarAccionesJson(path);
        }
    }

    private void cargarAccionesProperties(String path){

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

    private void cargarAccionesJson(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(new File(path));
            JsonNode accionesNode = root.path("acciones");

            for (JsonNode accionNode : accionesNode) {
                String accionClassName = accionNode.asText();
                try {
                    Class<?> accionClass = Class.forName(accionClassName);
                    Accion accion = (Accion) accionClass.getDeclaredConstructor().newInstance();
                    acciones.add(accion);
                } catch (Exception e) {
                    System.out.println("Error cargando la clase: " + accionClassName);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo de configuración: " + path);
            e.printStackTrace();
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
