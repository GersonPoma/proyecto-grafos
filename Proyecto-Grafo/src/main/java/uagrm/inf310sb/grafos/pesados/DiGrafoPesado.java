package uagrm.inf310sb.grafos.pesados;

import uagrm.inf310sb.grafos.excepciones.AristaNoExisteExcepcion;
import uagrm.inf310sb.grafos.excepciones.AristaYaExisteExcepcion;

import java.util.Collections;
import java.util.List;

/**
 * grafo dirigido con peso
 * @author GERSON
 */
public class DiGrafoPesado extends GrafoPesado{

    public DiGrafoPesado() {
        super();
    }

    public DiGrafoPesado(int nroVertices) {
        super(nroVertices);
    }

    @Override
    public void insertarAristas(int posDeVerticeOrigen, int posDeVerticeDestino,
                                double peso) throws AristaYaExisteExcepcion {
        if (existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino))
            throw new AristaYaExisteExcepcion();

        List<AdyacenteConPeso> adyacentesDelOrigen =
                super.listasDeAdyacencias.get(posDeVerticeOrigen);
        adyacentesDelOrigen.add(new AdyacenteConPeso(posDeVerticeDestino, peso));
        Collections.sort(adyacentesDelOrigen);
    }

    @Override
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino)
            throws AristaNoExisteExcepcion {
        if (!super.existeAdyacencia(posVerticeOrigen, posVerticeDestino))
            throw new AristaNoExisteExcepcion();

        List<AdyacenteConPeso> adyacentesDelOrigen =
                this.listasDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso adyacenteDestino = new AdyacenteConPeso(posVerticeDestino);
        int posDelVerticeDestinoEnLaAdy = adyacentesDelOrigen.indexOf(adyacenteDestino);
        adyacentesDelOrigen.remove(posDelVerticeDestinoEnLaAdy);
    }

    @Override
    public int cantidadDeAristas() {
        int cantidad = 0;

        for (List<AdyacenteConPeso> adyacentesDeUnVertice : super.listasDeAdyacencias) {
            cantidad += adyacentesDeUnVertice.size();
        }

        return cantidad;
    }

    @Override
    public int gradoDeVertice(int posDeVertice) {
        throw new RuntimeException("Metodo no soportado para este grafo");
    }

    public int gradoDeEntradaDelVertice(int posDeVertice) {
        super.validarVertice(posDeVertice);
        int cantidad = 0;
        AdyacenteConPeso vertice = new AdyacenteConPeso(posDeVertice);

        for (List<AdyacenteConPeso> adyacentesDeUnVertice : super.listasDeAdyacencias) {
            for (AdyacenteConPeso posDeAdyacente : adyacentesDeUnVertice) {
                if (posDeAdyacente.compareTo(vertice) == 0)
                    cantidad++;
            }
        }

        return cantidad;
    }

    public int gradoDeSalidaDelVertice(int posDeVertice) {
        return super.gradoDeVertice(posDeVertice);
    }
}
