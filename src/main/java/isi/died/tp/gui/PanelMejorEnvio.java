package isi.died.tp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import isi.died.tp.controladores.ControladorCamiones;
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
	private ControladorCamiones controladorCamiones;
	private Integer idPlantaSeleccionada=-1;
	private Integer idCamionSeleccionado=-1;
	private int contador = 0;
	private JLabel lblPanelTitulo;
	
	public PanelMejorEnvio() {
		super();
		controladorPlantas = ControladorPlantas.getInstance();
		controladorPlantas.setpMEnvio(this);
		controladorCamiones = ControladorCamiones.getInstance();
		controladorCamiones.setpMEnvio(this);
		armar();
		configurarEventos();
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(1000,750);
    }

	private void armar() {
		setLayout(new GridBagLayout());
		int fila = 0;
    	int col = 0;
    	
    	GridBagConstraints c = new GridBagConstraints();    	

    	this.lblPanelTitulo = new JLabel("MEJOR SELECCIÓN DE ENVÍO");
    	this.lblPanelTitulo.setFont(ControladorCamiones.FUENTE_TITULO);
    	this.lblPanelTitulo.setForeground(ControladorCamiones.COLOR_TITULO);
    	c.gridx=col;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.NORTH;
    	c.gridwidth=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblPanelTitulo,c);
    	
    	//Primer fila
    	
    	col=0;
    	fila++;
    	
    	lblTablaPlantas = new JLabel("Plantas registradas");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.weighty=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	add(lblTablaPlantas,c);
    	
    	lblTablaStock = new JLabel("Stock faltantes");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.weighty=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	add(lblTablaStock,c);
    	
    	//Segunda fila
    	
    	col=0;
    	fila++;
    	
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weighty=1.0;
    	c.weightx=1.0;
		
		gtmPlantas = crearModeloTablaPlanta();
    	tablaPlantas = new JTable(gtmPlantas);
    	tablaPlantas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JScrollPane scrollPane1 = new JScrollPane(tablaPlantas);
    	add(scrollPane1, c);
    	controladorPlantas.cargarPlantas();
    	
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weighty=1.0;
    	c.weightx=1.0;
		
    	gtmStock = crearModeloTablaStock();
    	tablaStock = new JTable(gtmStock);
    	tablaStock.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JScrollPane scrollPane2 = new JScrollPane(tablaStock);
    	add(scrollPane2, c);
    	
    	//Tercer fila
    	
    	col=0;
    	fila++;
    	
    	lblTablaCamiones = new JLabel("Camiones disponibles");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=2;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.weighty=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	add(lblTablaCamiones,c);
    	
    	//Cuarta fila
    	
    	col=0;
    	fila++;
    	
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=2;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weighty=1.0;
    	c.weightx=1.0;
		
    	gtmCamiones = crearModeloTablaCamiones();
    	tablaCamiones = new JTable(gtmCamiones);
    	tablaCamiones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JScrollPane scrollPane3 = new JScrollPane(tablaCamiones);
    	add(scrollPane3, c);
    	controladorCamiones.cargarCamiones();
    	
    	//Quinta fila
    	
    	col=0;
    	fila++;
    	
    	JPanel panelBotones = new JPanel();
		
		btnGenerarSolucion = new JButton("GENERAR SOLUCIÓN");
		btnGenerarSolucion.setEnabled(false);
		btnCancelar = new JButton("CANCELAR");
		panelBotones.add(btnGenerarSolucion);
		panelBotones.add(btnCancelar);
		
    	c.fill=GridBagConstraints.HORIZONTAL;
    	c.anchor=GridBagConstraints.WEST;
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=2;
    	c.weighty=0.0;
    	c.weightx=0.0;
    	c.insets = new Insets(10, 10, 10, 10);
    	
    	this.add(panelBotones,c);
    	
    	//Sexta fila
    	
    	col=0;
    	fila++;
		
		textArea = new JTextArea();
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=2;
    	c.gridheight=1;
    	c.weightx=1.0;
    	c.weighty=1.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.insets = new Insets(10, 10, 10, 10);
		add(textArea, c);

	}
	
	private void configurarEventos() {
		
		tablaPlantas.getSelectionModel().addListSelectionListener(lse -> {
			if(contador==1) {
				if(tablaPlantas.getSelectedRow()>=0) idPlantaSeleccionada = (Integer)tablaPlantas.getValueAt(tablaPlantas.getSelectedRow(), 0);
				System.out.println(idPlantaSeleccionada);
				if(idPlantaSeleccionada>0) actualizarDatosTablaStock(controladorPlantas.buscarStockFaltante(idPlantaSeleccionada));
				if(idCamionSeleccionado>0 && tablaStock.getRowCount()!=0) btnGenerarSolucion.setEnabled(true);
				else btnGenerarSolucion.setEnabled(false);
				contador=0;
			}
			else contador++;
		});
		
		tablaCamiones.getSelectionModel().addListSelectionListener(lse -> {
			if(contador==1) {
				if(tablaCamiones.getSelectedRow()>=0) idCamionSeleccionado = (Integer)tablaCamiones.getValueAt(tablaCamiones.getSelectedRow(), 0);
				System.out.println(idCamionSeleccionado);
				if(idPlantaSeleccionada>0 && tablaStock.getRowCount()!=0) btnGenerarSolucion.setEnabled(true);
				else btnGenerarSolucion.setEnabled(false);
				contador=0;
			}
			else contador++;
		});
		
		btnGenerarSolucion.addActionListener( e -> {
			Boolean camionAptoParaLiquidos = controladorCamiones.aptoParaLiquidos(idCamionSeleccionado);
			if(!camionAptoParaLiquidos) {
				int confirmar = JOptionPane.showConfirmDialog(this, "El camión seleccionado no es apto para líquidos.\nSi continúa no se tendrán en cuenta los insumos de este tipo. ", "Advertencia", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(confirmar==0) controladorPlantas.mejorEnvio(idPlantaSeleccionada, idCamionSeleccionado, camionAptoParaLiquidos);
			}
			else controladorPlantas.mejorEnvio(idPlantaSeleccionada, idCamionSeleccionado, camionAptoParaLiquidos);
			btnGenerarSolucion.setEnabled(false);
		});
		
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
    	columnas.add(new GenericTableColumn("Id", "getId"));
    	columnas.add(new GenericTableColumn("Insumo", "getInsumo"));
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

