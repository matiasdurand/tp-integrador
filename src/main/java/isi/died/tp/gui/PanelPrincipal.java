package isi.died.tp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import isi.died.tp.controladores.ControladorPlantas;
import isi.died.tp.dominio.Planta;
import isi.died.tp.gui.util.GenericTableColumn;
import isi.died.tp.gui.util.GenericTableModel;

public class PanelPrincipal extends JPanel {

	private JTextField flujoMaximo;
	private JButton btnFlujoMaximo;
	private JButton btnMejorSeleccionEnvio;
	GenericTableModel<Planta> gtm ;
	private JTable tablaPlantas;
	
	private ControladorPlantas controlador;
	private JLabel lblPanelTitulo;
	private JLabel lblListaPlantas;
	
	
	public PanelPrincipal() {
		super();
		controlador = ControladorPlantas.getInstance();
		controlador.setpPrincipal(this);
		armar();
		configurarEventos();
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(1000,750);
    }
	
	public void armar() {
		
		setLayout(new GridBagLayout());
		int fila = 0;
    	int col = 0;
    	
    	GridBagConstraints c = new GridBagConstraints();    	
    	this.lblPanelTitulo = new JLabel("SISTEMA DE GESTIÓN LOGÍSTICA");
    	this.lblPanelTitulo.setFont(ControladorPlantas.FUENTE_TITULO_PRINCIPAL);
    	this.lblPanelTitulo.setForeground(ControladorPlantas.COLOR_TITULO);
    	c.gridx=col;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.NORTH;
    	c.gridwidth=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblPanelTitulo,c);
    	
    	//Primera fila
    	
    	col=0;
    	fila++;
    	
    	this.lblListaPlantas = new JLabel("Plantas principales");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.HORIZONTAL;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblListaPlantas,c);
    	
    	//Segunda fila
    	
    	col=0;
    	fila++;
    	
		gtm = crearModeloTabla();
    	tablaPlantas = new JTable(gtm);
    	tablaPlantas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JScrollPane scrollPane = new JScrollPane(tablaPlantas);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weighty=1.0;
    	c.weightx=1.0;
    	add(scrollPane, c);
    	
    	//Tercera fila
    	
    	col=0;
    	fila++;
    	
    	JPanel panelFila3 = new JPanel();
    	    	
    	btnFlujoMaximo = new JButton("Calcular flujo máximo");
    	c.fill=GridBagConstraints.HORIZONTAL;
    	c.anchor=GridBagConstraints.CENTER;
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=3;
    	c.weighty=0.0;
    	c.weightx=0.0;
    	c.insets = new Insets(10, 10, 10, 10);
    	panelFila3.add(btnFlujoMaximo, c);
    	
    	flujoMaximo = new JTextField(10);
    	panelFila3.add(flujoMaximo, c);
    	
    	this.add(panelFila3,c);
	}
	
	public void configurarEventos() {
		
		btnFlujoMaximo.addActionListener(e -> {
			controlador.calcularFlujoMaximo();
			
		});
		
	}
	
	public void pageRank() {
		actualizarTablaPlantas(controlador.pageRank());
	}

	private GenericTableModel<Planta> crearModeloTabla(){
    	gtm = new GenericTableModel<Planta>();
    	List<GenericTableColumn> lista = new ArrayList<GenericTableColumn>();
    	lista.add(new GenericTableColumn("Id" , "getId"));
    	lista.add(new GenericTableColumn("Nombre" , "getNombre"));
    	gtm.setColumnas(lista);
    	return gtm;
    }
	
	public void actualizarTablaPlantas(List<Planta> lista) {
		this.gtm.setDatos(lista);
    	this.gtm.fireTableDataChanged();
	}

	public void mostrarFlujoMaximo(Double flujoMaximo) {
		this.flujoMaximo.setText(flujoMaximo.toString()+" KG");
	}
}
