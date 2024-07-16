package uagrm.inf310sb.grafos.nopesados;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GERSON
 */
public class Warshall {
    private final int[][] matrizCamino;
    private final int[][] matrizOriginal;
    List<int[][]> historialDeCaminos = new ArrayList<>();
    private final DiGrafo grafo;

    public Warshall(DiGrafo grafo) {
        this.grafo = grafo;
        int cantVertices = this.grafo.cantidadDeVertices();
        this.matrizCamino = new int[cantVertices][cantVertices];
        this.matrizOriginal = new int[cantVertices][cantVertices];
        rellenarMatrices();
    }

    private void rellenarMatrices() {
        for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
            Iterable<Integer> adyacentesDelVerticeAct =
                    this.grafo.adyacentesDelVertice(i);

            for (Integer vertice : adyacentesDelVerticeAct) {
                this.matrizCamino[i][vertice] = 1;
                this.matrizOriginal[i][vertice] = 1;

            }
        }
    }

    public void buscarCaminos() {
        for (int k = 0; k < this.grafo.cantidadDeVertices(); k++) {
            for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
                if (i != k) {
                    for (int j = 0; j < this.grafo.cantidadDeVertices(); j++) {
                        if (j != k) {
                            if (this.matrizCamino[i][k] == 1 &&
                                    this.matrizCamino[k][j] == 1)
                                this.matrizCamino[i][j] = 1;
                        }
                    }
                }
            }
            int[][] matriz = this.copiarMatrizCamino();
            this.historialDeCaminos.add(matriz);
        }
    }

    private int[][] copiarMatrizCamino() {
        int cantVertices = this.grafo.cantidadDeVertices();
        int[][] matriz = new int[cantVertices][cantVertices];

        for (int i = 0; i < cantVertices; i++) {
            for (int j = 0; j < cantVertices; j++) {
                matriz[i][j] = this.matrizCamino[i][j];
            }
        }

        return matriz;
    }

    public int[][] getMatrizCamino() {
        return this.matrizCamino;
    }

    public int[][] getMatrizOriginal() {
        return this.matrizOriginal;
    }

    public List<int[][]> getHistorialDeCaminos() {
        return this.historialDeCaminos;
    }

    public String mostrarMatrizDeCamino() {
        StringBuilder mostrar = new StringBuilder();

        for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
            for (int j = 0; j < this.grafo.cantidadDeVertices(); j++) {
                mostrar.append(this.matrizCamino[i][j]).append(" ");
            }
            mostrar.append('\n');
        }

        return mostrar.toString();
    }

    public String mostrarMatrizOriginal() {
        StringBuilder mostrar = new StringBuilder();

        for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
            for (int j = 0; j < this.grafo.cantidadDeVertices(); j++) {
                mostrar.append(this.matrizOriginal[i][j]).append(" ");
            }
            mostrar.append('\n');
        }

        return mostrar.toString();
    }

    public String mostrarHistorialDeCaminos() {
        StringBuilder mostrar = new StringBuilder();

        for (int k = 0; k < this.historialDeCaminos.size(); k++) {
            int[][] matriz = this.historialDeCaminos.get(k);
            mostrar.append("K = ").append(k).append('\n');
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[0].length ; j++) {
                    mostrar.append(matriz[i][j]).append(" ");
                }
                mostrar.append('\n');
            }
            mostrar.append('\n');
        }

        return mostrar.toString();
    }

}
