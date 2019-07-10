package isi.died.tp.controladores;

import java.awt.Color;
import java.awt.Font;

import isi.died.tp.gui.PanelCamion;
import isi.died.tp.gui.PanelEditarInsumo;
import isi.died.tp.gui.PanelGrafo;
import isi.died.tp.gui.PanelInsumo;
import isi.died.tp.gui.PanelPlanta;
import isi.died.tp.gui.PanelRegistrarInsumo;

public class ControladorPaneles {
	
	private static ControladorPaneles _INSTANCIA = null;
	
	private PanelPlanta panelPlanta = null;
	private PanelInsumo panelInsumo = null;
	private PanelCamion panelCamion = null;
	private PanelGrafo panelGrafo = null;
	
	private PanelRegistrarInsumo panelRegistrarInsumo = null;
	
	//public static final Font FUENTE_TITULO = new Font("Calibri",Font.BOLD,18);
	//public static final Color COLOR_TITULO = new Color(5,85,244);
	
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
	
	public PanelGrafo getPanelGrafo() {
		if(panelGrafo == null) panelGrafo = new PanelGrafo();
		return panelGrafo;
	}	
	
	public PanelRegistrarInsumo getPanelRegistrarInsumo() {
		if(panelRegistrarInsumo == null) panelRegistrarInsumo = new PanelRegistrarInsumo();
		return panelRegistrarInsumo;
	}
	
	public PanelEditarInsumo getPanelEditarInsumo(Integer idInsumo) {
		return new PanelEditarInsumo(idInsumo);
	}
	
}
