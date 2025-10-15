 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Objetos.Arco;
import Objetos.Nodo;
import Objetos.Plano;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JPanelPlano extends JPanel implements MouseListener, MouseMotionListener {

    private Plano plano;
    private int estado = 1;

    private Point mousePos1;
    private Point mousePos2;

    private int nodo1;
    private int nodo2;

    // constructor
    public JPanelPlano() {
        addMouseMotionListener(this);
        addMouseListener(this);

        this.plano = new Plano();
        setBackground(Color.white);
        setLocation(155, 20);
        setSize(795, 565);
    }

    public void actualizarPlano(Plano plano) {
        this.plano = plano;
        repaint();
    }

    public Plano getPlano() {
        return plano;
    }

    public int getModo() {
        return estado;
    }

    public void cambiarAccion(int estado) {
        this.estado = estado;

        mousePos1 = null;
        repaint();
    }

    //--------------------------------------------------------------------------
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        //--PINTANDO CELDAS-----------------------------------------------------
        g.setColor(Color.lightGray);
        
        for (int x = 0; x < 90; x++) {
            g.drawLine(x * 20 + 1, 0, x * 20 + 1, 1000);
        }
        for (int y = 0; y < 90; y++) {
            g.drawLine(0, y * 20 + 1, 1000, y * 20 + 1);
        }
         
        //--MOUSE ALZADO--------------------------------------------------------
        if (mousePos1 != null) {
            if (estado == 1) {

                g.setColor(Color.black);
                g.drawOval(mousePos1.x - 30 / 2, mousePos1.y - 30 / 2, 30, 30);

            } else if (estado == 2) {

                if (mousePos2 != null) {
                    g.drawLine(mousePos1.x, mousePos1.y, mousePos2.x, mousePos2.y);
                }
            }
        }

        //--PINTANDO ARCOS------------------------------------------------------
        if (!plano.getArcos().isEmpty()) {

            double anguloEntreP, radians, mag, m;
            Point p = new Point();

            try {

                for (Arco arco : plano.getArcos()) {

                    anguloEntreP = 90;

                    Nodo nodoOrigen = plano.buscarNodo(arco.getNodoOrigen());
                    Nodo nodoDestino = plano.buscarNodo(arco.getNodoDestino());

                    if (nodoDestino.getX() != nodoOrigen.getX()) {
                        m = (double) (nodoDestino.getY() - nodoOrigen.getY()) / (double) (nodoDestino.getX() - nodoOrigen.getX());
                        anguloEntreP = Math.toDegrees(Math.atan(m));
                    }

                    radians = Math.abs(Math.toRadians(anguloEntreP));

                    mag = Math.sqrt(Math.pow(nodoOrigen.getX() - nodoDestino.getX(), 2) + Math.pow(nodoOrigen.getY() - nodoDestino.getY(), 2)) - 50;

                    if (nodoOrigen.getY() < nodoDestino.getY()) {
                        if (nodoOrigen.getX() < nodoDestino.getX()) {
                            p.y = (int) (nodoOrigen.getY() + mag * Math.sin(radians));
                            p.x = (int) (nodoOrigen.getX() + mag * Math.cos(radians));
                        } else {
                            p.y = (int) (nodoOrigen.getY() + mag * Math.sin(radians));
                            p.x = (int) (nodoOrigen.getX() - mag * Math.cos(radians));
                        }
                    } else {
                        if (nodoOrigen.getX() < nodoDestino.getX()) {
                            p.y = (int) (nodoOrigen.getY() - mag * Math.sin(radians));
                            p.x = (int) (nodoOrigen.getX() + mag * Math.cos(radians));
                        } else {
                            p.y = (int) (nodoOrigen.getY() - mag * Math.sin(radians));
                            p.x = (int) (nodoOrigen.getX() - mag * Math.cos(radians));
                        }
                    }
                    
                    g.setColor(Color.black);
                    if(arco.getTipo() == 2){
                        
                        Graphics2D g2d= (Graphics2D) g;
                        float guiones[]={10,5};
                        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,0,guiones,0));
                        g2d.drawLine(nodoOrigen.getX(), nodoOrigen.getY(), p.x, p.y);
                        g2d.setStroke(new BasicStroke());
                    }
                    else{
                        g.drawLine(nodoOrigen.getX(), nodoOrigen.getY(), p.x, p.y);
                    }
                    

                    if (arco.getTipo() == 1) {    // MOSTRAR PESO
                        int xPintar = (nodoOrigen.getX() + nodoDestino.getX()) / 2;
                        int yPintar = (nodoOrigen.getY() + nodoDestino.getY()) / 2;

                        g.setColor(new Color(126, 24, 3));
                        g.setFont(new Font("Century", 0, 20));

                        g.drawString(arco.getPeso() + "", xPintar, yPintar);
                    }

                }
            } catch (Exception e) {
            }

        }
        //--PINTANDO NODOS------------------------------------------------------
        if (plano.getNodos() != null) {
            for (Nodo nodo : plano.getNodos()) {
                
                double angulo = 90 + 180, radians = 0, dimension = 50;
                
                char letra = '0';
                if (nodo.getTipo() == 1) {
                    
                    letra = 'A';
                    int vx[] = new int[3], vy[] = new int[3];

                    for (int i = 0; i < 3; i++) {
                        radians = Math.abs(Math.toRadians(angulo));
                        vx[i] = (int) ((int) nodo.getX() + (int) dimension * Math.cos(radians));
                        vy[i] = (int) ((int) nodo.getY() + (int) dimension * Math.sin(radians));
                        angulo += 120;
                    }

                    g.setColor(Color.white);
                    g.fillPolygon(new Polygon(vx, vy, 3));
                    g.setColor(Color.black);
                    g.drawPolygon(new Polygon(vx, vy, 3));

                    g.setFont(new Font("Century", 0, 20));
                    g.drawString("$" + nodo.getCosto(), (int) nodo.getX() - 30, (int) nodo.getY() - 60);

                } else if (nodo.getTipo() == 2) {

                    letra = 'C';
                    int vx[] = new int[5], vy[] = new int[5];
                    for (int i = 0; i < 5; i++) {
                        radians = Math.abs(Math.toRadians(angulo));
                        vx[i] = (int) (nodo.getX() + dimension * Math.cos(radians));
                        vy[i] = (int) (nodo.getY() + dimension * Math.sin(radians));
                        angulo += 72;
                    }
                    g.setColor(Color.white);
                    g.fillPolygon(new Polygon(vx, vy, 5));
                    g.setColor(Color.black);
                    g.drawPolygon(new Polygon(vx, vy, 5));

                } else if (nodo.getTipo() == 3) {

                    letra = 'R';
                    g.setColor(Color.white);
                    g.fillOval((int) nodo.getX() - 100 / 2, (int) nodo.getY() - 100 / 2, 100, 100);
                    g.setColor(Color.black);
                    g.drawOval((int) nodo.getX() - 100 / 2, (int) nodo.getY() - 100 / 2, 100, 100);

                } else if (nodo.getTipo() == 4) {

                    letra = 'G';
                    g.setColor(Color.white);
                    g.fillRect((int) nodo.getX() - 100 / 2, (int) nodo.getY() - 100 / 2, 100, 100);
                    g.setColor(Color.black);
                    g.drawRect((int) nodo.getX() - 100 / 2, (int) nodo.getY() - 100 / 2, 100, 100);
                }

                g.setFont(new Font("Century", 0, 20));
                g.drawString(letra + "", (int) nodo.getX() - 8, (int) nodo.getY() + 8);

            }

        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {

        //--MOVER NODO----------------------------------------------------------
        if (estado == 3) {

            Nodo nodoC = plano.buscarNodo(me.getPoint());
            if (nodoC != null) {

                mousePos1 = me.getPoint();
                plano.moverNodo(nodoC, mousePos1);
                repaint();
            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent me) {

        if (estado == 1) {
            mousePos1 = me.getPoint();
            repaint();
        } else if (estado == 2) {

            if (mousePos1 != null) {

                mousePos2 = me.getPoint();
                repaint();
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent me) {

        if (estado == 1) {
            mousePos1 = me.getPoint();

            if (plano.buscarNodo(mousePos1) == null) {

                try {
                    int id, tipo;
                    float costo = 0;

                    id = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite Identificador Unico: "));
                    tipo = Integer.parseInt(JOptionPane.showInputDialog(this, "Tipo...\n1) Agents \n2) Cababilities \n3) Roles \n4) Goals \nDigite Tipo elemento (1-4): "));

                    if (tipo == 1) { // TRIANGULOS
                        costo = Float.parseFloat(JOptionPane.showInputDialog(this, "Digite Costo: "));
                    }

                    Nodo nuevoN = new Nodo(id, costo, tipo, mousePos1.x, mousePos1.y);
                    plano.adicionarNodo(nuevoN);
                    JOptionPane.showMessageDialog(this, "Nodo agregado con exito");

                } catch (HeadlessException | NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Error datos no validos");
                }

                estado = 0;
            }
        } else if (estado == 2) {

            Nodo nodoC = plano.buscarNodo(me.getPoint());
            if (nodoC != null) {

                if (mousePos1 == null) {

                    nodo1 = nodoC.getId();
                    mousePos1 = me.getPoint();

                } else {

                    nodo2 = nodoC.getId();
                    mousePos2 = me.getPoint();

                    int tipo = 0;
                    Nodo aux1 = plano.buscarNodo(nodo1), aux2 = plano.buscarNodo(nodo2);
                    if ((aux1.getTipo() == 1 && aux2.getTipo() == 2)
                            || (aux1.getTipo() == 2 && aux2.getTipo() == 1)) {
                        tipo = 1;
                    } else if ((aux1.getTipo() == 2 && aux2.getTipo() == 3)
                            || (aux1.getTipo() == 3 && aux2.getTipo() == 2)) {
                        tipo = 2;
                    } else if ((aux1.getTipo() == 3 && aux2.getTipo() == 4)
                            || (aux1.getTipo() == 4 && aux2.getTipo() == 3)) {
                        tipo = 3;
                    }

                    if (tipo != 0) {
                        try {
                            int id;
                            float peso = 0;

                            id = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite Identificador Unico: "));

                            if (tipo == 1) {
                                peso = Float.parseFloat(JOptionPane.showInputDialog(this, "Digite el peso: "));
                            }

                            Arco arcoN = new Arco(id, peso, tipo, nodo1, nodo2);
                            Arco arc = plano.buscarArco(id);
                            if (arc == null) {
                                plano.adicionarArco(arcoN);
                                JOptionPane.showMessageDialog(this, "Arco agregado con exito");
                            } else {
                                JOptionPane.showMessageDialog(this, "Arco ya existe");
                            }

                        } catch (HeadlessException | NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Error datos no validos");
                        }
                    }

                    estado = 0;
                }
            }
        } else if (estado == 4) {

            Nodo nodoC = plano.buscarNodo(me.getPoint());
            if (nodoC != null) {

                try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite Identificador del nodo: "));

                    if (plano.buscarNodo(id) == null) {

                        float costo = 0;
                        int tipo = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite Tipo elemento (1-4): "));

                        if (tipo == 1) { // TRIANGULOS
                            costo = Float.parseFloat(JOptionPane.showInputDialog(this, "Digite Costo: "));
                        }

                        Nodo nuevoN = new Nodo(id, costo, tipo, nodoC.getX(), nodoC.getY());

                        plano.modificarNodo(nuevoN, nodoC.getId());

                        for (int i = 0; i < plano.getArcos().size(); i++) {
                            if (plano.getArcos().get(i).getNodoDestino() == nodoC.getId()) {
                                plano.getArcos().get(i).setNodoDestino(id);
                            }
                            if (plano.getArcos().get(i).getNodoOrigen() == nodoC.getId()) {
                                plano.getArcos().get(i).setNodoOrigen(id);
                            }
                        }

                        JOptionPane.showMessageDialog(null, "nodo modificado con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "id existente, no puede ser creado");
                    }

                } catch (HeadlessException | NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Error datos no validos");
                }
                estado = 0;
            }
        } else if (estado == 5) {

            Nodo nodoC = plano.buscarNodo(me.getPoint());
            if (nodoC != null) {

                JOptionPane.showMessageDialog(this, "Nodo Eliminado");
                plano.eliminarNodo(nodoC);
                repaint();

            }
        } else if (estado == 6) {

            Arco arcoC = plano.buscarArco(me.getPoint());
            if (arcoC != null) {

                try {
                    int id, tipo = arcoC.getTipo();
                    float peso = 0;

                    id = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite Identificador Unico: "));

                    if (tipo == 1) {
                        peso = Float.parseFloat(JOptionPane.showInputDialog(this, "Digite el peso: "));
                    }

                    Arco nuevoA = new Arco(id, peso, tipo, arcoC.getNodoOrigen(), arcoC.getNodoDestino());

                    Arco arc = plano.buscarArco(id);
                    if (arc == null) {
                        plano.modificarArco(nuevoA, arcoC.getId());
                        JOptionPane.showMessageDialog(this, "Arco modificado con exito");
                    } else {
                        JOptionPane.showMessageDialog(this, "Arco ya existe");
                    }

                } catch (HeadlessException | NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Error datos no validos");
                }
                estado = 0;
            }
        } else if (estado == 7) {

            Arco arcoC = plano.buscarArco(me.getPoint());
            if (arcoC != null) {

                JOptionPane.showMessageDialog(this, "Arco Eliminado");
                plano.eliminarArco(arcoC);
                repaint();
            }
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
