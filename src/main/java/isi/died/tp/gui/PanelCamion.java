package isi.died.tp.gui;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class PanelCamion extends JPanel {

	private PanelCamionControlador controlador;
	
	//agregrar como atributos todos los componentes de la interfaz
	
	
	public PanelCamion() {
		super();
		controlador = new PanelCamionControlador(this);
		this.armar();
		this.configurarEventos();
		
	}
	
	private void armar() {
		
		setLayout(new GridBagLayout());
		
		//configurar la apariencia del panel, como se veran sus componentes
	
	}
	
	private void configurarEventos() {
		
		//configurar los eventos de todos los botones
		
	}
}
