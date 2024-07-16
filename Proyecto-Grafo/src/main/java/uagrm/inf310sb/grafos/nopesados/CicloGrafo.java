package uagrm.inf310sb.grafos.nopesados;

/**
 *
 * @author GERSON
 */
public class CicloGrafo {
    private final Grafo elGrafo;
    private final ControlMarcado marcados;
    private boolean hayCiclo;

    public CicloGrafo(Grafo elGrafo) {
        this.elGrafo = elGrafo;
        this.marcados = new ControlMarcado(this.elGrafo.cantidadDeVertices());
        this.hayCiclo = false;
        detectarCiclo();
    }

    private void detectarCiclo() {
        int cantVertices = this.elGrafo.cantidadDeVertices();
        for (int i = 0; i < cantVertices; i++) {
            if (!this.marcados.estaMarcado(i)) {
                if (encontroCiclo(i, -1)) {
                    this.hayCiclo = true;
                    return;
                }
            }
        }
    }

    private boolean encontroCiclo(int posVertice, int padreVertice) {
        this.marcados.marcarVertice(posVertice);
        Iterable<Integer> adyacentesDelVertice =
                this.elGrafo.adyacentesDelVertice(posVertice);

        for (Integer vertice : adyacentesDelVertice) {
            if (!this.marcados.estaMarcado(vertice)) {
                if (encontroCiclo(vertice, posVertice))
                    return true;
            } else if (vertice != padreVertice) {
                return true;
            }
        }

        return false;
    }

    public boolean hayCiclo() {
        return this.hayCiclo;
    }
}
