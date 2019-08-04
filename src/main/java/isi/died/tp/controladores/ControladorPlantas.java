package isi.died.tp.controladores;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import isi.died.tp.dao.CamionDao;
import isi.died.tp.dao.CamionDaoH2;
import isi.died.tp.dao.PlantaDao;
import isi.died.tp.dao.PlantaDaoH2;
import isi.died.tp.dao.StockDao;
import isi.died.tp.dao.StockDaoH2;
import isi.died.tp.dominio.Camion;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.Stock;
import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.GrafoPlantas;
import isi.died.tp.gui.PanelMejorEnvio;
import isi.died.tp.gui.PanelPlanta;
import isi.died.tp.gui.PanelPrincipal;

public class ControladorPlantas {

	private static ControladorPlantas _INSTANCIA = null;
	
	private PanelPlanta pPlanta;
	private PanelPrincipal pPrincipal;
	private PanelMejorEnvio pMEnvio;
	private ControladorInsumos controladorInsumos;
	private PlantaDao daoPlanta;
	private StockDao daoStock;
	private CamionDao daoCamion;
	private GrafoPlantas grafoPlantas;
	
	public static final Font FUENTE_TITULO_PRINCIPAL = new Font("Calibri",Font.BOLD,24);
	public static final Font FUENTE_TITULO = new Font("Calibri",Font.BOLD,18);
	public static final Color COLOR_TITULO = new Color(5,85,244);
	
	
	private ControladorPlantas() {
		controladorInsumos = ControladorInsumos.getInstance();
		daoPlanta = new PlantaDaoH2();
		daoStock = new StockDaoH2();
		daoCamion = new CamionDaoH2();
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
	
	public PanelPrincipal getpPrincipal() {
		return pPrincipal;
	}
	
	public void setpPrincipal(PanelPrincipal pPrincipal) {
		this.pPrincipal = pPrincipal;
	}
	
	public List<Planta> buscarTodas(){
		return daoPlanta.buscarTodas();
	}
	
	public void setpMEnvio(PanelMejorEnvio pMEnvio) {
		this.pMEnvio = pMEnvio;
	}
	
	public List<Planta> necesitanInsumo(Insumo i){
		return buscarTodas().stream().filter(p->p.necesitaInsumo(i)).collect(Collectors.toList());
	}

	public List<Arista<Planta>> getAristasGrafoPlantas() {
		return grafoPlantas.getAristas();
	}

	public void cargarComboInsumos(JComboBox<Insumo> combo){
		List<Insumo> insumos = controladorInsumos.buscarTodos();
		for(Insumo i: insumos) combo.addItem(i);					
	}
	
	public void crearPlanta(String nombre) {
		Runnable r = () -> {
			Planta p = new Planta(nombre);
			grafoPlantas.addNodo(daoPlanta.crear(p));
			List<Planta> plantas = daoPlanta.buscarTodas();
			try {
				SwingUtilities.invokeAndWait(() -> {
					pPlanta.actualizarDatosTablaPlantas(plantas);
					JOptionPane.showMessageDialog(pPlanta, "La planta ha sido creada correctamente");
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		Thread hilo = new Thread(r);
		hilo.start();
	}
	
	public void crearAcopios(String nombre1, String nombre2) {
		Runnable r = () -> {
			Planta acopioInicial = new Planta(nombre1);
			Planta acopioFinal = new Planta(nombre2);
			grafoPlantas.addNodo(daoPlanta.crear(acopioInicial));
			grafoPlantas.addNodo(daoPlanta.crear(acopioFinal));
			List<Planta> plantas = daoPlanta.buscarTodas();
			try {
				SwingUtilities.invokeAndWait(() -> {
					pPlanta.actualizarDatosTablaPlantas(plantas);
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		Thread hilo = new Thread(r);
		hilo.start();
	}
	
	public void actualizarPlanta(Integer id, String nombre) {
		Runnable r = () -> {
			Planta p = new Planta(nombre);
			p.setId(id);
			grafoPlantas.actualizarNodo(daoPlanta.actualizar(p));
			List<Planta> plantas = daoPlanta.buscarTodas();
			try {
				SwingUtilities.invokeAndWait(() -> {
					pPlanta.actualizarDatosTablaPlantas(plantas);
					JOptionPane.showMessageDialog(pPlanta, "La planta ha sido actualizada correctamente");
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		Thread hilo = new Thread(r);
		
		hilo.start();	
	}
	
	public void borrarPlanta(Integer id) {
		Runnable r = () -> {
			Planta plantaAEliminar = daoPlanta.buscar(id);
			grafoPlantas.eliminarNodo(plantaAEliminar);
			List<Stock> stockAEliminar = plantaAEliminar.getListaStock();
			for(Stock s: stockAEliminar) controladorInsumos.disminuirStockInsumo(s.getInsumo(), s.getCantidad());
			daoPlanta.borrar(id);
			List<Planta> plantas = daoPlanta.buscarTodas();
			try {
				SwingUtilities.invokeAndWait(() -> {
					pPlanta.actualizarDatosTablaPlantas(plantas);
					JOptionPane.showMessageDialog(pPlanta, "La planta ha sido eliminada correctamente");
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		Thread hilo = new Thread(r);
		hilo.start();
	}

	public void cargarComboPlantasExceptoSeleccionada(JComboBox<Planta> combo, Integer id) {
		Runnable r = () -> {
			List<Planta> plantas = daoPlanta.buscarTodas();
			try {
				SwingUtilities.invokeAndWait(() -> {
					for(Planta p: plantas){
						if(p.getId()!=id) {
							combo.addItem(p);
						}
					}
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		Thread hilo = new Thread(r);
		hilo.start();
	}
	
	public Planta obtenerPlanta(Integer id) {
		return daoPlanta.buscar(id);
	}

	public void mostrarStock(Integer idPlantaSeleccionada){
		pPlanta.actualizarDatosTablaStock(daoPlanta.buscar(idPlantaSeleccionada).getListaStock());
	}

	public void conectarPlantas(Integer idPlantaOrigen, Planta destino, Double distancia, Double duracion, Double pesoMax) {
		grafoPlantas.conectar(daoPlanta.buscar(idPlantaOrigen), destino, distancia, duracion, pesoMax);
	}
	
	public void cargarStock(Integer id, Insumo i, Integer cantidad, Integer puntoPedido) {
		Planta acopioInicial = daoPlanta.buscar(1);
		if(id.equals(1)) {
			daoStock.actualizar(1, new Stock(acopioInicial.disponible(i)+cantidad, 0, i));
			pPlanta.actualizarDatosTablaStock(daoPlanta.buscar(id).getListaStock());
			JOptionPane.showMessageDialog(pPlanta, "El stock ha sido cargado correctamente");
			controladorInsumos.aumentarStockInsumo(i, cantidad);
		}
		else {
			if(acopioInicial.validarCantidad(i, cantidad)) {
				Stock s = new Stock(cantidad, puntoPedido, i);
				Planta p = daoPlanta.buscar(id);
				Boolean existe = p.existeStock(s);
				if(existe) daoStock.actualizar(id,s);
				else daoStock.crear(id,s);
				daoStock.actualizar(1, new Stock(acopioInicial.disponible(i)-cantidad, 0, i));
				pPlanta.actualizarDatosTablaStock(daoPlanta.buscar(id).getListaStock());
				JOptionPane.showMessageDialog(pPlanta, "El stock ha sido cargado correctamente");
				grafoPlantas.actualizarNodo(daoPlanta.buscar(id));
			}
			else JOptionPane.showMessageDialog(pPlanta, "No hay stock disponible en el Acopio Inicial");
		}
		grafoPlantas.actualizarNodo(daoPlanta.buscar(1));
	}
	
	public List<List<Planta>> buscarMejorCamino(List<Planta> plantas, Boolean priorizarDistancia) {
		return grafoPlantas.buscarMejorCamino(plantas, priorizarDistancia);
	}

	public HashMap<List<Planta>, Double[]> buscarCaminos(Integer idOrigen, Integer idDestino) {
		return grafoPlantas.buscarCaminos(daoPlanta.buscar(idOrigen), daoPlanta.buscar(idDestino));
	}
	
	public List<Planta> pageRank(){
		return grafoPlantas.pageRank();
	}

	public void calcularFlujoMaximo() {
		pPrincipal.mostrarFlujoMaximo(grafoPlantas.flujoMaximo());
	}

	public void almacenar(Insumo i) {
		daoStock.crear(1, new Stock(i.getStock(), 0, i));
	}

	public List<Stock> buscarStockFaltante(Integer idPlantaSeleccionada) {
		return daoPlanta.buscar(idPlantaSeleccionada).getListaStock().stream().filter(s -> s.getCantidad()<=s.getPuntoPedido()).collect(Collectors.toList());
	}

	public void cargarPlantas() {
		pMEnvio.actualizarDatosTablaPlantas(daoPlanta.buscarTodas());
	}

	public void mejorEnvio(Integer idPlantaSeleccionada, Integer idCamionSeleccionado) {
		
		Camion camion = daoCamion.buscar(idCamionSeleccionado);
		System.out.println("Camion: "+camion.getDominio());
		List<Stock> stockFaltante = buscarStockFaltante(idPlantaSeleccionada);
		
		int cantidadInsumos = stockFaltante.size();
		System.out.println("Cantidad insumos a enviar: "+cantidadInsumos);
		int[] pesos = new int[cantidadInsumos+1];
		double[] valores = new double[cantidadInsumos+1];
		
		pesos[0]=0;
		valores[0]=0;
		
		int index=1;
		for(Stock s: stockFaltante) {
			Insumo i = s.getInsumo();
			int cantidadAEnviar = s.getPuntoPedido()-s.getCantidad()+10;
			pesos[index] = (int)((i.getPeso().doubleValue()*cantidadAEnviar)+1);
			valores[index] = i.getCosto().doubleValue();
			index++;
		}
		
		for(int j=0; j<cantidadInsumos+1; j++) {
			System.out.println("Peso y valor de insumo "+j+" a transportar: "+pesos[j]+" "+valores[j]);
		}
		
		Boolean[] seleccionados = resolver(pesos, valores, cantidadInsumos+1, camion.getCapacidad().intValue());
		
		List<Insumo> insumosAEnviar = new ArrayList<Insumo>();
		
		for(int x=0; x<seleccionados.length; x++) {
			if(seleccionados[x]) insumosAEnviar.add(stockFaltante.get(x-1).getInsumo());
			System.out.println(seleccionados[x]);
		}
		
	    pMEnvio.mostrarMejorSeleccionEnvio("Enviar los siguientes insumos: "+insumosAEnviar.toString());
	    
	}

	public Boolean[] resolver(int[] pesos, double[] valores, int cantidadInsumos, int pesoMaximo) {
		System.out.println("peso maximo: "+pesoMaximo);
	    int N = cantidadInsumos;
	    int W = pesoMaximo;
	    
	    double[][] opt = new double[N][W];
	    boolean[][] sol = new boolean[N][W];
	    
	    for (int n = 1; n < N; n++) {
	        for (int w = 0; w < W; w++) {
	            double option1 = n < 1 ? 0 : opt[n - 1][w];
	            double option2 = Double.MIN_VALUE;
	            if (pesos[n]<=w) option2 = valores[n] + opt[n-1][w-pesos[n]];
	            opt[n][w] = Math.max(option1, option2);
	            sol[n][w] = (option2 > option1);
	        }
	    }
	    
	    Boolean[] esSolucion = new Boolean[N];
	    for (int n = N-1, w = W-1; n >= 0; n--) {
	        if (sol[n][w]) {
	            esSolucion[n] = true;
	            w = w - pesos[n];
	        } 
	        else esSolucion[n] = false;
	    }
	    
	    return esSolucion;
	    
	}

	
}