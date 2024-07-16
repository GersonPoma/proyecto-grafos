package uagrm.inf310sb.grafos.nopesados;

/**
 *
 * @author GERSON
 */
public class CicloDiGrafo {
    private final Grafo elGrafo;
    private final ControlMarcado visitados;
    private final ControlMarcado enElCamino;
    private boolean hayCiclo;

    public CicloDiGrafo(Grafo elGrafo) {
        this.elGrafo = elGrafo;
        this.visitados = new ControlMarcado(elGrafo.cantidadDeVertices());
        this.enElCamino = new ControlMarcado(elGrafo.cantidadDeVertices());
        this.hayCiclo = false;
        detectarCiclo();
    }

    private void detectarCiclo() {
        for (int i = 0; i < this.elGrafo.cantidadDeVertices(); i++) {
            if (!this.visitados.estaMarcado(i)) {

                if (encontroCiclo(i)) {
                    this.hayCiclo = true;
                    return;
                }
            }
        }
    }

    private boolean encontroCiclo(int posDeVertice) {
        this.enElCamino.marcarVertice(posDeVertice);

        Iterable<Integer> adyacentesDelVertice =
                this.elGrafo.adyacentesDelVertice(posDeVertice);

        for (Integer vertice : adyacentesDelVertice) {
            if (!this.visitados.estaMarcado(vertice)) {
                this.visitados.marcarVertice(vertice);

                if (encontroCiclo(vertice)) {
                    return true;
                }
            } else if (this.enElCamino.estaMarcado(vertice)) {
                return true;
            }
        }

        this.enElCamino.desmarcarVertice(posDeVertice);
        return false;
    }

    public boolean hayCiclo() {
        return this.hayCiclo;
    }
}
