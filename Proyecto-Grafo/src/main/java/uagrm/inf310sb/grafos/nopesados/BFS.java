package uagrm.inf310sb.grafos.nopesados;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Búsqueda por anchura
 * @author GERSON
 */
public class BFS {
    private final Grafo elGrafo;
    private final ControlMarcado marcados;
    private final List<Integer> recorrido;

    public BFS(Grafo unGrafo, int posVerticeInicial) {
        this.elGrafo = unGrafo;
        this.recorrido = new ArrayList<>();
        this.marcados = new ControlMarcado(this.elGrafo.cantidadDeVertices());
        ejecutarBFS(posVerticeInicial);
    }
    
    public void ejecutarBFS(int posDeVertice) {
        this.elGrafo.validarVertice(posDeVertice);
        Queue<Integer> colaDePosVertice = new LinkedList<>();
        colaDePosVertice.offer(posDeVertice);
        this.marcados.marcarVertice(posDeVertice);
        
        do {
            int posDeVerticeAct = colaDePosVertice.poll();
            Iterable<Integer> adysDeVerticeAct =
                    this.elGrafo.adyacentesDelVertice(posDeVerticeAct);
            this.recorrido.add(posDeVerticeAct);
            
            for (Integer posDeAdy : adysDeVerticeAct) {
                if (!this.marcados.estaMarcado(posDeAdy)) {
                    colaDePosVertice.offer(posDeAdy);
                    this.marcados.marcarVertice(posDeAdy);
                }
            }
        } while (!colaDePosVertice.isEmpty());
    }
    
    public Iterable<Integer> getRecorrido() {
        return this.recorrido;
    }
    
    public boolean llegoATodos() {
        return this.marcados.estanTodosMarcados();
    }
    
    public boolean llegoAVertice(int posDeVerticeDestino) {
        this.elGrafo.validarVertice(posDeVerticeDestino);
        return this.marcados.estaMarcado(posDeVerticeDestino);
    }
}
