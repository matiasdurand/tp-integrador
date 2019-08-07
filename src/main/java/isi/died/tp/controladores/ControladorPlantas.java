package isi.died.tp.controladores;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
	private ControladorCamiones controladorCamiones;
	private PlantaDao daoPlanta;
	private StockDao daoStock;
	private GrafoPlantas grafoPlantas;
	
	public static final Font FUENTE_TITULO_PRINCIPAL = new Font("Calibri",Font.BOLD,24);
	public static final Font FUENTE_TITULO = new Font("Calibri",Font.BOLD,18);
	public static final Color COLOR_TITULO = new Color(5,85,244);
	
	
	private ControladorPlantas() {
		controladorInsumos = ControladorInsumos.getInstance();
		controladorCamiones = ControladorCamiones.getInstance();
		daoPlanta = new PlantaDaoH2();
		daoStock = new StockDaoH2();
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
	
	public void setpMEnvio(PanelMejorEnvio pMEnvio) {
		this.pMEnvio = pMEnvio;
	}
	
	public List<Planta> buscarTodas(){
		return daoPlanta.buscarTodas();
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
	
	public void actualizarDatosTablaPlantas() {
		pPlanta.actualizarDatosTablaPlantas(daoPlanta.buscarTodas());
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
			
			daoStock.borrar(id, -1);
			
			List<Stock> stockAEliminar = plantaAEliminar.getListaStock();
			for(Stock s: stockAEliminar) controladorInsumos.disminuirStockInsumo(s.getInsumo(), s.getCantidad());
			
			grafoPlantas.eliminarNodo(plantaAEliminar);
			
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
					for(Planta p: plantas) if(p.getId()!=id) combo.addItem(p);
				});
			} 
			catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
			
		};
		
		Thread hilo = new Thread(r);
		hilo.start();
		
	}
	
	public Planta buscarPlanta(Integer id) {
		return daoPlanta.buscar(id);
	}

	public void mostrarStock(Integer id){
		pPlanta.actualizarDatosTablaStock(daoPlanta.buscar(id).getListaStock());
	}

	public void conectarPlantas(Integer idPlantaOrigen, Planta destino, Double distancia, Double duracion, Double pesoMax) {
		grafoPlantas.conectar(daoPlanta.buscar(idPlantaOrigen), destino, distancia, duracion, pesoMax);
	}
	
	public void cargarStock(Integer id, Insumo i, Integer cantidad, Integer puntoPedido) {
		
		Planta acopioInicial = daoPlanta.buscar(1);
		
		if(id.equals(1)) {
			
			daoStock.actualizar(1, new Stock(acopioInicial.disponible(i)+cantidad, 0, i));
			
			//JOptionPane.showMessageDialog(pPlanta, "El stock ha sido cargado correctamente");
			
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
				
				//JOptionPane.showMessageDialog(pPlanta, "El stock ha sido cargado correctamente");
				
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
		grafoPlantas.actualizarNodo(daoPlanta.buscar(1));
	}

	public List<Stock> buscarStockFaltante(Integer idPlantaSeleccionada) {
		return daoPlanta.buscar(idPlantaSeleccionada).getListaStock().stream().filter(s -> s.getCantidad()<=s.getPuntoPedido()).collect(Collectors.toList());
	}

	public void cargarPlantas() {
		pMEnvio.actualizarDatosTablaPlantas(daoPlanta.buscarTodas());
	}

	public void mejorEnvio(/*Integer idPlantaSeleccionada,*/ Integer idCamionSeleccionado, Boolean camionAptoParaLiquidos) {
		
		Camion camion = controladorCamiones.buscarCamion(idCamionSeleccionado);
		
		List<Planta> plantas = daoPlanta.buscarTodas();
		List<Stock> stockFaltante = new ArrayList<Stock>();
		
		for(Planta p: plantas) {
			for(Stock sf: buscarStockFaltante(p.getId())) {
				if(stockFaltante.contains(sf)) {
					Stock s2 = stockFaltante.stream().filter(s->s.equals(sf)).collect(Collectors.toList()).get(0);
					stockFaltante.remove(s2);
					stockFaltante.add(new Stock((s2.getCantidad()+2*sf.getPuntoPedido()-sf.getCantidad()), 0, s2.getInsumo()));
				}
				else stockFaltante.add(new Stock(2*sf.getPuntoPedido()-sf.getCantidad(), 0, sf.getInsumo()));
			}
		}
		System.out.println(stockFaltante);
		//List<Stock> stockFaltante = buscarStockFaltante(idPlantaSeleccionada);
		
		if(!camionAptoParaLiquidos) stockFaltante = stockFaltante.stream().filter(s->!s.getInsumo().getEsLiquido()).collect(Collectors.toList());
		
		if(stockFaltante.isEmpty()) JOptionPane.showMessageDialog(pMEnvio, "El stock faltante es de insumos líquidos.\nPor favor seleccione un camión apto para este tipo de insumos.");
		else {
			int cantidadInsumos = stockFaltante.size();

			int[] pesos = new int[cantidadInsumos+1];
			double[] valores = new double[cantidadInsumos+1];
			
			pesos[0]=0;
			valores[0]=0;
			
			int index=1;
			int pesoTotalAEnviar=0;
			
			for(Stock s: stockFaltante) {
				
				Insumo i = s.getInsumo();
				
				//int cantidadAEnviar = 2*s.getPuntoPedido()-s.getCantidad();
				int cantidadAEnviar = s.getCantidad();
				
				double pesoInsumo = i.getPeso().doubleValue();
				
				int pesoAEnviar;
				
				if(pesoInsumo%1==0) pesoAEnviar = (int)pesoInsumo*cantidadAEnviar;
				else pesoAEnviar = (int)((pesoInsumo*cantidadAEnviar)+1);
				
				pesos[index] = pesoAEnviar;
				valores[index] = i.getCosto().doubleValue();
				
				pesoTotalAEnviar += pesoAEnviar;
				
				index++;
			}
			
			int pesoMaximo = camion.getCapacidad().intValue();
			
			List<Insumo> insumosAEnviar = new ArrayList<Insumo>();
			
			if(pesoTotalAEnviar<=pesoMaximo) insumosAEnviar = stockFaltante.stream().map(Stock::getInsumo).collect(Collectors.toList());
			else {
				if(!(cantidadInsumos==1)) {
					
					Boolean[] seleccionados = resolver(pesos, valores, cantidadInsumos, pesoMaximo);
					
					for(int x=1; x<seleccionados.length; x++) if(seleccionados[x]) insumosAEnviar.add(stockFaltante.get(x-1).getInsumo());
					
				}
			}
			
			pMEnvio.mostrarMejorSeleccionEnvio("Enviar los siguientes insumos: "+insumosAEnviar.toString());
			
		}
		
	}

	public Boolean[] resolver(int[] pesos, double[] valores, int cantidadInsumos, int pesoMaximo) {
		
	    int N = cantidadInsumos+1;
	    int W = pesoMaximo+1;
	    
	    double[][] opt = new double[N][W];
	    boolean[][] sol = new boolean[N][W];
	    
	    for (int n = 0; n < N; n++) {
	        for (int w = 0; w < W; w++) {
	            double option1 = n < 1 ? 0 : opt[n - 1][w];
	            double option2 = Double.MIN_VALUE;
	            if (n!=0 && pesos[n]<=w) option2 = valores[n] + opt[n-1][w-pesos[n]];
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

	public void borrarStocksEnGrafoPlantas(Insumo i) {
		grafoPlantas.borrarStocks(i);
	}

	public void actualizarStocksEnGrafoPlantas(Insumo i) {
		grafoPlantas.actualizarStocks(i);
		
	}

}