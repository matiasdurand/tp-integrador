package isi.died.tp.controladores;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import isi.died.tp.aplicacion.Aplicacion;
import isi.died.tp.dominio.GrafoPlantas;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.Stock;
import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.Grafo;
import isi.died.tp.dominio.Insumo.UnidadDeMedida;
import isi.died.tp.gui.AristaView;
import isi.died.tp.gui.PanelGrafo;
import isi.died.tp.gui.VerticeView;

public class ControladorGrafo {

	private static ControladorGrafo _INSTANCIA = null;

	private PanelGrafo pGrafo;
	private ControladorPlantas controladorPlantas;
	private ControladorInsumos controladorInsumos;
	private Grafo<Planta> grafoPlantas;
	
	private ControladorGrafo() {
		//NO SE PUEDE INSTANCIAR FUERA DE ESTE AMBITO.
		controladorPlantas = ControladorPlantas.getInstance();
		controladorInsumos = ControladorInsumos.getInstance();
		grafoPlantas = GrafoPlantas.getInstance();
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
	
	public void inicializarGrafo() {
		Runnable r = () -> {
			//List<Planta> listaPlantas = controladorPlantas.buscarTodas();
			//PARA PROBAR LA VISUALIZACION: 
			List<Planta> plantas = Aplicacion.listaPlantas;//probar
			List<VerticeView> verticesView = new ArrayList<VerticeView>();
			int y = 100;
			int x = 0;
			int i = 0;
			Color c = null;
			for(Planta p : plantas){
				i++;
				x +=30; 
				if(i%2 == 0) y=100;
				else y=200;
				c = Color.YELLOW;
				VerticeView v = new VerticeView(x,y,c);
				v.setId(p.getId());
				v.setNombre(p.getNombre());
				pGrafo.agregar(v);
				verticesView.add(v);
			}
			List<Arista<Planta>> aristasGrafoPlanta = grafoPlantas.getAristas();
			VerticeView v1, v2;
			for(Arista<Planta> arista: aristasGrafoPlanta) {
				v1 = verticesView.stream().filter(v->v.getId().equals(arista.getInicio().getValor().getId())).collect(Collectors.toList()).get(0);
				v2 = verticesView.stream().filter(v->v.getId().equals(arista.getFin().getValor().getId())).collect(Collectors.toList()).get(0);
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
	
	public void obtenerMejorCamino(Insumo insumo, Boolean priorizarDistancia) {
		//List<Planta> necesitanInsumo = controladorPlantas.necesitanInsumo(insumo);
		//List<List<Planta>> mejorCamino = GrafoPlantas.getInstance().buscarMejorCamino(necesitanInsumo, priorizarDistancia));
	}

	public List<Integer> verticesAPintar(Insumo i) {
		//return controladorPlantas.necesitanInsumo(i).stream().map(p->p.getId()).collect(Collectors.toList());
		return Aplicacion.listaPlantas.stream().filter(p->p.necesitaInsumo(i)).map(p->p.getId()).collect(Collectors.toList());//para probar
	}
	
}
