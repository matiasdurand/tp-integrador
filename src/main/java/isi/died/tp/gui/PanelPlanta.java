package isi.died.tp.gui;

import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import isi.died.tp.controladores.ControladorPlantas;

public class PanelPlanta extends JPanel {
	
	private ControladorPlantas controlador;
	
	//agregrar como atributos todos los componentes de la interfaz
	
	
	public PanelPlanta() {
		super();
		controlador = ControladorPlantas.getInstance();
		controlador.setpPlanta(this);
		armar();
		configurarEventos();
		
	}
	
	private void armar() {
		
		setLayout(new GridBagLayout());
		
		//configurar la apariencia del panel, como se veran sus componentes
	
	}
	
	private void configurarEventos() {
		
		//configurar los eventos de todos los botones
		
	}
	
}
