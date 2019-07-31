package isi.died.tp.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import isi.died.tp.controladores.ControladorPlantas;
import isi.died.tp.dominio.Camion;
import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.Stock;
import isi.died.tp.gui.util.GenericTableColumn;
import isi.died.tp.gui.util.GenericTableModel;

public class PanelMejorEnvio extends JPanel {

	private JLabel lblTablaPlantas;
	private JLabel lblTablaInsumos;
	private JLabel lblTablaCamiones;
	private JButton btnGenerarSolucion;
	private JTable tablaPlantas;
	private JTable tablaStock;
	private JTable tablaCamiones;
	private GenericTableModel<Planta> gtmPlantas;
	private GenericTableModel<Stock> gtmStock;
	private GenericTableModel<Camion> gtmCamiones;
	private ControladorPlantas controladorPlantas;
	
	public PanelMejorEnvio() {
		super();
		controladorPlantas = ControladorPlantas.getInstance();
		armar();
		configurarEventos();
	}

	private void configurarEventos() {
		
		/*
		btnGenerarSolucion.addActionListener( e -> {
			controladorPlantas.mejorEnvio();
		});
		*/
		
	}

	private void armar() {
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		btnGenerarSolucion = new JButton("GENERAR SOLUCION");
		c.gridx = 1;
		c.gridy = 1;
		add(btnGenerarSolucion, c);
		
		
	}
	
	public void actualizarDatosTablaPlantas(List<Planta> plantas) {
    	gtmPlantas.setDatos(plantas);
    	gtmPlantas.fireTableDataChanged();
	}
	
    public void actualizarDatosTablaStock(List<Stock> stock) {
    	gtmStock.setDatos(stock);
    	gtmStock.fireTableDataChanged();
    }
    
    public void actualizarDatosTablaCamiones(List<Camion> camiones) {
    	gtmCamiones.setDatos(camiones);
    	gtmCamiones.fireTableDataChanged();
    }
  
    private GenericTableModel<Planta> crearModeloTablaPlanta() {
    	gtmPlantas = new GenericTableModel<Planta>();
    	List<GenericTableColumn> columnas = new ArrayList<GenericTableColumn>();
    	columnas.add(new GenericTableColumn("Id" , "getId"));
    	columnas.add(new GenericTableColumn("Nombre" , "getNombre"));
    	gtmPlantas.setColumnas(columnas);
    	return gtmPlantas;
    }
    
    private GenericTableModel<Stock> crearModeloTablaStock() {
    	gtmStock = new GenericTableModel<Stock>();
    	List<GenericTableColumn> columnas = new ArrayList<GenericTableColumn>();
    	columnas.add(new GenericTableColumn("Insumo", "getInsumo"));
    	columnas.add(new GenericTableColumn("Id", "getId"));
    	columnas.add(new GenericTableColumn("Cantidad en stock", "getCantidad"));
    	columnas.add(new GenericTableColumn("Punto de pedido", "getPuntoPedido"));
    	gtmStock.setColumnas(columnas);
    	return gtmStock;
    }
    
    private GenericTableModel<Camion> crearModeloTablaCamiones() {
    	gtmCamiones = new GenericTableModel<Camion>();
    	List<GenericTableColumn> columnas = new ArrayList<GenericTableColumn>();
    	columnas.add(new GenericTableColumn("Id", "getId"));
    	columnas.add(new GenericTableColumn("Marca", "getMarca"));
    	columnas.add(new GenericTableColumn("Modelo", "getModelo"));
    	columnas.add(new GenericTableColumn("Dominio", "getDominio"));
    	columnas.add(new GenericTableColumn("Año", "getAño"));
    	columnas.add(new GenericTableColumn("Capacidad", "getCapacidad"));
    	columnas.add(new GenericTableColumn("Costo por km", "getCostoPorKm"));
    	columnas.add(new GenericTableColumn("Apto para líquidos", "getAptoParaLiquidos"));
    	gtmCamiones.setColumnas(columnas);
    	return gtmCamiones;
    }

}

