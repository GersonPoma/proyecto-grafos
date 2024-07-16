package uagrm.inf310sb.grafos.excepciones;

/**
 *
 * @author GERSON
 */
public class AristaYaExisteExcepcion extends Exception {

    public AristaYaExisteExcepcion() {
        super("Arista que quierse insertar ya existe");
    }

    public AristaYaExisteExcepcion(String message) {
        super(message);
    }
    
}
