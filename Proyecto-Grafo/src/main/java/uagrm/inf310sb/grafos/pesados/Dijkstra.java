package uagrm.inf310sb.grafos.pesados;

import uagrm.inf310sb.grafos.excepciones.AristaNoExisteExcepcion;
import uagrm.inf310sb.grafos.excepciones.NoHayCaminoExcepcion;
import uagrm.inf310sb.grafos.nopesados.ControlMarcado;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author GERSON
 */
public class Dijkstra {
    private final double INFINITO = Double.POSITIVE_INFINITY;

    private final GrafoPesado grafo;
    private final ControlMarcado marcados;
    private final List<Integer> predecesores;
    private final List<Double> costos;
    private int origen;
    private int destino;

    public Dijkstra(GrafoPesado grafo) {
        this.grafo = grafo;
        this.marcados = new ControlMarcado(this.grafo.cantidadDeVertices());
        this.predecesores = new ArrayList<>();
        this.costos = new ArrayList<>();
        this.origen = -1;
        this.destino = -1;
        inicializarValores();
    }

    public void ejecutarDijkstra(int posInicio, int posFin)
            throws NoHayCaminoExcepcion {
        this.origen = posInicio;
        this.destino = posFin;
        this.costos.set(posInicio, 0.0);

        while (!llegoAlDestino(posFin)) {
            int vertice = verticeDeMenorCosto();

            if (vertice == -1)
                break;

            Iterable<Integer> adyacentesDelVertice =
                    this.grafo.adyacentesDelVertice(vertice);

            for (int adyacente : adyacentesDelVertice) {
                if (!this.marcados.estaMarcado(adyacente)) {
                    double costoTotal = 0;
                    try {
                        costoTotal = this.costos.get(vertice) +
                                this.grafo.peso(vertice, adyacente);
                    } catch (AristaNoExisteExcepcion e) {
                        e.printStackTrace();
                    }

                    if (costoTotal < this.costos.get(adyacente)) {
                        this.costos.set(adyacente, costoTotal);
                        this.predecesores.set(adyacente, vertice);
                    }
                }
            }
            this.marcados.marcarVertice(vertice);
        }

        if (!this.llegoAlDestino(posFin)) {
            throw new NoHayCaminoExcepcion("No hay camino para el destino " + posFin);
        }
    }

    public int verticeDeMenorCosto() {
        double menor = Double.POSITIVE_INFINITY;
        int indice = -1;

        for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
            if (!this.marcados.estaMarcado(i)) {
                if (this.costos.get(i) < menor) {
                    menor = this.costos.get(i);
                    indice = i;
                }
            }
        }

        return indice;
    }

    private boolean llegoAlDestino(int destino) {
        return this.marcados.estaMarcado(destino);
    }

    private void inicializarValores() {
        for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
            this.predecesores.add(-1);
            this.costos.add(INFINITO);
        }
    }

    public double costoMinimo() {
//        if (this.llegoAlDestino(this.destino))
//            return this.costos.get(destino);
//        else
//            return INFINITO;

        if (this.destino == -1)
            return INFINITO;
        else
            return this.costos.get(destino);
    }

    public List<Integer> obtenerCamino() {
        List<Integer> camino = new LinkedList<>();
        Stack<Integer> pila = new Stack<>();
        int vertice = this.destino;
        while (vertice != this.origen) {
            pila.push(vertice);
            vertice = this.predecesores.get(vertice);
        }
        pila.push(this.origen);

        while (!pila.isEmpty()) {
            camino.add(pila.pop());
        }

        return camino;
    }

}
