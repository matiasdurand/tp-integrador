package isi.died.tp.gui;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import isi.died.tp.controladores.ControladorGrafo;

public class PanelGrafo extends JPanel {
	
	private ControladorGrafo controlador;
	
	//agregrar como atributos todos los componentes de la interfaz
	
	
	public PanelGrafo() {
		super();
		controlador = ControladorGrafo.getInstance();
		controlador.setpGrafo(this);
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
