package isi.died.tp.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import isi.died.tp.dao.PlantaDao;
import isi.died.tp.dao.PlantaDaoH2;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;
import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.GrafoPlantas;
import isi.died.tp.gui.PanelPlanta;

public class ControladorPlantas {

	private static ControladorPlantas _INSTANCIA = null;
	private PanelPlanta pPlanta;
	private GrafoPlantas grafoPlantas;
	private PlantaDao dao;
	
	
	private ControladorPlantas() {
		dao = new PlantaDaoH2();
		grafoPlantas = GrafoPlantas.getInstance();
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
	
	public List<Planta> buscarPlantas(){
		return dao.buscarTodas();
	}
	
	public List<Planta> necesitanInsumo(Insumo i){
		List<Planta> plantas = buscarPlantas();
		return plantas.stream().filter(p->p.necesitaInsumo(i)).collect(Collectors.toList());
	}

	public List<Arista<Planta>> getAristasGrafoPlantas() {
		return grafoPlantas.getAristas();
	}

	public List<List<Planta>> buscarMejorCamino(Insumo i, Boolean priorizarDistancia) {
		List<Planta> plantas = necesitanInsumo(i);
		return grafoPlantas.buscarMejoresCaminos(plantas, priorizarDistancia);
		
	}

	public HashMap<List<Planta>, Double[]> buscarCaminos(Integer idOrigen, Integer idDestino) {
		Planta origen = dao.buscar(idOrigen);
		Planta destino = dao.buscar(idDestino);
		return grafoPlantas.buscarCaminos(origen, destino);
	}
	
	
	
}