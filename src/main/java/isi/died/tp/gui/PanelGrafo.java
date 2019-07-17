package isi.died.tp.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import isi.died.tp.controladores.ControladorGrafo;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;

public class PanelGrafo extends JPanel {
	
	private JComboBox<Insumo> cmboxInsumo;
	private JTextArea textArea;
	private JRadioButton rbtnDistancia;
	private JRadioButton rbtnTiempo;
	private JButton btnNecesitanInsumo;
	private JButton btnMejorCamino;
    private List<VerticeView> vertices;
    private List<AristaView> aristas;
	private ControladorGrafo controlador;
	private VerticeView verticeSeleccionado;
	private List<AristaView> aristasAMover;
	
	public PanelGrafo() {
		super();
		controlador = ControladorGrafo.getInstance();
		controlador.setpGrafo(this);
		vertices = new ArrayList<>();
        aristas = new ArrayList<>();
        aristasAMover = new ArrayList<>();
		controlador.inicializarGrafo();
		armar();
		configurarEventos();
	}
	
	private void armar() {
		//CONFIGURAR APARIENCIA DEL PANEL.
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();  
		cmboxInsumo = new JComboBox<Insumo>();
		controlador.cargarComboInsumos(cmboxInsumo);
		c.gridx=0;
		c.gridy=3;
		add(cmboxInsumo,c);
		
		btnNecesitanInsumo = new JButton("NECESITAN INSUMO");
		c.gridx=1;
		c.gridy=3;
		add(btnNecesitanInsumo,c);
		
	}
	
	private void configurarEventos() {
		addMouseListener(new MouseAdapter() {
            /*public void mouseClicked(MouseEvent event) {
                if (event.getClickCount()==2 && !event.isConsumed()) {
                    event.consume();
                    VerticeView v = clicEnUnNodo(event.getPoint());
                    if(v!=null) {
                    	verticeSeleccionado = v; 
                    	verticeSeleccionado.setColor(Color.CYAN);
                    	//actualizarVertice(verticeSeleccionado, event.getPoint());
                    }
                    System.out.println("DOBLE CLICK");
                }
            }*/
			public void mousePressed(MouseEvent event) {
				System.out.println("click en "+event.getPoint());
				VerticeView v = clicEnUnNodo(event.getPoint());
				if(v!=null) {
					verticeSeleccionado = v;
					verticeSeleccionado.setColor(Color.YELLOW);
					for(AristaView a: aristas) if(a.getOrigen().equals(verticeSeleccionado)||a.getDestino().equals(verticeSeleccionado)) aristasAMover.add(a);
				}   
			}
			public void mouseReleased(MouseEvent event) {
               	verticeSeleccionado = null;
               	aristasAMover.clear();
			}
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent event) {
                if(verticeSeleccionado!=null) {
                	actualizarVertice(verticeSeleccionado, event.getPoint());
                	for(AristaView a: aristasAMover) actualizarArista(a, verticeSeleccionado);
                	repaint();
                }
            }
        });
        btnNecesitanInsumo.addActionListener( e -> {
        	pintarVerticesColorOriginal();
        	pintarVertices(controlador.verticesAPintar((Insumo)cmboxInsumo.getSelectedItem()));
        	repaint();
        });
        /*btnMejorCamino.addActionListener( e -> {
        	controlador.obtenerMejorCamino((Insumo)cmboxInsumo.getSelectedItem(), rbtnDistancia.isSelected());
        });*/
        
	}
	
	public void agregar(AristaView arista){
        this.aristas.add(arista);
    }    
    
    public void agregar(VerticeView vert){
        this.vertices.add(vert);
    }
    
    protected void paintComponent(Graphics g) {    	
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        dibujarVertices(g2d);
        dibujarAristas(g2d);
    }
    
    private void dibujarVertices(Graphics2D g2d) {
        for (VerticeView v : vertices) {
        	g2d.setPaint(Color.BLACK);
            g2d.drawString(v.etiqueta(),v.getCoordenadaX(),v.getCoordenadaY());
            g2d.setPaint(v.getColor());
            g2d.fill(v.getNodo());
        }
    }
    
    private void dibujarAristas(Graphics2D g2d) {
        for (AristaView a: aristas) {
            g2d.setPaint(a.getColor());
            g2d.setStroke(a.getFormatoLinea());
            g2d.draw(a.getLinea());
        }
    }
    
    private VerticeView clicEnUnNodo(Point p) {
        for (VerticeView v: vertices) if (v.getNodo().contains(p)) return v;
        return null;
    }
    
    private void actualizarVertice(VerticeView v, Point puntoNuevo) {
        v.setCoordenadaX(puntoNuevo.x-25);
        v.setCoordenadaY(puntoNuevo.y-25);
        v.update();
    }
    
    private void actualizarArista(AristaView a, VerticeView v){
    	if(a.getOrigen().equals(v)) a.setOrigen(v);
    	else a.setDestino(v);
    	a.update();
    }
    
    private void pintarVertices(List<Integer> idVerticesView) {
    	for(VerticeView v: vertices) if(idVerticesView.contains(v.getId())) v.setColor(Color.RED);
    }
    
	private void pintarVerticesColorOriginal() {
		for(VerticeView v: vertices) v.setColor(Color.YELLOW);
	}
}
