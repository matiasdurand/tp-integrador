package isi.died.tp.gui;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class PanelInsumo extends JPanel {
	
	private PanelInsumoControlador controlador;
	
	//agregrar como atributos todos los componentes del panel
	
	
	public PanelInsumo() {
		super();
		controlador = new PanelInsumoControlador(this);
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
