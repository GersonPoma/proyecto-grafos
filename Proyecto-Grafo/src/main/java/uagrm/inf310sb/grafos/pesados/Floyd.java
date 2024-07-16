package uagrm.inf310sb.grafos.pesados;

import uagrm.inf310sb.grafos.excepciones.AristaNoExisteExcepcion;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author GERSON
 */
public class Floyd {
    private final double[][] matrizDePesos;
    private final int[][] matrizDePredecesores;
    private final DiGrafoPesado grafo;

    public Floyd(DiGrafoPesado grafo) throws AristaNoExisteExcepcion {
        this.grafo = grafo;
        int nroVertices = this.grafo.cantidadDeVertices();
        this.matrizDePesos = new double[nroVertices][nroVertices];
        this.matrizDePredecesores = new int[nroVertices][nroVertices];
        this.inicializarMatrices();
    }

    private void inicializarMatrices() throws AristaNoExisteExcepcion {
        int nroVertices = this.grafo.cantidadDeVertices();

        for (int i = 0; i < nroVertices; i++) {
            for (int j = 0; j < nroVertices; j++) {
                if (i == j) {
                    this.matrizDePesos[i][j] = 0;
                    this.matrizDePredecesores[i][j] = -1;
                } else {
                    if (this.grafo.existeAdyacencia(i, j)) {
                        double peso = this.grafo.peso(i, j);
                        this.matrizDePesos[i][j] = peso;
                    } else {
                        this.matrizDePesos[i][j] = Double.POSITIVE_INFINITY;
                    }

                    this.matrizDePredecesores[i][j] = j;
                }
            }
        }
    }

    public void ejecutarFloyd() {
        int nroVertices = this.grafo.cantidadDeVertices();

        for (int k = 0; k < nroVertices; k++) {
            for (int i = 0; i < nroVertices; i++) {
                for (int j = 0; j < nroVertices; j++) {
                    double sumaDePesos = this.matrizDePesos[i][k] +
                            this.matrizDePesos[k][j];

                    if (this.matrizDePesos[i][j] > sumaDePesos) {
                        this.matrizDePesos[i][j] = sumaDePesos;
                        this.matrizDePredecesores[i][j] = k;
                    }
                }
            }
        }
    }

    public double costoMinimo(int verticeOrigen, int verticeDestino) {
        return this.matrizDePesos[verticeOrigen][verticeDestino];
    }

    public List<Integer> obtenerCamino(int verticeOrigen, int verticeDestino) {
        List<Integer> caminos = new LinkedList<>();
        Stack<Integer> pilaDeCaminos = new Stack<>();

        if (costoMinimo(verticeOrigen, verticeDestino) == Double.POSITIVE_INFINITY)
            return caminos;

        int verticeActual = verticeDestino;

        while (verticeActual != this.matrizDePredecesores[verticeOrigen][verticeActual]) {
            pilaDeCaminos.push(verticeActual);
            verticeActual = this.matrizDePredecesores[verticeOrigen][verticeActual];
        }

        pilaDeCaminos.push(this.matrizDePredecesores[verticeOrigen][verticeActual]);
        pilaDeCaminos.push(verticeOrigen);

        while (!pilaDeCaminos.isEmpty()) {
            caminos.add(pilaDeCaminos.pop());
        }

        return caminos;
    }
}
