package isi.died.tp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import isi.died.tp.controladores.ControladorPlantas;
import isi.died.tp.dominio.Planta;
import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Stock;
import isi.died.tp.gui.util.GenericTableColumn;
import isi.died.tp.gui.util.GenericTableModel;

public class PanelPlanta extends JPanel {
	
	private ControladorPlantas controlador;
	private JLabel lblPanelTitulo;
	private JLabel lblNombre;
	private JTextField nombre;
	private JButton btnConectar;
	private JButton btnRegistrar;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnCargarStock;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JTable tablaPlantas;
	private JTable tablaStock;
	private Integer idSeleccionado;
	private int contador=0;
	GenericTableModel<Planta> gtmPlantas;
	GenericTableModel<Stock> gtmStock;
	
	
	public PanelPlanta() {
		super();
		controlador = ControladorPlantas.getInstance();
		controlador.setpPlanta(this);
		armar();
		configurarEventos();
	}
	
	private void armar() {
			setLayout(new GridBagLayout());
			int fila = 0;
	    	int col = 0;
	    	
	    	GridBagConstraints c = new GridBagConstraints();    	
	    	this.lblPanelTitulo = new JLabel("GESTIÓN DE PLANTAS");
	    	this.lblPanelTitulo.setFont(ControladorPlantas.FUENTE_TITULO);
	    	this.lblPanelTitulo.setForeground(ControladorPlantas.COLOR_TITULO);
	    	c.gridx=col;
	    	c.gridy=fila;
	    	c.anchor = GridBagConstraints.NORTH;
	    	c.gridwidth=GridBagConstraints.NONE;
	    	c.insets = new Insets(10, 10, 10, 10);
	    	this.add(lblPanelTitulo,c);
	    	
	    	//Primer fila 
	    	
	    	col=0;
	    	fila++;
	    	
	    	JPanel panelFila1 = new JPanel();

	    	this.lblNombre = new JLabel("Nombre:");
	    	c.gridx=col++;
	    	c.gridy=fila;
	    	c.gridwidth=1;
	    	c.gridheight=1;
	    	c.weightx=0.0;
	    	c.anchor = GridBagConstraints.WEST;
	    	c.fill=GridBagConstraints.HORIZONTAL;
	    	c.insets = new Insets(10, 10, 10, 10);
	    	panelFila1.add(lblNombre);
	    	
	    	this.nombre = new JTextField(30);
	    	this.nombre.setEnabled(false);
	    	c.weightx=0.5;
	    	panelFila1.add(nombre);
	    	this.add(panelFila1,c);
	    	
	    	JPanel panelBotones1 = new JPanel();

	    	this.btnGuardar = new JButton("Guardar");
	    	this.btnCancelar = new JButton("Cancelar");
	    	btnGuardar.setEnabled(false);
	    	btnCancelar.setEnabled(false);
	    	panelBotones1.add(btnGuardar);
	    	panelBotones1.add(btnCancelar);
	    	c.fill=GridBagConstraints.HORIZONTAL;
	    	c.anchor=GridBagConstraints.WEST;
	    	c.gridx=col++;
	    	c.gridy=fila;
	    	c.gridwidth=0;
	    	c.weighty=0.0;
	    	c.weightx=0.0;
	    	c.insets = new Insets(10, 10, 10, 10);
	    	this.add(panelBotones1,c);
	    	
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
	    	
	    	this.gtmPlantas = crearModeloTablaPlanta();
	    	this.tablaPlantas = new JTable(this.gtmPlantas);
	    	this.tablaPlantas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    	JScrollPane scrollPane= new  JScrollPane(this.tablaPlantas);
	    	this.add(scrollPane,c);
	    	
	    	c.gridx=col++;
	    	c.gridy=fila;
	    	c.gridwidth=2;
	    	c.anchor = GridBagConstraints.CENTER;
	    	c.fill=GridBagConstraints.BOTH;
	    	c.weighty=1.0;
	    	c.weightx=1.0;
	    	
	    	this.gtmStock = crearModeloTablaStock();
	    	this.tablaStock = new JTable(this.gtmStock);
	    	this.tablaStock.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    	JScrollPane scrollPane2= new  JScrollPane(this.tablaStock);
	    	this.add(scrollPane2,c);
	    	
	    	//Tercer fila

	    	col=0;
	    	fila++;
	    	
	    	JPanel panelBotones = new JPanel();

	    	this.btnConectar = new JButton("Conectar");
	    	this.btnRegistrar = new JButton("Registrar");
	    	this.btnEditar = new JButton("Editar");
	    	this.btnEliminar = new JButton("Eliminar");
	    	this.btnCargarStock = new JButton("Cargar Stock");
	    	this.btnConectar.setEnabled(false);
	    	this.btnEditar.setEnabled(false);
	    	this.btnEliminar.setEnabled(false);
	    	this.btnCargarStock.setEnabled(false);
	    	panelBotones.add(btnConectar);
	    	panelBotones.add(btnRegistrar);
	    	panelBotones.add(btnEditar);
	    	panelBotones.add(btnEliminar);
	    	panelBotones.add(btnCargarStock);
	    	c.fill=GridBagConstraints.HORIZONTAL;
	    	c.anchor=GridBagConstraints.CENTER;
	    	c.gridx=col++;
	    	c.gridy=fila;
	    	c.gridwidth=3;
	    	c.weighty=0.0;
	    	c.weightx=0.0;
	    	c.insets = new Insets(10, 10, 10, 10);
	    	this.add(panelBotones,c);
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(1000,750);
    }
	
