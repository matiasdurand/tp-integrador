package isi.died.tp.controladores;

import java.awt.Color;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import isi.died.tp.aplicacion.Aplicacion;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.Stock;
import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.Grafo;
import isi.died.tp.dominio.Insumo.UnidadDeMedida;
import isi.died.tp.gui.AristaView;
import isi.died.tp.gui.PanelGrafoView;
import isi.died.tp.gui.VerticeView;

public class ControladorGrafoView {

	private static ControladorGrafoView _INSTANCIA = null;
	private PanelGrafoView pGrafo;
	private ControladorPlantas controladorPlantas;
	private ControladorInsumos controladorInsumos;
	
	private ControladorGrafoView() {
		//NO SE PUEDE INSTANCIAR FUERA DE ESTE AMBITO.
		controladorPlantas = ControladorPlantas.getInstance();
		controladorInsumos = ControladorInsumos.getInstance();
	}
	
	public static ControladorGrafoView getInstance() {
		if(_INSTANCIA == null) _INSTANCIA = new ControladorGrafoView();
		return _INSTANCIA;
	}
	
	public PanelGrafoView getpGrafo() {
		return pGrafo;
	}

	public void setpGrafo(PanelGrafoView pGrafo) {
		this.pGrafo = pGrafo;
	}
	
	public void inicializarGrafoView() {
		Runnable r = () -> {
			//List<Planta> listaPlantas = controladorPlantas.buscarTodas();
			//PARA PROBAR LA VISUALIZACION: 
			List<Planta> plantas = Aplicacion.listaPlantas;//probar
			List<VerticeView> vertices = new ArrayList<VerticeView>();
			int y = 100, x = 0, i = 0;
			for(Planta p: plantas){
				i++;
				x += 30; 
				if(i%2 == 0) y = 100;
				else y = 200;
				VerticeView v = new VerticeView(x,y,Color.YELLOW);
				v.setId(p.getId());
				v.setNombre(p.getNombre());
				vertices.add(v);
				pGrafo.agregar(v);
			}
			List<Arista<Planta>> aristas = controladorPlantas.getAristasGrafoPlantas();
			VerticeView v1, v2;
			for(Arista<Planta> arista: aristas) {
				v1 = vertices.stream().filter(v->v.getId().equals(arista.getInicio().getValor().getId())).collect(Collectors.toList()).get(0);
				v2 = vertices.stream().filter(v->v.getId().equals(arista.getFin().getValor().getId())).collect(Collectors.toList()).get(0);
				pGrafo.agregar(new AristaView(v1, v2));
			}
		};
		Thread hilo = new Thread(r);
		hilo.start();	
	}
	
	public void cargarComboInsumos(JComboBox<Insumo> combo){
		Runnable r = () -> {
				//List<Insumo> insumos = controladorInsumos.buscarTodos();
				List<Insumo> insumos = Aplicacion.listaInsumos;//para probar
				try {
					SwingUtilities.invokeAndWait(() -> {
						for(Insumo i: insumos){
							combo.addItem(i);
						}
					});
				} catch (InvocationTargetException | InterruptedException e) {
					e.printStackTrace();
				}
			};
			Thread hilo = new Thread(r);
			hilo.start();	
		}
	
	public void buscarMejorCamino(Insumo insumo, Boolean priorizarDistancia) {

		List<List<Planta>> mejorCamino = controladorPlantas.buscarMejorCamino(insumo, priorizarDistancia);
		
		int contador = 1;
		String informacion = new String();
		
		if(!mejorCamino.isEmpty()) {
			for(List<Planta> plantas: mejorCamino) {
				informacion = contador+": "+plantas.get(0).getNombre();
				for(int i=1; i<plantas.size(); i++) informacion = informacion.concat(" -> "+plantas.get(i).getNombre());
				pGrafo.mostrarInfo(informacion+" .\n");
				informacion = "";
				contador++;
			}
			
		}
		else pGrafo.mostrarInfo("No existe un camino que conecte las plantas que necesitan el insumo.. ");
	}
	
	public void buscarCaminos(Integer idOrigen, Integer idDestino) {
		
		HashMap<List<Planta>, Double[]> caminos = controladorPlantas.buscarCaminos(idOrigen, idDestino);
		
		int contador = 1;
		String informacion = new String();
		
		if(!caminos.isEmpty()) {
			for(Entry<List<Planta>, Double[]> camino: caminos.entrySet()) {
				List<Planta> plantas = camino.getKey();
				informacion = "CAMINO "+contador+": "+plantas.get(0).getNombre();
				for(int i=1; i<plantas.size(); i++) informacion = informacion.concat(" -> "+plantas.get(i).getNombre());
				Double[] info = camino.getValue();
				informacion = informacion.concat(".\n");
				informacion = informacion.concat("Distancia total: "+info[0]+". Tiempo total: "+info[1]+". Peso maximo total: "+info[2]+".");
				pGrafo.mostrarInfo(informacion+"\n");
				informacion = "";
				contador++;
			}
		}
		else pGrafo.mostrarInfo("No existe un camino entre los nodos seleccionados..");
		
	}

	public List<Integer> verticesAPintar(Insumo i) {
		//return controladorPlantas.necesitanInsumo(i).stream().map(p->p.getId()).collect(Collectors.toList());
		return Aplicacion.listaPlantas.stream().filter(p->p.necesitaInsumo(i)).map(Planta::getId).collect(Collectors.toList());
	}
	
}
