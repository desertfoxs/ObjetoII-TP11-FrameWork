package olivetti.framework;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Menu{

    public static final String MAX_THREADS = "max-threads";
    private static final String properties = ".properties";;
    public static final String CLAVE_ACCIONES = "acciones";
    private ExecutorService executor;
    Scanner entrada = new Scanner(System.in);
    private List<Accion> acciones;

    private int cantHilos = 1;

    public Menu(String path) {
        acciones = new ArrayList<>();
        if(path.endsWith(properties)){
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
                if(!key.equals(MAX_THREADS)){
                    String accionName1 = prop.getProperty(key);
                    Class<?> c1 = Class.forName(accionName1);
                    Accion accion = (Accion) c1.getDeclaredConstructor().newInstance();
                    acciones.add(accion);
                }
                else{
                    cantHilos = Integer.parseInt(prop.getProperty(key));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("No pude crear la instancia de Acciones... ", e);
        }
    }

    private void cargarAccionesJson(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(new File(path));
            JsonNode accionesNode = root.path(CLAVE_ACCIONES);

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

            JsonNode maxHilos = root.path(MAX_THREADS);
            if(maxHilos.isEmpty()){
                cantHilos = maxHilos.asInt();
            }

        } catch (IOException e) {
            System.out.println("Error leyendo el archivo de configuración: " + path);
            e.printStackTrace();
        }
    }

    public final void print(){
        this.executor = Executors.newFixedThreadPool(cantHilos);

        List<Callable<Void>> listAdapter = new ArrayList<>();

        String texto = "";
        System.out.println("Bienvenido, estas son sus opciones:");
        System.out.println("La cantidad de hilos es: " + cantHilos);

        while(!(texto.equals("Salir"))){
            armarMenu();
            texto = entrada.nextLine();

            if(texto.equals("Ejecutar")){
                try {
                    executor.invokeAll(listAdapter);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                listAdapter.clear();
            }

            for(Accion accion : acciones){
                if(accion.nombreItemMenu().equals(texto)){
                    //accion.ejecutar();
                    listAdapter.add(new AdapterAccion(accion));
                }
            }
        }
        executor.shutdown();
    }


    private void armarMenu(){
        int indice = 1;
        for(Accion accion : acciones){
            System.out.println( indice + ". " + accion.nombreItemMenu() + " (" + accion.descripcionItemMenu() + " )");
            indice++;
        }
        System.out.println( indice + ". Ejecutar");
        System.out.println( (indice+1)  + ". Salir");
        System.out.println("Ingrese su opción: ");
    }
}
