package isi.died.tp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import isi.died.tp.controladores.ControladorPaneles;
import isi.died.tp.controladores.ControladorPlantas;
import isi.died.tp.dominio.Camion;
import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.Stock;
import isi.died.tp.gui.util.GenericTableColumn;
import isi.died.tp.gui.util.GenericTableModel;

public class PanelMejorEnvio extends JPanel {

	private JLabel lblTablaPlantas;
	private JLabel lblTablaStock;
	private JLabel lblTablaCamiones;
	private JButton btnGenerarSolucion;
	private JButton btnCancelar;
	private JTable tablaPlantas;
	private JTable tablaStock;
	private JTable tablaCamiones;
	private JTextArea textArea;
	private GenericTableModel<Planta> gtmPlantas;
	private GenericTableModel<Stock> gtmStock;
	private GenericTableModel<Camion> gtmCamiones;
	private ControladorPlantas controladorPlantas;
	private Integer idPlantaSeleccionada;
	private Integer idCamionSeleccionado;
	private int contador = 0;
	
	public PanelMejorEnvio() {
		super();
		controladorPlantas = ControladorPlantas.getInstance();
		controladorPlantas.setpMEnvio(this);
		armar();
		configurarEventos();
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(1000,750);
    }

	private void armar() {
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		gtmPlantas = crearModeloTablaPlanta();
    	tablaPlantas = new JTable(gtmPlantas);
    	tablaPlantas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JScrollPane scrollPane1 = new JScrollPane(tablaPlantas);
    	c.gridx = 0;
    	c.gridy = 0;
    	add(scrollPane1, c);
		
    	gtmStock = crearModeloTablaStock();
    	tablaStock = new JTable(gtmStock);
    	tablaStock.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JScrollPane scrollPane2 = new JScrollPane(tablaStock);
    	c.gridx = 1;
    	c.gridy = 0;
    	add(scrollPane2, c);
		
    	gtmCamiones = crearModeloTablaCamiones();
    	tablaCamiones = new JTable(gtmCamiones);
    	tablaCamiones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JScrollPane scrollPane3 = new JScrollPane(tablaCamiones);
    	c.gridx = 0;
    	c.gridy = 1;
    	add(scrollPane3, c);
		
		btnGenerarSolucion = new JButton("GENERAR SOLUCION");
		c.gridx = 1;
		c.gridy = 1;
		add(btnGenerarSolucion, c);
		
		btnCancelar = new JButton("CANCELAR");
		c.gridx = 0;
		c.gridy = 3;
		add(btnCancelar, c);
		
		textArea = new JTextArea();
		c.gridx = 0;
		c.gridy = 2;
		add(textArea, c);

	}
	
	private void configurarEventos() {
		
		tablaPlantas.getSelectionModel().addListSelectionListener(lse -> {
			if(contador==1) {
				if(gtmPlantas.getDatos()!=null && !gtmPlantas.getDatos().isEmpty() && lse.getFirstIndex()<gtmPlantas.getDatos().size()) {
					gtmPlantas.datos.get(lse.getFirstIndex());
					idPlantaSeleccionada = tablaPlantas.getSelectedRow()+1;
					System.out.println(idPlantaSeleccionada);
					if(idPlantaSeleccionada>0) actualizarDatosTablaStock(controladorPlantas.buscarStockFaltante(idPlantaSeleccionada));
					contador=0;
				}
			}
			else contador++;
		});
		
		tablaCamiones.getSelectionModel().addListSelectionListener(lse -> {
			if(contador==1) {
				if(gtmCamiones.getDatos()!=null && !gtmCamiones.getDatos().isEmpty() && lse.getFirstIndex()<gtmCamiones.getDatos().size()) {
					gtmCamiones.datos.get(lse.getFirstIndex());
					idCamionSeleccionado = tablaCamiones.getSelectedRow()+1;
					btnGenerarSolucion.setEnabled(true);
					System.out.println(idCamionSeleccionado);
					contador=0;
				}
			}
			else contador++;
		});
		
		/*btnGenerarSolucion.addActionListener( e -> {
			controladorPlantas.mejorEnvio(idCamionSeleccionado);
		});*/
		
		btnCancelar.addActionListener(e -> {
			JFrame frame = ((JFrame)SwingUtilities.getWindowAncestor(this));
			frame.getContentPane().removeAll();
			frame.getContentPane().add(ControladorPaneles.getInstance().getPanelPrincipal());
			frame.pack();
			frame.revalidate();
			frame.repaint();
		});
		
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
    
    public void mostrarMejorSeleccionEnvio(String insumos) {
    	textArea.setText(insumos);
    }

}

