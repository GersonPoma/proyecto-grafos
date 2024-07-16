package uagrm.inf310sb.grafos.excepciones;

/**
 * @author GERSON
 */
public class NoHayCaminoExcepcion extends Exception{

    public NoHayCaminoExcepcion() {
        super("No hay camino para llegar al destino");
    }

    public NoHayCaminoExcepcion(String mensaje) {
        super(mensaje);
    }
}
