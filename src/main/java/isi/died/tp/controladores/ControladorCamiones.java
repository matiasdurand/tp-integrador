package isi.died.tp.controladores;

import isi.died.tp.gui.PanelCamion;

public class ControladorCamiones {

	private static ControladorCamiones _INSTANCIA = null;
	
	private PanelCamion pCamion;
	
	
	private ControladorCamiones() {
		
	}
	
	public static ControladorCamiones getInstance() {
		if(_INSTANCIA == null) _INSTANCIA = new ControladorCamiones();
		return _INSTANCIA;
	}

	public PanelCamion getpCamion() {
		return pCamion;
	}

	public void setpCamion(PanelCamion pCamion) {
		this.pCamion = pCamion;
	}
	
}
