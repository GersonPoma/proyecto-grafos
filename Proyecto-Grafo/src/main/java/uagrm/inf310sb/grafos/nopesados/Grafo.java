package uagrm.inf310sb.grafos.nopesados;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import uagrm.inf310sb.grafos.excepciones.AristaNoExisteExcepcion;
import uagrm.inf310sb.grafos.excepciones.AristaYaExisteExcepcion;

/**
 *
 * @author GERSON
 */
public class Grafo {
    protected final List<List<Integer>> listasDeAdyacencias;
    
    public Grafo() {
        this.listasDeAdyacencias = new ArrayList<>();
    }
    
    public Grafo(int nroDeVertices) {
        if (nroDeVertices < 0) {
            throw new IllegalArgumentException("Cantidad de vertices no puede" + 
                    " ser negativo.");
        }
        
        this.listasDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < nroDeVertices; i++) {
            this.insertarVertice();
        }
    }
    
    public void insertarVertice() {
        List<Integer> adyacenciasDelNuevoVertice = new ArrayList<>();
        this.listasDeAdyacencias.add(adyacenciasDelNuevoVertice);
    }
    
    public void validarVertice(int posDeVertice) {
        if (posDeVertice < 0 || posDeVertice >= this.listasDeAdyacencias.size())
            throw new IllegalArgumentException("Posición de vertice inválido");    
    }
    
    public boolean existeAdyacencia(int posDeVerticeOrigen, int posDeVerticeDestino) {
        validarVertice(posDeVerticeOrigen);
        validarVertice(posDeVerticeDestino);
        
        List<Integer> adyacentesDelOrigen = this.listasDeAdyacencias.get(posDeVerticeOrigen);
        return adyacentesDelOrigen.contains(posDeVerticeDestino);
    }
    
    public void insertarAristas(int posDeVerticeOrigen,
            int posDeVerticeDestino) throws AristaYaExisteExcepcion {
        if (existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino))
            throw new AristaYaExisteExcepcion();
        
        List<Integer> adyacentesDelOrigen = this.listasDeAdyacencias.get(posDeVerticeOrigen);
        adyacentesDelOrigen.add(posDeVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
        
        if (posDeVerticeOrigen != posDeVerticeDestino) {
            List<Integer> adyacentesDelDestino = this.listasDeAdyacencias.get(posDeVerticeDestino);
            adyacentesDelDestino.add(posDeVerticeOrigen);
            Collections.sort(adyacentesDelDestino);
        }
    }
    
    public int cantidadDeVertices() {
        return this.listasDeAdyacencias.size();
    }
    
    public int cantidadDeAristas() {
        int cantidad = 0;
        
        for (List<Integer> adyacentesDelVertice : this.listasDeAdyacencias) {
            int cantidadDeAdyacentes = adyacentesDelVertice.size();
            cantidad += cantidadDeAdyacentes;
        }
        
        return cantidad == 1 ? cantidad : cantidad / 2;
    }
    
    public void eliminarVertice(int posVerticeAEliminar) {
        validarVertice(posVerticeAEliminar);
        this.listasDeAdyacencias.remove(posVerticeAEliminar);
        
        for (List<Integer> adyacentesDelVertice : this.listasDeAdyacencias) {
            int posVerticeEnLaAdy = adyacentesDelVertice.indexOf(posVerticeAEliminar);
            
            if (posVerticeEnLaAdy >= 0)
                adyacentesDelVertice.remove(posVerticeEnLaAdy);
            
            for (int i = 0; i < adyacentesDelVertice.size(); i++) {
                int vertice = adyacentesDelVertice.get(i);
                
                if (vertice > posVerticeAEliminar)
                    adyacentesDelVertice.set(i, vertice - 1);
            }
        }
    }
    
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino)
            throws AristaNoExisteExcepcion {
        if (!existeAdyacencia(posVerticeOrigen, posVerticeDestino))
            throw new AristaNoExisteExcepcion();
        
        List<Integer> adyacentesDelOrigen = this.listasDeAdyacencias.get(posVerticeOrigen);
        int posVerticeDestinoEnAdy = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posVerticeDestinoEnAdy);
        
        if (posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacentesDelDestino = this.listasDeAdyacencias.get(posVerticeDestino);
            int posVerticeOrigenEnAdy = adyacentesDelDestino.indexOf(posVerticeOrigen);
            adyacentesDelDestino.remove(posVerticeOrigenEnAdy);
        }
    }
    
    public int gradoDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacenciasDelVertice = this.listasDeAdyacencias.get(posDeVertice);
        return adyacenciasDelVertice.size();
    }

    public Iterable<Integer> adyacentesDelVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        return this.listasDeAdyacencias.get(posDeVertice);
    }

    /*
    Hecho con la fórmula de permutación
    El número de aristas de un grafo tiene que ser igual a la fórmula de la
    permutación:
    - Donde n = la cantidad de vértices que tiene el grafo
                    n(n-1)
                    -------
                       2
     */
//    public boolean esCompleto() {
//        int cantVertices = this.cantidadDeVertices();
//
//        int cantidadAristasEnUnGrafoCompleto = (cantVertices * (cantVertices - 1)) / 2;
//
//        return  cantidadAristasEnUnGrafoCompleto == this.cantidadDeAristas();
//    }

    public boolean esCompleto() {
        for (int i = 0; i <= this.listasDeAdyacencias.size(); i++) {
            if (!estaEnLasOtrasAdyacencias(i))
                return false;
        }

        return true;
    }

    private boolean estaEnLasOtrasAdyacencias(int vertice) {
        for (int i = 0; i < this.listasDeAdyacencias.size(); i++) {
            if (i != vertice) {
                List<Integer> adyacenciasDelVerticeAct =
                        this.listasDeAdyacencias.get(i);
                int posicion = adyacenciasDelVerticeAct.indexOf(vertice);
                if (posicion == -1)
                    return false;
            }
        }
        return true;
    }

    public int cantidadDeIslas() {
        DFS dfs = new DFS(this, 0);

        if (dfs.llegoATodos())
            return 1;

        int cantidad = 1;

        while (!dfs.llegoATodos()) {
            for (int i = 0; i < this.cantidadDeVertices(); i++) {
                if (!dfs.llegoAVertice(i)) {
                    cantidad++;
                    dfs.ejecutarDFS(i);
                }
            }
        }

        return cantidad;
    }

    public String listaDeAdyacencias() {
        StringBuilder mostrar =new StringBuilder();

        for (int i = 0; i < this.listasDeAdyacencias.size(); i++) {
            mostrar.append(i);
            mostrar.append(" -> ");
            List<Integer> adyacenciasDeUnVertice = this.listasDeAdyacencias.get(i);
            mostrar.append(adyacenciasDeUnVertice);
            mostrar.append('\n');
        }

        return mostrar.toString();
    }

    public String matrizDeAdyacencias() {
        int cantVertices = this.cantidadDeVertices();
        int[][] matriz = new int[cantVertices][cantVertices];
        String mostrar = "";

        for (int i = 0; i < cantVertices; i++) {
            List<Integer> adyacenciasDelVerticeAct = this.listasDeAdyacencias.get(i);

            for (Integer vertice : adyacenciasDelVerticeAct) {
                matriz[i][vertice] = 1;
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
