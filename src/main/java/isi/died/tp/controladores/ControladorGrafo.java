package isi.died.tp.controladores;

import isi.died.tp.gui.PanelGrafo;

public class ControladorGrafo {

	private static ControladorGrafo _INSTANCIA = null;

	private PanelGrafo pGrafo;
	
	
	private ControladorGrafo() {
		
	}
	
	public static ControladorGrafo getInstance() {
		if(_INSTANCIA == null) _INSTANCIA = new ControladorGrafo();
		return _INSTANCIA;
	}
	
	public PanelGrafo getpGrafo() {
		return pGrafo;
	}

	public void setpGrafo(PanelGrafo pGrafo) {
		this.pGrafo = pGrafo;
	}
	
}
