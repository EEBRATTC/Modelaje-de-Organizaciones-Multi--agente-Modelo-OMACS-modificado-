/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;


public class Arco {
    
    private int id;
    private float peso;
    private int tipo;
    
    private int nodoOrigen;
    private int nodoDestino;

    public Arco() {
    }

    public Arco(int id, float peso, int tipo, int nodoOrigen, int nodoDestino) {
        this.id = id;
        this.peso = peso;
        this.tipo = tipo;
        this.nodoOrigen = nodoOrigen;
        this.nodoDestino = nodoDestino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float costo) {
        this.peso = costo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getNodoOrigen() {
        return nodoOrigen;
    }

    public void setNodoOrigen(int nodoOrigen) {
        this.nodoOrigen = nodoOrigen;
    }

    public int getNodoDestino() {
        return nodoDestino;
    }

    public void setNodoDestino(int nodoDestino) {
        this.nodoDestino = nodoDestino;
    }

   
}
