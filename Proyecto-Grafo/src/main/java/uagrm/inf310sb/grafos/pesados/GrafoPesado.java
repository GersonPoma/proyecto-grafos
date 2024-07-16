package uagrm.inf310sb.grafos.pesados;

import uagrm.inf310sb.grafos.excepciones.AristaNoExisteExcepcion;
import uagrm.inf310sb.grafos.excepciones.AristaYaExisteExcepcion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * grafo no dirigido con peso
 * @author GERSON
 */
public class GrafoPesado {
    protected final List<List<AdyacenteConPeso>> listasDeAdyacencias;

    public GrafoPesado() {
        this.listasDeAdyacencias = new ArrayList<>();
    }

    public GrafoPesado(int nroDeVertices) {
        if (nroDeVertices < 0) {
            throw new IllegalArgumentException("Cantidad de vertices" +
                    " no puede ser negativo");
        }

        this.listasDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < nroDeVertices; i++) {
            this.insertarVertice();
        }
    }

    public void insertarVertice() {
        List<AdyacenteConPeso> adyacenciasDelNuevoVertice = new ArrayList<>();
        this.listasDeAdyacencias.add(adyacenciasDelNuevoVertice);
    }

    public void validarVertice(int posDeVertice) {
        if (posDeVertice < 0 || posDeVertice >= this.listasDeAdyacencias.size())
            throw new IllegalArgumentException("Posición de vertice inválido");
    }

    public boolean existeAdyacencia(int posDeVerticeOrigen, int posDeVerticeDestino) {
        validarVertice(posDeVerticeOrigen);
        validarVertice(posDeVerticeDestino);

        List<AdyacenteConPeso> adyacentesDelOrigen =
                this.listasDeAdyacencias.get(posDeVerticeOrigen);
        AdyacenteConPeso adyacenteDestino = new AdyacenteConPeso(posDeVerticeDestino);
        return adyacentesDelOrigen.contains(adyacenteDestino);
    }

    public void insertarAristas(int posDeVerticeOrigen,
                                int posDeVerticeDestino, double peso)
            throws AristaYaExisteExcepcion {

        if (existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino))
            throw new AristaYaExisteExcepcion();

        List<AdyacenteConPeso> adyacentesDelOrigen = this.listasDeAdyacencias.get(posDeVerticeOrigen);
        adyacentesDelOrigen.add(new AdyacenteConPeso(posDeVerticeDestino, peso));
        Collections.sort(adyacentesDelOrigen);

        if (posDeVerticeOrigen != posDeVerticeDestino) {
            List<AdyacenteConPeso> adyacentesDelDestino = this.listasDeAdyacencias.get(posDeVerticeDestino);
            adyacentesDelDestino.add(new AdyacenteConPeso(posDeVerticeOrigen, peso));
            Collections.sort(adyacentesDelDestino);
        }
    }

    public int cantidadDeVertices() {
        return this.listasDeAdyacencias.size();
    }

    public int cantidadDeAristas() {
        int cantidad = 0;

        for (List<AdyacenteConPeso> adyacentesDelVertice : this.listasDeAdyacencias) {
            int cantidadDeAdyacentes = adyacentesDelVertice.size();
            cantidad += cantidadDeAdyacentes;
        }

        return cantidad == 1 ? cantidad : cantidad / 2;
    }

    public void eliminarVertice(int posVerticeAEliminar) {
        validarVertice(posVerticeAEliminar);
        this.listasDeAdyacencias.remove(posVerticeAEliminar);

        for (List<AdyacenteConPeso> adyacentesDelVertice : this.listasDeAdyacencias) {
            AdyacenteConPeso verticeAEliminar = new AdyacenteConPeso(posVerticeAEliminar);
            int posVerticeEnLaAdy = adyacentesDelVertice.indexOf(verticeAEliminar);

            if (posVerticeEnLaAdy >= 0)
                adyacentesDelVertice.remove(posVerticeEnLaAdy);

            for (int i = 0; i < adyacentesDelVertice.size(); i++) {
                AdyacenteConPeso adyacenteConPeso = adyacentesDelVertice.get(i);
                int vertice = adyacenteConPeso.getIndiceDelVertice();

                if (vertice > posVerticeAEliminar) {
                    adyacenteConPeso.setIndiceDelVertice(vertice - 1);
                    adyacentesDelVertice.set(i, adyacenteConPeso);
                }
            }
        }
    }

    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino)
            throws AristaNoExisteExcepcion {
        if (!existeAdyacencia(posVerticeOrigen, posVerticeDestino))
            throw new AristaNoExisteExcepcion();

        List<AdyacenteConPeso> adyacentesDelOrigen = this.listasDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso verticeDestino = new AdyacenteConPeso(posVerticeDestino);
        int posVerticeDestinoEnAdy = adyacentesDelOrigen.indexOf(verticeDestino);
        adyacentesDelOrigen.remove(posVerticeDestinoEnAdy);

        if (posVerticeOrigen != posVerticeDestino) {
            List<AdyacenteConPeso> adyacentesDelDestino = this.listasDeAdyacencias.get(posVerticeDestino);
            AdyacenteConPeso verticeOrigen = new AdyacenteConPeso(posVerticeOrigen);
            int posVerticeOrigenEnAdy = adyacentesDelDestino.indexOf(verticeOrigen);
            adyacentesDelDestino.remove(posVerticeOrigenEnAdy);
        }
    }

    public int gradoDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<AdyacenteConPeso> adyacenciasDelVertice = this.listasDeAdyacencias.get(posDeVertice);
        return adyacenciasDelVertice.size();
    }

    public Iterable<Integer> adyacentesDelVertice(int posDeVertice) {
        validarVertice(posDeVertice);

        List<AdyacenteConPeso> adyacentesDelVertice =
                this.listasDeAdyacencias.get(posDeVertice);
        List<Integer> copiaAdyacencia = new ArrayList<>();

        for (AdyacenteConPeso adyacente : adyacentesDelVertice) {
            copiaAdyacencia.add(adyacente.getIndiceDelVertice());
        }

        return (Iterable) copiaAdyacencia;
    }

    public double peso(int posDeVerticeOrigen, int posDeVerticeDestino)
            throws AristaNoExisteExcepcion {
        if (!this.existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino))
            throw new AristaNoExisteExcepcion();

        List<AdyacenteConPeso> adyacentesDelOrigen =
                this.listasDeAdyacencias.get(posDeVerticeOrigen);
        AdyacenteConPeso adyacenteDelDestino = new AdyacenteConPeso(posDeVerticeDestino);
        int posDelAdyacente = adyacentesDelOrigen.indexOf(adyacenteDelDestino);
        AdyacenteConPeso adyacenteDestinoConPeso =
                adyacentesDelOrigen.get(posDelAdyacente);

        return adyacenteDestinoConPeso.getPeso();
    }

    public boolean esCompleto() {
        for (int i = 0; i < this.listasDeAdyacencias.size(); i++) {
            if (!estaEnLasOtrasAdyacencias(i))
                return false;
        }

        return true;
    }

    private boolean estaEnLasOtrasAdyacencias(int vertice) {
        for (int i = 0; i < this.listasDeAdyacencias.size(); i++) {
            if (i != vertice) {
                List<AdyacenteConPeso> adyacenciasDelVerticeAct =
                        this.listasDeAdyacencias.get(i);
                AdyacenteConPeso adyacenteConPeso = new AdyacenteConPeso(vertice);
                int posicion = adyacenciasDelVerticeAct.indexOf(adyacenteConPeso);
                if (posicion == -1)
                    return false;
            }
        }
        return true;
    }


    public String listaDeAdyacencia() {
        String mostrar = "";

        for (int i = 0; i < this.listasDeAdyacencias.size(); i++) {
            mostrar += i + " -> ";
            List<AdyacenteConPeso> adyacentesDelVerticeAct =
                    this.listasDeAdyacencias.get(i);
            mostrar += adyacentesDelVerticeAct + "\n";

        }

        return mostrar;
    }

    public String matrizDeAdyacencias() {
        int cantVertices = this.cantidadDeVertices();
        double[][] matriz = new double[cantVertices][cantVertices];
        String mostrar = "";

        for (int i = 0; i < cantVertices; i++) {
            List<AdyacenteConPeso> adyacenciasDelVerticeAct = this.listasDeAdyacencias.get(i);


            for (AdyacenteConPeso adyacenteConPesoAct : adyacenciasDelVerticeAct) {
                int adyacente = adyacenteConPesoAct.getIndiceDelVertice();
                double pesoDelAdyacente = adyacenteConPesoAct.getPeso();

                matriz[i][adyacente] = pesoDelAdyacente;
            }
        }

        for (int i = 0; i < cantVertices; i++) {
            for (int j = 0; j < cantVertices; j++) {
                mostrar += matriz[i][j] + " ";
            }
            mostrar += '\n';
        }

        return mostrar;
    }

}
