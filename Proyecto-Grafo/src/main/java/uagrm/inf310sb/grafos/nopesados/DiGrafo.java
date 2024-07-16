package uagrm.inf310sb.grafos.nopesados;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import uagrm.inf310sb.grafos.excepciones.AristaNoExisteExcepcion;
import uagrm.inf310sb.grafos.excepciones.AristaYaExisteExcepcion;
import uagrm.inf310sb.grafos.excepciones.DiGrafoConCicloExcepcion;
import uagrm.inf310sb.grafos.excepciones.GrafoNoConexoExcepcion;


/**
 * Grafo dirigido
 * @author GERSON
 */
public class DiGrafo extends Grafo {
    public DiGrafo() {
        super();
    }
    
    public DiGrafo(int nroDeVertices) {
        super(nroDeVertices);
    }

    @Override
    public void insertarAristas(int posDeVerticeOrigen,
                                int posDeVerticeDestino) throws AristaYaExisteExcepcion {
        if (super.existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino))
            throw new AristaYaExisteExcepcion();

        List<Integer> adyacentesDelOrigen =
                super.listasDeAdyacencias.get(posDeVerticeOrigen);
        adyacentesDelOrigen.add(posDeVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
    }

    @Override
    public void eliminarArista(int posVerticeOrigen,
                               int posVerticeDestino) throws AristaNoExisteExcepcion {
        if (!super.existeAdyacencia(posVerticeOrigen, posVerticeDestino))
            throw new AristaNoExisteExcepcion();

        List<Integer> adyacentesDelOrigen = super.listasDeAdyacencias.get(posVerticeOrigen);
        int posDeVerticeDestinoEnLaAdy = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posDeVerticeDestinoEnLaAdy);
    }

    @Override
    public int gradoDeVertice(int posDeVertice) {
        throw new RuntimeException("Metodo no soportado para este grafo");
    }

    public int gradoDeEntradaDeVertice(int posDeVertice) {
        super.validarVertice(posDeVertice);
        int entradasDeVertice = 0;
        for (List<Integer> adyacentesDeUnVertice : super.listasDeAdyacencias) {
            for (Integer posDeAyacente : adyacentesDeUnVertice) {
                if (posDeAyacente == posDeVertice)
                    entradasDeVertice++;
            }
        }

        return entradasDeVertice;
    }

    public int gradoDeSalidaDeVertice(int posDeVertice) {
        return super.gradoDeVertice(posDeVertice);
    }

    @Override
    public int cantidadDeAristas() {
        int cantAristas = 0;
        for (int i = 0; i < super.cantidadDeVertices(); i++) {
            List<Integer> adyacentesDelVerticeAct = super.listasDeAdyacencias.get(i);
            cantAristas += adyacentesDelVerticeAct.size();
        }

        return cantAristas;
    }

    public List<Integer> ordenTopologico() throws DiGrafoConCicloExcepcion, GrafoNoConexoExcepcion {
        Grafo grafo = convertirAGrafo(this);
        
        if (!new Conexo(grafo).esConexo())
            throw new GrafoNoConexoExcepcion();
        
        if (new CicloDiGrafo(this).hayCiclo())
            throw new DiGrafoConCicloExcepcion();

        int[] listaGradosDeEntradas = new int[this.cantidadDeVertices()];
        Queue<Integer> cola = new LinkedList<>();
        List<Integer> orden = new LinkedList<>();

        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            int gradoEntrada = this.gradoDeEntradaDeVertice(i);
            listaGradosDeEntradas[i] = gradoEntrada;

            if (gradoEntrada == 0)
                cola.offer(i);
        }

        do {
            int vertice = cola.poll();
            orden.add(vertice);

            for (Integer adyacentesDelVertice : this.adyacentesDelVertice(vertice)) {
                listaGradosDeEntradas[adyacentesDelVertice] -= 1;

                if (listaGradosDeEntradas[adyacentesDelVertice] == 0)
                    cola.offer(adyacentesDelVertice);
            }
        } while (!cola.isEmpty());

        return orden;
    }

    public static String cantidadDeIslas(DiGrafo ungrafo) {
        Grafo grafo = convertirAGrafo(ungrafo);
        List<List<Integer>> islas = new LinkedList<>();
        int cantidad = 0;
        ControlMarcado visitados = new ControlMarcado(grafo.cantidadDeVertices());

        for (int i = 0; i < grafo.cantidadDeVertices(); i++) {
            if (!visitados.estaMarcado(i)) {
                DFS dfs = new DFS(grafo, i);
                List<Integer> recorrido = (List<Integer>)dfs.getRecorrido();

                for (int vertice : recorrido) {
                    visitados.marcarVertice(vertice);
                }

                islas.add(recorrido);
                cantidad++;
            }
        }

        return aString(cantidad, islas);
    }

    private static Grafo convertirAGrafo(DiGrafo unGrafo) {
        Grafo grafo = new Grafo(unGrafo.cantidadDeVertices());

        for (int vertice = 0; vertice < unGrafo.cantidadDeVertices();
             vertice++) {
            for (int adyacente : unGrafo.adyacentesDelVertice(vertice)) {
                // existeAdyacencia(vertice, adyacente)
                if (!grafo.existeAdyacencia(adyacente, vertice)) {
                    try {
                        //grafo.insertarAristas(adyacente, vertice);
                        grafo.insertarAristas(vertice, adyacente);
                    } catch (AristaYaExisteExcepcion e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return grafo;
    }

    private static String aString(int cantidad, List<List<Integer>> islas) {
        String mostrar = "Cantidad de islas: " + cantidad + "\n";

        for (int i = 0; i < islas.size(); i++) {
            mostrar = mostrar + "Isla " + (i + 1) + ": " + islas.get(i) + "\n";
        }

        return mostrar;
    }
}
