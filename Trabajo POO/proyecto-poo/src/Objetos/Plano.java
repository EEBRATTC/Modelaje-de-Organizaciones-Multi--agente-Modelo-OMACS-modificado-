package Objetos;

import java.awt.Point;
import java.util.ArrayList;


public class Plano {

    private ArrayList<Nodo> nodos = new ArrayList();
    private ArrayList<Arco> arcos = new ArrayList();
    

    public Plano() {
    }

    
    // METODOS DE NODOS
    public boolean adicionarNodo(Nodo nodoN) {

        for (Nodo nodo : nodos) {
            
            // VERIFICAR ID UNICO
            if (nodo.getId() == nodoN.getId()) {
                return false;
            }
        }

        nodos.add(nodoN);
        return true;
    }
    
    public Nodo buscarNodo(Point punto) {
        
        for (Nodo nodo : nodos) {
            int radio = 80 / 2;
            double distancia = Math.sqrt(Math.pow(nodo.getX() - punto.x, 2) + Math.pow(nodo.getY() - punto.y, 2));
            if (distancia < radio) {
                return nodo;
            }
        }
        return null;
    }
    
    public Nodo buscarNodo(int id) {
        
        for (Nodo nodo : nodos) {
            if (nodo.getId() == id) {
                return nodo;
            }
        }
        return null;
    }
    
    public void moverNodo(Nodo nodo, Point punto) {
        
        for (int i = 0; i < nodos.size(); i++) {
            if (nodos.get(i).getId() == nodo.getId()) {
                nodo.setX(punto.x);
                nodo.setY(punto.y);
                nodos.set(i, nodo);
                break;
            }
        }
    }
    
    public void modificarNodo(Nodo nodo, int id) {
        
        for (int i = 0; i < nodos.size(); i++) {
            if (nodos.get(i).getId() == id) {
                nodos.set(i, nodo);
                break;
            }
        }
    }
    
    public void eliminarNodo(Nodo nodo) {
        
        for (int i = 0; i < nodos.size(); i++) {
            if (nodos.get(i).getId() == nodo.getId()) {
                for (int j = 0; j < arcos.size(); j++) {
                    if (arcos.get(j).getNodoOrigen() == nodo.getId()
                        || arcos.get(j).getNodoDestino() == nodo.getId()) {
                            arcos.remove(j--);
                    }
                }
                nodos.remove(i);
                break;
            }
        }
    }

    // METODOS DE ARCOS
    
    public Arco buscarArco(Point punto) {
        
        for (Arco arco : arcos) {
            Nodo nI = buscarNodo(arco.getNodoOrigen());
            Nodo nF = buscarNodo(arco.getNodoDestino());
            
            double m = (double) (nI.getY() - nF.getY())/(double) (nI.getX() - nF.getX());
            double yAux = m * (punto.x - nI.getX()) + nI.getY();

            if (Math.abs(yAux - punto.y) < 8) {
                return arco;
            }
        }
        return null;
    }

    public Arco buscarArco(int id) {
        
        for (Arco arco : arcos) {
            if (arco.getId() == id) {
                return arco;
            }
        }
        return null;
    }
        
    public boolean adicionarArco(Arco arcoN) {

        for (Arco arco : arcos) {
            
            if (arco.getId() == arcoN.getId()) {         
                return false;
            }
        }
        arcos.add(arcoN);
        return true;
    }

    public void modificarArco(Arco arco, int id) {
        
        for (int i = 0; i < arcos.size(); i++) {
            if (arcos.get(i).getId() == id) {
                arcos.set(i, arco);
                break;
            }
        }
    }

    public void eliminarArco(Arco arco) {
        
        for (int i = 0; i < arcos.size(); i++) {
            if (arcos.get(i).getId() == arco.getId()) {
                arcos.remove(i);
                break;
            }
        }
    }
    
    // GETTER Y SETTER
    
    public ArrayList<Arco> getArcos() {
        return arcos;
    }

    public void setArcos(ArrayList<Arco> arcos) {
        this.arcos = arcos;
    }

    public ArrayList<Nodo> getNodos() {
        return nodos;
    }

    public void setNodos(ArrayList<Nodo> nodos) {
        this.nodos = nodos;
    }

}
