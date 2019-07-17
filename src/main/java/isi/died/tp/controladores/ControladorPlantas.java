package isi.died.tp.controladores;

import java.util.List;
import java.util.stream.Collectors;

import isi.died.tp.dao.PlantaDao;
import isi.died.tp.dao.PlantaDaoH2;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;
import isi.died.tp.gui.PanelPlanta;

public class ControladorPlantas {

	private static ControladorPlantas _INSTANCIA = null;
	private PlantaDao dao;
	private PanelPlanta pPlanta;
	
	
	
	private ControladorPlantas() {
		dao = new PlantaDaoH2();
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
	
	public List<Planta> buscarTodas(){
		return dao.buscarTodas();
	}
	
	public List<Planta> necesitanInsumo(Insumo i){
		List<Planta> listaPlantas = dao.buscarTodas();
		return listaPlantas.stream().filter( p -> p.necesitaInsumo(i)).collect(Collectors.toList());
	}
	
	
	
}
