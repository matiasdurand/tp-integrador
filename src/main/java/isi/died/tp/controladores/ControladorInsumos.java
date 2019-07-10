package isi.died.tp.controladores;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Insumo.UnidadDeMedida;
import isi.died.tp.dominio.Liquido;
import isi.died.tp.gui.PanelEditarInsumo;
import isi.died.tp.gui.PanelInsumo;
import isi.died.tp.gui.PanelRegistrarInsumo;

public class ControladorInsumos {

	private static ControladorInsumos _INSTANCIA = null;
	private PanelInsumo pInsumo;
	private PanelRegistrarInsumo pRInsumo;
	private PanelEditarInsumo pEInsumo;
	
	private ControladorInsumos() {
		//EL CONTROLADOR NO PUEDE SER INSTANCIADO FUERA DE ESTE AMBITO
	}
	
	public static ControladorInsumos getInstance() {
		if(_INSTANCIA == null) _INSTANCIA = new ControladorInsumos();
		return _INSTANCIA;
	}
	
	public PanelInsumo getpInsumo() {
		return pInsumo;
	}
	
	public void setpInsumo(PanelInsumo pInsumo) {
		this.pInsumo = pInsumo;
	}
	
	public PanelRegistrarInsumo getpRInsumo() {
		return pRInsumo;
	}
	
	public void setpRInsumo(PanelRegistrarInsumo pRInsumo) {
		this.pRInsumo = pRInsumo;
	}
	
	public PanelEditarInsumo getpEInsumo() {
		return pEInsumo;
	}
	
	public void setpEInsumo(PanelEditarInsumo pEInsumo) {
		this.pEInsumo = pEInsumo;
	}
	
	public void crearInsumo(String nombre, String descripcion, UnidadDeMedida udm, Double costo, Integer stock, Double peso, Boolean esRefrigerado, Double densidad) {
		Runnable r = () -> {
			Insumo i;
			if(densidad>0) i = new Liquido(nombre, descripcion, costo, stock, esRefrigerado, densidad);
			else i = new Insumo(nombre, descripcion, udm, costo, stock, peso, esRefrigerado);
			
			//this.logica.guardar(p);
			//List<Proyecto> listaInsumos = logica.buscar(); ver como obtener lista de insumos sin base de datos;
			try {
				SwingUtilities.invokeAndWait(() -> {
					//pInsumo.actualizarDatosTabla(listaInsumos);
					JOptionPane.showMessageDialog((Component)pInsumo, "Insumo Registrado");
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		Thread hilo = new Thread(r);
		hilo.start();
	}
	
	public void actualizarInsumo(Integer id, String nombre, String descripcion, UnidadDeMedida udm, Double costo, Integer stock, Double peso, Boolean esRefrigerado, Double densidad) {
		/*Runnable r = () -> {
			Insumo i;
			if(densidad>0) i = new Liquido(nombre, descripcion, costo, stock, esRefrigerado, densidad);
			else i = new Insumo(nombre, descripcion, udm, costo, stock, peso, esRefrigerado);

			this.logica.guardar(p);
			List<Proyecto> lista = logica.buscar();
			try {
				SwingUtilities.invokeAndWait(() -> {
					panel.actualizarDatosTabla(lista);
					JOptionPane.showMessageDialog((Component) panel, "Proyecto "+ nombre +" Actualizado");
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		Thread hilo = new Thread(r);
		
		hilo.start();	*/
	}
	
	public List<Insumo> filtrar(String nombre, String costoMinimo, String costoMaximo, String stockMinimo, String stockMaximo) {
		
		Double costoMin, costoMax, stockMin, stockMax;
		List<Insumo> filtrado = new ArrayList<Insumo>();
		//OBTENER LISTA DE INSUMOS Y FILTRAR SEGUN PARAMETROS.
		
		return filtrado;
	}
	
	public void eliminarInsumo(Integer id) {
		//ELIMINAR EL INSUMO CON ID IGUAL A id;
	}

	public List<Insumo> ordenarPor(String criterio) {
		
		//ordenar la lista con el criterio (nombre, costo, stock)
		
		return null;
	}
	
	public void cargarComboUDM(JComboBox<UnidadDeMedida> combo){
		Runnable r = () -> {
				UnidadDeMedida[] unidadesDeMedida = UnidadDeMedida.values();
				try {
					SwingUtilities.invokeAndWait(() -> {
						for(UnidadDeMedida udm: unidadesDeMedida){
							combo.addItem(udm);
						}
					});
				} catch (InvocationTargetException | InterruptedException e) {
					e.printStackTrace();
				}
			};
			
			Thread hilo = new Thread(r);
			
			hilo.start();	
		}
	
	public Boolean esLiquido(Integer idInsumo) {
		//DEVOLVER TRUE SI EL INSUMO CON idInsumo ES LIQUIDO.
	}

}
