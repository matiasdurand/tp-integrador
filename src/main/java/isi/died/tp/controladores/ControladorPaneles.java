package isi.died.tp.controladores;

import isi.died.tp.gui.PanelCamion;
import isi.died.tp.gui.PanelEditarInsumo;
import isi.died.tp.gui.PanelGrafoView;
import isi.died.tp.gui.PanelInsumo;
import isi.died.tp.gui.PanelPlanta;
import isi.died.tp.gui.PanelRegistrarInsumo;

public class ControladorPaneles {
	
	private static ControladorPaneles _INSTANCIA = null;
	
	private PanelPlanta panelPlanta = null;
	private PanelInsumo panelInsumo = null;
	private PanelCamion panelCamion = null;
	private PanelGrafoView panelGrafoView = null;
	
	private PanelRegistrarInsumo panelRegistrarInsumo = null;
	
	private ControladorPaneles() {
		
	}
	
	public static ControladorPaneles getInstance() {
		if(_INSTANCIA == null) _INSTANCIA = new ControladorPaneles();
		return _INSTANCIA;
	}

	public PanelPlanta getPanelPlanta() {
		if(panelPlanta == null) panelPlanta = new PanelPlanta();
		return panelPlanta;
	}
	
	public PanelInsumo getPanelInsumo() {
		if(panelInsumo == null) panelInsumo = new PanelInsumo();
		return panelInsumo;
	}
	
	public PanelCamion getPanelCamion() {
		if(panelCamion == null) panelCamion = new PanelCamion();
		return panelCamion;
	}	  
	
	public PanelGrafoView getPanelGrafoView() {
		if(panelGrafoView == null) panelGrafoView = new PanelGrafoView();
		return panelGrafoView;
	}	
	
	public PanelRegistrarInsumo getPanelRegistrarInsumo() {
		if(panelRegistrarInsumo == null) panelRegistrarInsumo = new PanelRegistrarInsumo();
		return panelRegistrarInsumo;
	}
	
	public PanelEditarInsumo getPanelEditarInsumo(Integer idInsumo) {
		return new PanelEditarInsumo(idInsumo);
	}
	
}
