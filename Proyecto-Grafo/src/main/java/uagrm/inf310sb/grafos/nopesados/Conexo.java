package uagrm.inf310sb.grafos.nopesados;

/**
 *
 * @author GERSON
 */
public class Conexo {
    private DFS dfs;
    
    public Conexo(Grafo grafo) {
        this.dfs = new DFS(grafo);
    }
    
    public boolean esConexo() {
        return this.dfs.llegoATodos();
    }
}
