package uagrm.inf310sb.grafos.excepciones;

/**
 *
 * @author GERSON
 */
public class AristaNoExisteExcepcion extends Exception {

    public AristaNoExisteExcepcion() {
        super("Arista no existe");
    }

    public AristaNoExisteExcepcion(String message) {
        super(message);
    }
   
}
