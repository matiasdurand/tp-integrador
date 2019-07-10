package isi.died.tp.gui;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import isi.died.tp.controladores.ControladorCamiones;

public class PanelCamion extends JPanel {

	private ControladorCamiones controlador;
	
	//agregrar como atributos todos los componentes de la interfaz
	
	
	public PanelCamion() {
		super();
		controlador = ControladorCamiones.getInstance();
		controlador.setpCamion(this);
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
