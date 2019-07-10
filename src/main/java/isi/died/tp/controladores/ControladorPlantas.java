package isi.died.tp.controladores;

import isi.died.tp.gui.PanelPlanta;

public class ControladorPlantas {

	private static ControladorPlantas _INSTANCIA = null;
	
	private PanelPlanta pPlanta;
	
	
	private ControladorPlantas() {
		
	}
	
	public static ControladorPlantas getInstance() {
		if(_INSTANCIA == null) _INSTANCIA = new ControladorPlantas();
		return _INSTANCIA;
	}

	public PanelPlanta getpPlanta() {
		return pPlanta;
	}

	public void setpPlanta(PanelPlanta pPlanta) {
		this.pPlanta = pPlanta;
	}
	
}
