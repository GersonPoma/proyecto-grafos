package uagrm.inf310sb.ejecutador;

import java.util.List;
import javax.swing.JOptionPane;
import uagrm.inf310sb.grafos.excepciones.AristaNoExisteExcepcion;
import uagrm.inf310sb.grafos.excepciones.AristaYaExisteExcepcion;
import uagrm.inf310sb.grafos.excepciones.DiGrafoConCicloExcepcion;
import uagrm.inf310sb.grafos.excepciones.GrafoNoConexoExcepcion;
import uagrm.inf310sb.grafos.excepciones.NoHayCaminoExcepcion;
import uagrm.inf310sb.grafos.nopesados.DiGrafo;
import uagrm.inf310sb.grafos.pesados.Dijkstra;
import uagrm.inf310sb.grafos.pesados.GrafoPesado;
import uagrm.inf310sb.grafos.pesados.Kruskal;
import uagrm.inf310sb.grafos.pesados.Prim;

/**
 *
 * @author GERSON
 */
public class Ejecutor {
    private static String[] departamentos = {"Santa Cruz", "Trinidad", "Tarija",
        "Cochabamba", "Sucre", "Potosi", "Oruro", "La Paz", "Cobija"};
    
    private static String[] pasos = {
        "Conocer el tema",                 //0
        "Ver plantillas preparadas",       //1
        "Programar lo necesario",          //2
        "Analiza a competidores",          //3
        "Plantillas finalistas",           //4
        "Decidir que no hacer",            //5
        "Programacion adicional",          //6
        "Que mas necesitas",               //7
        "Finalizacion del proyecto"};      //8
 
