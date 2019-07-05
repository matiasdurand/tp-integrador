package isi.died.tp.gui;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class PanelPlanta extends JPanel {
	
	private PanelPlantaControlador controlador;
	
	//agregrar como atributos todos los componentes de la interfaz
	
	
	public PanelPlanta() {
		super();
		controlador = new PanelPlantaControlador(this);
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
