package isi.died.tp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;

import isi.died.tp.controladores.ControladorGrafoView;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.gui.util.GenericTableModel;

public class PanelGrafoView extends JPanel {
	
	private JComboBox<Insumo> cmboxInsumo;
	private JTextArea textArea;
	private JRadioButton rbtnDistancia;
	private JRadioButton rbtnTiempo;
	private JButton btnMostrarInfo;
	private JButton btnMostrarCaminos;
    private List<VerticeView> vertices;
    private List<AristaView> aristas;
	private ControladorGrafoView controlador;
	private VerticeView verticeSeleccionado;
	private List<AristaView> aristasAMover;
	private int contador = 0;
	private Integer idNodoInicio = -1;
	private Integer idNodoFin = -1;
	
	public PanelGrafoView() {
		super();
		controlador = ControladorGrafoView.getInstance();
		controlador.setpGrafo(this);
		vertices = new ArrayList<>();
        aristas = new ArrayList<>();
        aristasAMover = new ArrayList<>();
		armar();
		configurarEventos();
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(1000,750);
    }
	
	public void inicializarGrafoView() {
		vertices.clear();
		aristas.clear();
		controlador.inicializarGrafoView();
	}
	
	private void armar() {
		//CONFIGURAR APARIENCIA DEL PANEL.
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();  
		
		cmboxInsumo = new JComboBox<Insumo>();
		c.gridx=0;
		c.gridy=2;
		add(cmboxInsumo,c);
		
		btnMostrarInfo = new JButton("MOSTRAR INFORMACION");
		c.gridx=0;
		c.gridy=3;
		add(btnMostrarInfo,c);
		
		textArea = new JTextArea();
		c.gridx=1;
		c.gridy=4;
		add(textArea,c);

		btnMostrarCaminos = new JButton("MOSTRAR CAMINOS");
		c.gridx=0;
		c.gridy=5;
		btnMostrarCaminos.setEnabled(false);
		add(btnMostrarCaminos,c);
		
	}
	
	private void configurarEventos() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				verticeSeleccionado = clicEnUnNodo(event.getPoint());
				if(verticeSeleccionado!=null) {
					for(AristaView a: aristas) if(a.getOrigen().equals(verticeSeleccionado)||a.getDestino().equals(verticeSeleccionado)) aristasAMover.add(a);
				}   
			}
			public void mouseReleased(MouseEvent event) {
               	verticeSeleccionado = null;
               	aristasAMover.clear();
			}
			public void mouseClicked(MouseEvent event) {
				verticeSeleccionado = clicEnUnNodo(event.getPoint());
				if(verticeSeleccionado!=null) {
					
					if(contador==0) {
						verticeSeleccionado.setColor(Color.GREEN);
						idNodoInicio = verticeSeleccionado.getId();
						contador++;
						repaint();
					}
					else {
						if(contador==1) {
							if(verticeSeleccionado.getId()==idNodoInicio) {
								verticeSeleccionado.setColor(Color.YELLOW);
								idNodoInicio = -1;
								contador = 0;
								repaint();
							}
							else {
								verticeSeleccionado.setColor(Color.GREEN);
								idNodoFin = verticeSeleccionado.getId();
								contador++;
								repaint();
							}
						}
						else {
							if(verticeSeleccionado.getId()==idNodoInicio) {
								verticeSeleccionado.setColor(Color.YELLOW);
								idNodoInicio = idNodoFin;
								idNodoFin = -1;
								contador--;
								repaint();
							}
							else {
								if(verticeSeleccionado.getId()==idNodoFin) {
									verticeSeleccionado.setColor(Color.YELLOW);
									idNodoFin = -1;
									contador--;
									repaint();
								}
							}
						}
					}
				}
				if(contador==2) btnMostrarCaminos.setEnabled(true);
				else btnMostrarCaminos.setEnabled(false);
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
        btnMostrarInfo.addActionListener( e -> {
        		textArea.setText("");
        		pintarVertices(Color.YELLOW);
        		pintarVertices(controlador.verticesAPintar((Insumo)cmboxInsumo.getSelectedItem()), Color.RED);
        		repaint();
        		//controlador.buscarMejorCamino((Insumo)cmboxInsumo.getSelectedItem(), rbtnDistancia.isSelected());
        });
        
        btnMostrarCaminos.addActionListener( e -> {
        	textArea.setText("");
        	controlador.buscarCaminos(idNodoInicio, idNodoFin);
        });
	}
	
	public void cargarComboInsumos() {
		controlador.cargarComboInsumos(cmboxInsumo);
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
            g2d.drawString(a.etiqueta(), (a.getOrigen().getCoordenadaX()+a.getDestino().getCoordenadaX())/2, (a.getOrigen().getCoordenadaY()+a.getDestino().getCoordenadaY())/2);
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
    
    private void pintarVertices(List<Integer> idVertices, Color color) {
    	for(VerticeView v: vertices) if(idVertices.contains(v.getId())) v.setColor(color);
    }
    
	private void pintarVertices(Color color) {
		for(VerticeView v: vertices) v.setColor(color);
	}

	public void mostrarInfo(String camino) {
		if(textArea.getText().isEmpty()) textArea.setText(camino);
		else textArea.append(camino);
	}


}
