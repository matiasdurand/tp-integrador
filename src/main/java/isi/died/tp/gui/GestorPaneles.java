package isi.died.tp.gui;

import java.awt.Color;
import java.awt.Font;

public class GestorPaneles {
	
	private static GestorPaneles _INSTANCIA = null;
	private PanelPlanta panelPlanta = null;
	private PanelInsumo panelInsumo = null;
	private PanelCamion panelCamion = null;
	private PanelGrafo panelGrafo = null;
	
	//public static final Font FUENTE_TITULO = new Font("Calibri",Font.BOLD,18);
	//public static final Color COLOR_TITULO = new Color(5,85,244);
	
	private GestorPaneles() {
		
	}
	
	public static GestorPaneles getInstance() {
		if(_INSTANCIA == null) _INSTANCIA = new GestorPaneles();
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
}
