package uagrm.inf310sb.grafos.pesados;

import uagrm.inf310sb.grafos.excepciones.AristaNoExisteExcepcion;
import uagrm.inf310sb.grafos.excepciones.AristaYaExisteExcepcion;
import uagrm.inf310sb.grafos.nopesados.ControlMarcado;

import java.util.LinkedList;
import java.util.List;

/**
 * @author GERSON
 */
public class Prim {
    private final GrafoPesado grafo;
    private final GrafoPesado grafoPrim;
    private final ControlMarcado marcados;

    public Prim(GrafoPesado grafo) {
        this.grafo = grafo;
        this.grafoPrim = new GrafoPesado(this.grafo.cantidadDeVertices());
        this.marcados = new ControlMarcado(this.grafo.cantidadDeVertices());
    }

    public void ejecutarPrim() throws AristaNoExisteExcepcion, AristaYaExisteExcepcion {
        List<Integer> listaDeVerticesInsertados = new LinkedList<>();
        this.marcados.marcarVertice(0);
        listaDeVerticesInsertados.add(0);
        int origenPM = -1, destinoPM = -1;

        while (!this.marcados.estanTodosMarcados()) {
            double pesoMenor = Double.POSITIVE_INFINITY;
            for (Integer vertice : listaDeVerticesInsertados) {
//                Iterable<Integer> adyacentesDelVertice = this.grafo.adyacentesDelVertice(vertice);
                for (Integer adyacente : this.grafo.adyacentesDelVertice(vertice)) {
                    if (!this.marcados.estaMarcado(adyacente)) {
                        double peso = this.grafo.peso(vertice, adyacente);

                        if (peso < pesoMenor) {
                            pesoMenor = peso;
                            origenPM = vertice;
                            destinoPM = adyacente;
                        }
                    }
                }
            }
            if (pesoMenor == Double.POSITIVE_INFINITY)
                break;

            listaDeVerticesInsertados.add(destinoPM);
            this.marcados.marcarVertice(destinoPM);
            this.grafoPrim.insertarAristas(origenPM, destinoPM, pesoMenor);
        }
    }

    public void ejecutarPrim(int verticeInicial) throws AristaNoExisteExcepcion,
            AristaYaExisteExcepcion {
        List<Integer> listaDeVerticesInsertados = new LinkedList<>();
        this.marcados.marcarVertice(verticeInicial);
        listaDeVerticesInsertados.add(verticeInicial);
        int origenPM = -1, destinoPM = -1;

        while (!this.marcados.estanTodosMarcados()) {
            double pesoMenor = Double.POSITIVE_INFINITY;
            for (Integer vertice : listaDeVerticesInsertados) {
                for (Integer adyacente : this.grafo.adyacentesDelVertice(vertice)) {
                    if (!this.marcados.estaMarcado(adyacente)) {
                        double peso = this.grafo.peso(vertice, adyacente);

                        if (peso < pesoMenor) {
                            pesoMenor = peso;
                            origenPM = vertice;
                            destinoPM = adyacente;
                        }
                    }
                }
            }
            if (pesoMenor == Double.POSITIVE_INFINITY)
                break;

            listaDeVerticesInsertados.add(destinoPM);
            this.marcados.marcarVertice(destinoPM);
            this.grafoPrim.insertarAristas(origenPM, destinoPM, pesoMenor);
        }
    }

    public GrafoPesado getArbolExpasion() {
        return this.grafoPrim;
    }
}
