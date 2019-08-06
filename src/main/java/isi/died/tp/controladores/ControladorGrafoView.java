package isi.died.tp.controladores;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;
import isi.died.tp.estructuras.Arista;
import isi.died.tp.gui.AristaView;
import isi.died.tp.gui.PanelGrafoView;
import isi.died.tp.gui.VerticeView;

public class ControladorGrafoView {

	private static ControladorGrafoView _INSTANCIA = null;

	private PanelGrafoView pGrafo;
	private ControladorPlantas controladorPlantas;
	private ControladorInsumos controladorInsumos;
	
	public static final Font FUENTE_TITULO = new Font("Calibri",Font.BOLD,18);
	public static final Color COLOR_TITULO = new Color(5,85,244);
	
	
	private ControladorGrafoView() {
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
		
			List<Planta> plantas = controladorPlantas.buscarTodas();
			
			List<VerticeView> vertices = new ArrayList<VerticeView>();
			
			int y=250, x=50, i=0;
			
			for(Planta p: plantas){
				i++;
				x+=30; 
				
				if(i%2==0) y=100;
				else y=200;
				
				VerticeView nuevoVertice = new VerticeView(x,y,Color.YELLOW);
				nuevoVertice.setId(p.getId());
				nuevoVertice.setNombre(p.getNombre());
				
				vertices.add(nuevoVertice);
				
				pGrafo.agregar(nuevoVertice);
			}
			
			List<Arista<Planta>> aristas = controladorPlantas.getAristasGrafoPlantas();
			
			VerticeView verticeOrigen, verticeDestino;
			
			for(Arista<Planta> a: aristas) {
				
				verticeOrigen = vertices.stream().filter(v->v.getId().equals(a.getInicio().getValor().getId())).collect(Collectors.toList()).get(0);
				verticeDestino = vertices.stream().filter(v->v.getId().equals(a.getFin().getValor().getId())).collect(Collectors.toList()).get(0);
				
				pGrafo.agregar(new AristaView(verticeOrigen, verticeDestino, a.getDistancia().toString(), a.getTiempo().toString(), a.getPesoMax().toString()));
			}
			
	}
	
	public void cargarComboInsumos(JComboBox<Insumo> combo){
		
		Runnable r = () -> {
				
			List<Insumo> insumos = controladorInsumos.buscarTodos();
				
			try {
				SwingUtilities.invokeAndWait(() -> {
					for(Insumo i: insumos) combo.addItem(i);
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
			
		};
		
		Thread hilo = new Thread(r);
		hilo.start();	
		
	}
	
	public void buscarMejorCamino(Insumo insumo, Boolean priorizarDistancia) {
		
		List<Planta> plantas = controladorPlantas.necesitanInsumo(insumo);
		
		if(plantas.isEmpty()) pGrafo.mostrarInfo("No existe una planta que necesite el insumo seleccionado.");
		else {
			List<List<Planta>> mejorCamino = controladorPlantas.buscarMejorCamino(plantas, priorizarDistancia);
			
			if(mejorCamino.isEmpty()) pGrafo.mostrarInfo("No existe un camino que conecte las plantas que necesitan el insumo.");
			else {
				int contador = 1;
				String informacion = new String();
				pGrafo.mostrarInfo("Mejor camino encontrado:\n");
				for(List<Planta> camino: mejorCamino) {
					informacion = contador+":  "+camino.get(0).getNombre();
					for(int i=1; i<camino.size(); i++) informacion = informacion.concat(" -> "+camino.get(i).getNombre());
					pGrafo.mostrarInfo(informacion+" .\n");
					informacion = "";
					contador++;
				}
			}
		}
		
	}
	
	public void buscarCaminos(Integer idOrigen, Integer idDestino) {
		
		HashMap<List<Planta>, Double[]> caminos = controladorPlantas.buscarCaminos(idOrigen, idDestino);
		
		if(caminos.isEmpty()) pGrafo.mostrarInfo("No existe un camino entre los nodos seleccionados.");
		else {
			int contador = 1;
			String informacion = new String();
			for(Entry<List<Planta>, Double[]> camino: caminos.entrySet()) {
				List<Planta> plantas = camino.getKey();
				informacion = "CAMINO "+contador+":  "+plantas.get(0).getNombre();
				for(int i=1; i<plantas.size(); i++) informacion = informacion.concat(" -> "+plantas.get(i).getNombre());
				Double[] info = camino.getValue();
				informacion = informacion.concat(".\n");
				informacion = informacion.concat("Información: Distancia total: "+info[0]+". Tiempo total: "+info[1]+". Peso maximo: "+info[2]+".");
				pGrafo.mostrarInfo(informacion+"\n\n");
				informacion = "";
				contador++;
			}
		}
		
	}

	public void buscarVertices(Insumo i) {
		
		List<Integer> idVertices = controladorPlantas.necesitanInsumo(i).stream().map(p->p.getId()).collect(Collectors.toList());
		
		pGrafo.pintarVertices(idVertices, Color.RED);
		
	}
	
}