    public static String ejecutarDijkstra(int inicio, int fin) {
        /**
         * Santa Cruz = 0
           Trinidad = 1
           Tarija = 2
           Cochabamba = 3
           Sucre = 4
           Potosi = 5
           Oruro = 6
           La Paz = 7
           Cobija = 8
         */
        GrafoPesado g = new GrafoPesado(9);
        
        try {
            g.insertarAristas(0, 1, 134.0);
            g.insertarAristas(0, 2, 210.0);
            g.insertarAristas(0, 4, 130.0);
            g.insertarAristas(0, 3, 131.0);
            g.insertarAristas(0, 7, 210.0);
            g.insertarAristas(3, 6, 68.0);
            g.insertarAristas(3, 7, 106.0);
            g.insertarAristas(3, 4, 110.0);
            g.insertarAristas(4, 2, 120.0);
            g.insertarAristas(4, 5, 20.0);
            g.insertarAristas(6, 5, 45.0);
            g.insertarAristas(5, 2, 110.0);
            g.insertarAristas(7, 8, 210.0);
        } catch (AristaYaExisteExcepcion ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
        
        Dijkstra d = new Dijkstra(g);
        
        try {
            d.ejecutarDijkstra(inicio, fin);
        } catch (NoHayCaminoExcepcion ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
        double costoMinimo = d.costoMinimo();
        List<Integer> camino = d.obtenerCamino();
        
        return mostrarCamino(costoMinimo, camino);
    }
    
    private static String mostrarCamino(double costoMinimo, List<Integer> camino) {
        String mostrar = "Costo minimo = " + costoMinimo + "\n";
        mostrar += "Ruta: \n";
        
        for (int i = 0; i < camino.size(); i++) {
            int vertice = camino.get(i);
            String depa = departamentos[vertice];
            
            if (i == camino.size() - 1) {
                mostrar += (i + 1) + " -> " + depa;
            } else {
                mostrar += (i + 1) + " -> " + depa + "\n";
            }
        }
        
        return mostrar;
    }
    
    private static int getIndex(String departamento) {
        for (int i = 0; i < Ejecutor.departamentos.length; i++) {
            if (Ejecutor.departamentos[i].equals(departamento))
                return i;
        }
        
        return -1;
    }
    
    public static String ejecutarOrdenTopologico() {
        DiGrafo g = new DiGrafo(9);
        
        try {
            g.insertarAristas(0, 1);
            g.insertarAristas(0, 2);
            g.insertarAristas(1, 4);
            g.insertarAristas(0, 3);
            g.insertarAristas(2, 6);
            g.insertarAristas(3, 5);
            g.insertarAristas(5, 4);
            g.insertarAristas(4, 7);
            g.insertarAristas(7, 6);
//         sin insertar esa arista, el grafo no es conexo  
            g.insertarAristas(6, 8);
//         insertando esa arista el grafo tiene ciclo            
//            g.insertarAristas(6, 5);
        } catch (AristaYaExisteExcepcion ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
        
        List<Integer> orden;
        try {
            orden = g.ordenTopologico();
        } catch (DiGrafoConCicloExcepcion | GrafoNoConexoExcepcion ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
        
        return ordenDePasos(orden);
    }
    
    private static String ordenDePasos(List<Integer> orden) {
        String mostrar = "Orden a seguir:\n";
        
        for (int i = 0; i < orden.size(); i++) {
            int vertice = orden.get(i);
            String paso = pasos[vertice];
            
            if (i == orden.size() - 1) {
                mostrar += (i + 1) + ". " + paso;
            } else {
                mostrar += (i + 1) + ". " + paso + "\n";
            }
        }
        
        return mostrar;
    }
    
    public static String ejecutarPrim(int verticeIni) {
        GrafoPesado g = new GrafoPesado(5);
        
        try {
            g.insertarAristas(0, 1, 2);
            g.insertarAristas(0, 2, 5);
            g.insertarAristas(1, 2, 5);
            g.insertarAristas(1, 3, 3);
            g.insertarAristas(1, 4, 2);
            g.insertarAristas(2, 4, 4);
            g.insertarAristas(3, 4, 4);
            g.insertarAristas(0, 4, 6);
        } catch (AristaYaExisteExcepcion ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
        
        Prim p = new Prim(g);
        try {
            p.ejecutarPrim(verticeIni);
        } catch (AristaNoExisteExcepcion | AristaYaExisteExcepcion ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
        
        GrafoPesado gp = p.getArbolExpasion();
        
        return gp.listaDeAdyacencia();
    }
    
    public static String ejecutarKruskal1() {
        GrafoPesado g = new GrafoPesado(9);
        
        try {
            g.insertarAristas(0, 1, 4);
            g.insertarAristas(0, 7, 9);
            g.insertarAristas(1, 7, 11);
            g.insertarAristas(1, 2, 9);
            g.insertarAristas(7, 8, 7);
            g.insertarAristas(8, 2, 2);
            g.insertarAristas(8, 6, 6);
            g.insertarAristas(2, 3, 7);
            g.insertarAristas(2, 5, 4);
            g.insertarAristas(5, 3, 15);
            g.insertarAristas(5, 4, 11);
            g.insertarAristas(3, 4, 10);
            g.insertarAristas(7, 6, 1);
            g.insertarAristas(6, 5, 2);
        } catch (AristaYaExisteExcepcion ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
        
        Kruskal k = new Kruskal(g);
        
        try {
            k.ejecutarKruskal();
        } catch (AristaYaExisteExcepcion | AristaNoExisteExcepcion ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
        
        GrafoPesado gk = k.obtenerArbolDeExpasion();
        return gk.listaDeAdyacencia();
    }
    
    public static String ejecutarKruskal2() {
        GrafoPesado g = new GrafoPesado(9);
        
        try {
            g.insertarAristas(0, 1, 4);
            g.insertarAristas(0, 7, 9);
            g.insertarAristas(1, 7, 11);
            g.insertarAristas(1, 2, 9);
            g.insertarAristas(7, 8, 7);
            g.insertarAristas(8, 2, 2);
            g.insertarAristas(8, 6, 6);
            
            
            g.insertarAristas(5, 3, 15);
            g.insertarAristas(5, 4, 11);
            g.insertarAristas(3, 4, 10);
            g.insertarAristas(7, 6, 1);
         
        } catch (AristaYaExisteExcepcion ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
        
        Kruskal k = new Kruskal(g);
        
        try {
            k.ejecutarKruskal();
        } catch (AristaYaExisteExcepcion | AristaNoExisteExcepcion ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }
        
        GrafoPesado gk = k.obtenerArbolDeExpasion();
        return gk.listaDeAdyacencia();
    }
}
