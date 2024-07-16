package uagrm.inf310sb.grafos.nopesados;

import java.util.ArrayList;
import java.util.List;

/**
 * Búsqueda en profundidad
 * @author GERSON
 */
public class DFS {
    protected final Grafo elGrafo;
    protected final ControlMarcado marcados;
    protected final List<Integer> recorrido;

    public DFS(Grafo unGrafo, int posVerticeInicial) {
        this.elGrafo = unGrafo;
        this.recorrido = new ArrayList<>();
        this.marcados = new ControlMarcado(this.elGrafo.cantidadDeVertices());
        ejecutarDFS(posVerticeInicial);
    }

    public DFS(Grafo unGrafo) {
        this.elGrafo = unGrafo;
        this.recorrido = new ArrayList<>();
        this.marcados = new ControlMarcado(this.elGrafo.cantidadDeVertices());
    }
    
    public void ejecutarDFS(int posDeVerticeAct) {
        this.elGrafo.validarVertice(posDeVerticeAct);
        this.marcados.marcarVertice(posDeVerticeAct);
        Iterable<Integer> adysDeVerticeAct =
                this.elGrafo.adyacentesDelVertice(posDeVerticeAct);
        this.recorrido.add(posDeVerticeAct);
        
        for (Integer posDeAdy : adysDeVerticeAct) {
            if (!this.marcados.estaMarcado(posDeAdy)) {
                ejecutarDFS(posDeAdy);
            }
        }
    }
    
    public Iterable<Integer> getRecorrido() {
        return this.recorrido;
    }
    
    public boolean llegoATodos() {
        return this.marcados.estanTodosMarcados();
    }
    
    public boolean llegoAVertice(int podDeVerticeDestino) {
        this.elGrafo.validarVertice(podDeVerticeDestino);
        return this.marcados.estaMarcado(podDeVerticeDestino);
    }
}