	private GenericTableModel<Planta> crearModeloTablaPlanta(){
    	this.gtmPlantas = new GenericTableModel<Planta>();
    	List<GenericTableColumn> lista = new ArrayList<GenericTableColumn>();
    	lista.add(new GenericTableColumn("Id" , "getId"));
    	lista.add(new GenericTableColumn("Nombre" , "getNombre"));
    	gtmPlantas.setColumnas(lista);
    	return gtmPlantas;
    }
	
	private GenericTableModel<Stock> crearModeloTablaStock(){
    	this.gtmStock = new GenericTableModel<Stock>();
    	List<GenericTableColumn> lista = new ArrayList<GenericTableColumn>();
    	lista.add(new GenericTableColumn("Id" , "getId"));
    	lista.add(new GenericTableColumn("Cantidad" , "getCantidad"));
    	lista.add(new GenericTableColumn("Punto de pedido", "getPuntoPedido"));
    	lista.add(new GenericTableColumn("Insumo", "getInsumo"));
    	gtmStock.setColumnas(lista);
    	return gtmStock;
    }
	
	private void configurarEventos() {
		btnRegistrar.addActionListener(e -> {
			nombre.setEnabled(true);
			nombre.setText("");
			btnRegistrar.setEnabled(false);
			btnGuardar.setEnabled(true);
			btnCancelar.setEnabled(true);
			tablaPlantas.setEnabled(false);
			btnEditar.setEnabled(false);
			btnConectar.setEnabled(false);
			btnEliminar.setEnabled(false);
			btnCargarStock.setEnabled(false);
			tablaStock.setEnabled(false);
			idSeleccionado=-1;
		});
		btnEditar.addActionListener(e -> {
			nombre.setEnabled(true);
			btnRegistrar.setEnabled(false);
			btnGuardar.setEnabled(true);
			btnCancelar.setEnabled(true);
			tablaPlantas.setEnabled(false);
			btnEditar.setEnabled(false);
			btnConectar.setEnabled(false);
			btnEliminar.setEnabled(false);
			btnCargarStock.setEnabled(false);
			tablaStock.setEnabled(false);
		});
		btnEliminar.addActionListener(e ->{
			int confirmar = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar la planta seleccionada?", "Eliminar planta", JOptionPane.YES_NO_OPTION);
			if(confirmar==0) {
				controlador.borrarPlanta(idSeleccionado);
				nombre.setEnabled(false);
				nombre.setText("");
				btnRegistrar.setEnabled(true);
				btnGuardar.setEnabled(false);
				btnCancelar.setEnabled(false);
				tablaPlantas.setEnabled(true);
				btnEditar.setEnabled(false);
				btnConectar.setEnabled(false);
				btnEliminar.setEnabled(false);
				btnCargarStock.setEnabled(false);
				tablaStock.setEnabled(true);
				idSeleccionado=-1;
			}
			actualizarDatosTablaStock(new ArrayList<Stock>());
		});
		btnCancelar.addActionListener(e -> {
			nombre.setEnabled(false);
			nombre.setText("");
			btnRegistrar.setEnabled(true);
			btnGuardar.setEnabled(false);
			btnCancelar.setEnabled(false);
			tablaPlantas.setEnabled(true);
			btnEditar.setEnabled(false);
			btnConectar.setEnabled(false);
			btnEliminar.setEnabled(false);
			btnCargarStock.setEnabled(false);
			tablaStock.setEnabled(true);
			idSeleccionado=-1;
		});
		btnGuardar.addActionListener(e -> {
			if(!nombre.getText().isEmpty()) {
				if(idSeleccionado<0) controlador.crearPlanta(nombre.getText());
				else controlador.actualizarPlanta(idSeleccionado, nombre.getText());
			}
			else JOptionPane.showMessageDialog(this, "Debe ingresar un nombre");
			nombre.setEnabled(false);
			nombre.setText("");
			btnRegistrar.setEnabled(true);
			btnGuardar.setEnabled(false);
			btnCancelar.setEnabled(false);
			tablaPlantas.setEnabled(true);
			btnEditar.setEnabled(false);
			btnConectar.setEnabled(false);
			btnEliminar.setEnabled(false);
			btnCargarStock.setEnabled(false);
			tablaStock.setEnabled(true);
			idSeleccionado=-1;
		});
		btnConectar.addActionListener(e -> {
			JComboBox<Planta> cmboxPlantas = new JComboBox<Planta>();
			controlador.cargarComboPlantasExceptoSeleccionada(cmboxPlantas, idSeleccionado);
			JTextField distancia = new JTextField();
			JTextField duracion = new JTextField();
			JTextField pesoMax = new JTextField();
			distancia.setText("");
			duracion.setText("");
			pesoMax.setText("");
			Object[] inputFields = {
				"Conectar con:", cmboxPlantas,
				"Distancia:", distancia,
				"Duración:", duracion,
				"Peso máximo", pesoMax
			};
			int opcion = JOptionPane.showConfirmDialog(this, inputFields, "Conectar plantas", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if(opcion == JOptionPane.OK_OPTION) {
				if(!distancia.getText().isEmpty() && !duracion.getText().isEmpty() && !pesoMax.getText().isEmpty()) {
					controlador.conectarPlantas(idSeleccionado, (Planta)cmboxPlantas.getSelectedItem(), Double.valueOf(distancia.getText()), Double.valueOf(duracion.getText()), Double.valueOf(pesoMax.getText()));
					JOptionPane.showMessageDialog(this, "Las plantas se han conectado exitosamente");
				}
				else JOptionPane.showMessageDialog(this, "Debes completar todos los campos");
			}
		});
		btnCargarStock.addActionListener(e -> {
			JComboBox<Insumo> cmboxInsumos = new JComboBox<Insumo>();
			controlador.cargarComboInsumos(cmboxInsumos);
			if(cmboxInsumos.getItemCount()==0) JOptionPane.showMessageDialog(this, "Primero debes registrar insumos");
			else {
				JTextField cantidad = new JTextField();
				JTextField puntoPedido = new JTextField();
				cantidad.setText("");
				puntoPedido.setText("");
				if(idSeleccionado.equals(1)) {
					Object[] inputFields = {
							"Seleccione un insumo:", cmboxInsumos,
							"Cantidad a cargar:", cantidad,
					};
					int opcion = JOptionPane.showConfirmDialog(this, inputFields, "Cargar stock en Acopio Inicial", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if(opcion == JOptionPane.OK_OPTION) {
						if(!cantidad.getText().isEmpty()) controlador.cargarStock(idSeleccionado, (Insumo)cmboxInsumos.getSelectedItem(), Integer.valueOf(cantidad.getText()), 0);
						else JOptionPane.showMessageDialog(this, "Debe completar todos los campos");
					}
				}
				else {
					Object[] inputFields = {
							"Seleccione un insumo:", cmboxInsumos,
							"Cantidad a cargar:", cantidad,
							"Punto de pedido:", puntoPedido
					};
					int opcion = JOptionPane.showConfirmDialog(this, inputFields, "Cargar stock en planta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if(opcion == JOptionPane.OK_OPTION) {
						if(!cantidad.getText().isEmpty() && !puntoPedido.getText().isEmpty()) controlador.cargarStock(idSeleccionado, (Insumo)cmboxInsumos.getSelectedItem(), Integer.valueOf(cantidad.getText()), Integer.valueOf(puntoPedido.getText()));
						else JOptionPane.showMessageDialog(this, "Debe completar todos los campos");
					}
				}
			}
			
		});
		tablaPlantas.getSelectionModel().addListSelectionListener(lse -> {
				if(contador==1) {
					if(tablaPlantas.getSelectedRow()>=0) idSeleccionado = (Integer) tablaPlantas.getValueAt(tablaPlantas.getSelectedRow(), 0);
					if(idSeleccionado.equals(1)) {
						btnConectar.setEnabled(true);
						btnEditar.setEnabled(true);
						btnEliminar.setEnabled(false);
						btnCargarStock.setEnabled(true);
					}
					else {
						if(idSeleccionado.equals(2)) {
							btnConectar.setEnabled(true);
							btnEditar.setEnabled(true);
							btnEliminar.setEnabled(false);
							btnCargarStock.setEnabled(false);
						}
						else {
							btnConectar.setEnabled(true);
							btnEditar.setEnabled(true);
							btnEliminar.setEnabled(true);
							btnCargarStock.setEnabled(true);
						}
					}
					if(idSeleccionado>0) {
						nombre.setText(controlador.obtenerPlanta(idSeleccionado).getNombre());
						controlador.mostrarStock(idSeleccionado);
					}
					contador=0;
				}
				else contador++;
        });
	}
	
	public void actualizarDatosTablaStock(List<Stock> lista) {
    	this.gtmStock.setDatos(lista);
    	this.gtmStock.fireTableDataChanged();
	}
	
    public void actualizarDatosTablaPlantas(List<Planta> lista) {
    	this.gtmPlantas.setDatos(lista);
    	this.gtmPlantas.fireTableDataChanged();
    }
    
    public void crearAcopios() {
    	controlador.crearAcopios("Acopio Inicial", "Acopio Final");
    }

	public void actualizarTablaStock() {
		if(idSeleccionado!=null && !idSeleccionado.equals(-1)) controlador.mostrarStock(idSeleccionado);
	}
}
