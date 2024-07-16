package uagrm.inf310sb.grafos.excepciones;

/**
 *
 * @author GERSON
 */
public class GrafoNoConexoExcepcion extends Exception {
    public GrafoNoConexoExcepcion() {
        super("El grafo no es conexo");
    }
    
    public GrafoNoConexoExcepcion(String mensaje) {
        super(mensaje);
    }
}
