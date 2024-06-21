package olivetti.framework;

import java.util.concurrent.Callable;

public class AdapterAccion implements Callable<Void> {

    private Accion accion;
    public AdapterAccion(Accion accion) {
        this.accion = accion;
    }

    @Override
    public Void call()throws Exception{
        accion.ejecutar();
        return null;
    }

}
