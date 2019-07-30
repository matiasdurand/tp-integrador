package isi.died.tp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
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
		
    	GridBagConstraints c = new GridBagConstraints();   
    	
		gtm = crearModeloTabla();
    	tablaPlantas = new JTable(gtm);
    	tablaPlantas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JScrollPane scrollPane = new JScrollPane(tablaPlantas);
    	c.gridx=0;
    	c.gridy=2;
    	add(scrollPane, c);
    	
    	
    	btnFlujoMaximo = new JButton("CALCULAR FLUJO MAXIMO");
    	c.gridx=0;
    	c.gridy=3;
    	add(btnFlujoMaximo, c);
    	
    	flujoMaximo = new JTextField(10);
    	c.gridx=1;
    	c.gridy=3;
    	add(flujoMaximo, c);
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
