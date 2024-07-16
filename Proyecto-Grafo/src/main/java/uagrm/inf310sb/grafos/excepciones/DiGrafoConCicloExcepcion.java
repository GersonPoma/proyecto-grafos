package uagrm.inf310sb.grafos.excepciones;

/**
 * @author GERSON
 */
public class DiGrafoConCicloExcepcion extends Exception{

    public DiGrafoConCicloExcepcion() {
        super("El grafo tiene ciclo");
    }

    public DiGrafoConCicloExcepcion(String mensaje) {
        super(mensaje);
    }
}
