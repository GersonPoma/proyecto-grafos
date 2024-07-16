package uagrm.inf310sb.grafos.pesados;

import java.util.Objects;

/**
 *
 * @author GERSON
 */
public class AdyacenteConPeso implements Comparable<AdyacenteConPeso> {
    private int indiceDeVertice;
    private double peso;

    public AdyacenteConPeso() {
    }

    public AdyacenteConPeso(int indiceDeVertice) {
        this.indiceDeVertice = indiceDeVertice;
    }

    public AdyacenteConPeso(int indiceDeVertice, double peso) {
        this.indiceDeVertice = indiceDeVertice;
        this.peso = peso;
    }

    public int getIndiceDelVertice() {
        return indiceDeVertice;
    }

    public void setIndiceDelVertice(int indiceDeVertice) {
        this.indiceDeVertice = indiceDeVertice;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public int compareTo(AdyacenteConPeso otroAdyacenteConPeso) {
        return this.indiceDeVertice - otroAdyacenteConPeso.indiceDeVertice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdyacenteConPeso that = (AdyacenteConPeso) o;
        return this.compareTo(that) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(indiceDeVertice);
    }

    @Override
    public String toString() {
        return "(" + this.indiceDeVertice + "," + this.peso+")";
    }
}
