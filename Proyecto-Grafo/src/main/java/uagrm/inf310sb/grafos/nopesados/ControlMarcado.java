package uagrm.inf310sb.grafos.nopesados;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GERSON
 */
public class ControlMarcado {
    private final List<Boolean> marcados;
    private final int cantidadVertices;

    public ControlMarcado(int cantVertices) {
        this.cantidadVertices = cantVertices;
        marcados = new ArrayList<>();
        
        for (int i = 0; i < this.cantidadVertices; i++) {
            this.marcados.add(Boolean.FALSE);
        }
    }

    public void marcarVertice(int posVertice) {
        this.marcados.set(posVertice, true);
    }
    
    public boolean estaMarcado(int posVertice) {
        return this.marcados.get(posVertice);
    }
    
    public boolean estanTodosMarcados() {
        for (int i = 0; i < this.marcados.size(); i++) {
            if (!this.marcados.get(i))
                return false;
        }
        
        return true;
    }

    public void desmarcarTodos() {
        int i = 0;

        while (i < this.marcados.size()) {
            this.marcados.set(i, false);
            i++;
        }
    }

    public void desmarcarVertice(int posVertice) {
        this.marcados.set(posVertice, false);
    }
}
