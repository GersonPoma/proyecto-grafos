package uagrm.inf310sb.grafos.pesados;

import uagrm.inf310sb.grafos.excepciones.AristaNoExisteExcepcion;
import uagrm.inf310sb.grafos.excepciones.AristaYaExisteExcepcion;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author GERSON
 */
public class Kruskal {
    private class AristaConPeso implements Comparable<AristaConPeso>{
        private int origen;
        private int destino;
        private double peso;

        public AristaConPeso(int origen, int destino, double peso) {
            this.origen = origen;
            this.destino = destino;
            this.peso = peso;
        }

        public int getOrigen() {
            return origen;
        }

        public int getDestino() {
            return destino;
        }

        public double getPeso() {
            return peso;
        }

        @Override
        public String toString() {
            return "(" + this.origen + " - " + this.destino + " = " + this.peso + ")";
        }

        @Override
        public int compareTo(AristaConPeso otraArista) {
            return Double.compare(this.peso, otraArista.peso);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AristaConPeso that = (AristaConPeso) o;
            return this.compareTo(that) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(peso);
        }
    }
    private final GrafoPesado grafo;
    private final List<AristaConPeso> listaDeAristas;
    private GrafoPesado grafoKruskal;

    public Kruskal(GrafoPesado grafo) {
        this.grafo = grafo;
        this.listaDeAristas = new LinkedList<>();
        this.llenarLista();
    }

    private void llenarLista() {
        for (int vertice = 0; vertice < this.grafo.cantidadDeVertices(); vertice++) {
            for (int adyacente : this.grafo.adyacentesDelVertice(vertice)) {
                double peso = 0;
                try {
                    peso = this.grafo.peso(vertice, adyacente);
                } catch (AristaNoExisteExcepcion e) {
                    throw new IllegalArgumentException(e);
                }
                this.listaDeAristas.add(new AristaConPeso(vertice, adyacente, peso));
            }
        }

        Collections.sort(this.listaDeAristas);
    }

    public void ejecutarKruskal() throws AristaYaExisteExcepcion, AristaNoExisteExcepcion {
        this.grafoKruskal = new GrafoPesado(this.grafo.cantidadDeVertices());

        for (AristaConPeso arista : listaDeAristas) {
            int origen = arista.getOrigen();
            int destino = arista.getDestino();
            double peso = arista.getPeso();

            if (!this.grafoKruskal.existeAdyacencia(destino, origen)) {
                this.grafoKruskal.insertarAristas(origen, destino, peso);

                CicloGrafoPesado ciclo = new CicloGrafoPesado(this.grafoKruskal);
                if (ciclo.hayCiclo()) {
                    this.grafoKruskal.eliminarArista(origen, destino);
                }
            }

        }
    }

    public List<AristaConPeso> getListaDeAristas() {
        return listaDeAristas;
    }

    public GrafoPesado obtenerArbolDeExpasion() {
        return this.grafoKruskal;
    }
}
