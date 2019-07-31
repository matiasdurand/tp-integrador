package isi.died.tp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import isi.died.tp.controladores.ControladorGrafoView;
import isi.died.tp.dominio.Insumo;

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
	private JLabel lblPanelTitulo;
	private JLabel lblInsumo;
	private JLabel lblPriorizar;
	
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
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints(); 
		
		int fila = 0;
    	int col = 0; 	

    	this.lblPanelTitulo = new JLabel("GRAFO DE PLANTAS");
    	this.lblPanelTitulo.setFont(ControladorGrafoView.FUENTE_TITULO);
    	this.lblPanelTitulo.setForeground(ControladorGrafoView.COLOR_TITULO);
    	c.gridx=col;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.NORTH;
    	c.gridwidth=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblPanelTitulo,c);
		
    	//Primer fila
    	
    	
		col=0;
    	fila++;
		
		JPanel panelFila2 = new JPanel();
		
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=5;
    	c.gridheight=2;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weighty=1.0;
    	c.weightx=1.0;
		
    	this.add(panelFila2,c);
    	panelFila2.setOpaque(false);

		//Segunda fila
    	
    	col=0;
    	fila+=2;
    	    	
    	JPanel panelFila1 = new JPanel();
    	
    	this.lblInsumo = new JLabel("Seleccione un insumo:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.NORTH;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	panelFila1.add(lblInsumo,c);
    	
		cmboxInsumo = new JComboBox<Insumo>();
		controlador.cargarComboInsumos(cmboxInsumo);
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.5;
    	c.anchor = GridBagConstraints.NORTH;
    	c.fill=GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(10, 10, 10, 10);
		panelFila1.add(cmboxInsumo,c);
		
		btnMostrarCaminos = new JButton("Mostrar caminos");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.NORTH;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
		btnMostrarCaminos.setEnabled(false);
		panelFila1.add(btnMostrarCaminos,c);
		
		btnMostrarInfo = new JButton("Mostrar información");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.NORTH;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
		panelFila1.add(btnMostrarInfo,c);
		
		lblPriorizar = new JLabel("Priorizar:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.NORTH;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	panelFila1.add(lblPriorizar,c);
		
		ButtonGroup grupo = new ButtonGroup();
		rbtnDistancia = new JRadioButton("Distancia");
		rbtnTiempo = new JRadioButton("Duración");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.NORTH;
    	c.fill=GridBagConstraints.NONE;
    	c.weightx=0.0;
    	rbtnDistancia.setSelected(true);
    	grupo.add(rbtnDistancia);
    	grupo.add(rbtnTiempo);
    	
    	panelFila1.add(rbtnDistancia,c);
    	panelFila1.add(rbtnTiempo,c);	
    	
		this.add(panelFila1,c);
    	
    	//Tercera fila
    	
    	col=0;
    	fila++;
    	
		textArea = new JTextArea();
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=5;
    	c.gridheight=1;
    	c.weightx=1.0;
    	c.weighty=1.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.insets = new Insets(10, 10, 10, 10);
		this.add(textArea,c);

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
		cmboxInsumo.removeAllItems();
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
