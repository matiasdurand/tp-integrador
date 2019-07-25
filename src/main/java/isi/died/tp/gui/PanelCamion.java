package isi.died.tp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


import isi.died.tp.gui.util.GenericTableColumn;
import isi.died.tp.controladores.ControladorCamiones;
import isi.died.tp.dominio.Camion;
import isi.died.tp.gui.util.GenericTableModel;

public class PanelCamion extends JPanel {

	//Componentes de la interfaz
	private ControladorCamiones controlador;
	private JLabel lblPanelTitulo;
	private JLabel lblMarca;
	private JLabel lblModelo;
	private JLabel lblAño;
	private JLabel lblDominio;
	private JLabel lblCapacidad;
	private JLabel lblCostoPorKm;
	private JLabel lblAptoParaLiquidos;
	private JTextField marca;
	private JTextField modelo;
	private JTextField año;
	private JTextField dominio;
	private JTextField capacidad;
	private JTextField costoPorKm;
	private JCheckBox aptoParaLiquidos;
	private JTable tablaCamiones;
	GenericTableModel<Camion> gtm;
	private JButton btnRegistrar;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private Integer idSeleccionado;
	
	
	public PanelCamion() {
		super();
		controlador = ControladorCamiones.getInstance();
		controlador.setpCamion(this);
		armar();
		configurarEventos();
	}
	
	private void armar() {
		//Configuracion apariencia del panel
		setLayout(new GridBagLayout());
		int fila = 0;
    	int col = 0;
    	
    	GridBagConstraints c = new GridBagConstraints();    	

    	this.lblPanelTitulo = new JLabel("GESTIÓN DE CAMIONES");
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

    	this.lblMarca = new JLabel("Marca:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.0;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblMarca,c);
    	
    	this.marca = new JTextField(30);
    	this.marca.setEnabled(false);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(marca,c);
    	
    	
    	this.lblModelo = new JLabel("Modelo:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.5;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblModelo,c);
    	
    	this.modelo = new JTextField(30);
    	this.modelo.setEnabled(false);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(modelo,c);
    	
    	this.lblAño = new JLabel("Año:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.5;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblAño,c);
    	
    	this.año = new JTextField(30);
    	this.año.setEnabled(false);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(año,c);
    	
    	//Segunda fila
    	
    	col=0;
    	fila++;
    	
    	this.lblDominio = new JLabel("Dominio:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.5;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblDominio,c);
    	
    	this.dominio = new JTextField(30);
    	this.dominio.setEnabled(false);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(dominio,c);
    	
    	this.lblCapacidad = new JLabel("Capacidad:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.5;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblCapacidad,c);
    	
    	this.capacidad = new JTextField(30);
    	this.capacidad.setEnabled(false);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(capacidad,c);
    	
    	this.lblCostoPorKm = new JLabel("Costo por km:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.5;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblCostoPorKm,c);
    	
    	this.costoPorKm = new JTextField(30);
    	this.costoPorKm.setEnabled(false);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(costoPorKm,c);
    	
    	//Tercera fila
    	
    	col=0;
    	fila++;
    	
    	this.lblAptoParaLiquidos = new JLabel("Apto para líquidos:");
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.5;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(lblAptoParaLiquidos,c);

    	this.aptoParaLiquidos = new JCheckBox();
    	this.aptoParaLiquidos.setEnabled(false);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weightx=0.5;
    	this.add(aptoParaLiquidos,c);
    	
    	this.btnGuardar = new JButton("Guardar");
    	this.btnGuardar.setEnabled(false);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.5;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(btnGuardar,c);
    	
    	this.btnCancelar = new JButton("Cancelar");
    	this.btnCancelar.setEnabled(false);
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=1;
    	c.gridheight=1;
    	c.weightx=0.5;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.NONE;
    	c.insets = new Insets(10, 10, 10, 10);
    	this.add(btnCancelar,c);
    	
    	//Cuarta fila

    	col=0;
    	fila++;
    	
    	c.gridx=col++;
    	c.gridy=fila;
    	c.gridwidth=6;
    	c.anchor = GridBagConstraints.CENTER;
    	c.fill=GridBagConstraints.BOTH;
    	c.weighty=1.0;
    	c.weightx=1.0;
    	
    	this.gtm = crearModeloTabla();
    	this.tablaCamiones = new JTable(this.gtm);
    	this.tablaCamiones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	JScrollPane scrollPane= new  JScrollPane(this.tablaCamiones);
    	this.add(scrollPane,c);
    	
    	
    	//Quinta fila
    	
    	col =0;
    	fila++;
    	
    	JPanel panelBotones = new JPanel();

    	this.btnRegistrar = new JButton("Registrar");
    	this.btnEditar = new JButton("Editar");
    	this.btnEliminar = new JButton("Eliminar");
    	this.btnEditar.setEnabled(false);
    	this.btnEliminar.setEnabled(false);
    	panelBotones.add(btnRegistrar);
    	panelBotones.add(btnEditar);
    	panelBotones.add(btnEliminar);
    	c.fill=GridBagConstraints.BOTH;
    	c.anchor=GridBagConstraints.CENTER;
    	c.gridy=fila;
    	c.gridwidth=6;
    	c.weighty=0.0;
    	c.weightx=0.0;
    	this.add(panelBotones,c);
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(450,250);
    }
	
	private void configurarEventos() {
		//Configuracion de eventos de los botones
		//idSeleccionado -> Registrar=-1; Editar=0;
		this.btnRegistrar.addActionListener( e -> {
			btnRegistrar.setEnabled(false);
    		btnEditar.setEnabled(false);
    		btnEliminar.setEnabled(false);
    		btnGuardar.setEnabled(true);
    		btnCancelar.setEnabled(true);
    		marca.setEnabled(true);
    		marca.setText("");
    		modelo.setEnabled(true);
    		modelo.setText("");
    		año.setEnabled(true);
    		año.setText("");
    		dominio.setEnabled(true);
    		dominio.setText("");
    		capacidad.setEnabled(true);
    		capacidad.setText("");
    		costoPorKm.setEnabled(true);
    		costoPorKm.setText("");
    		aptoParaLiquidos.setEnabled(true);
    		aptoParaLiquidos.setSelected(false);
    		tablaCamiones.setEnabled(false);
    		idSeleccionado=-1;
		});
		
		this.btnEditar.addActionListener( e -> {
			btnRegistrar.setEnabled(false);
    		btnEditar.setEnabled(false);
    		btnEliminar.setEnabled(false);
    		btnGuardar.setEnabled(true);
    		btnCancelar.setEnabled(true);
    		marca.setEnabled(true);
    		modelo.setEnabled(true);
    		año.setEnabled(true);
    		dominio.setEnabled(true);
    		capacidad.setEnabled(true);
    		costoPorKm.setEnabled(true);
    		aptoParaLiquidos.setEnabled(true);
    		tablaCamiones.setEnabled(false);
		});
		
		this.btnEliminar.addActionListener( e -> {
			int confirmar = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el camión seleccionado?", "Eliminar camión", JOptionPane.YES_NO_OPTION);
			if(confirmar==0) {
				controlador.borrarCamion(idSeleccionado);
				btnEliminar.setEnabled(false);
			}
		});
		
		this.btnGuardar.addActionListener( e -> {
			if(validarDatos()) {
				if(idSeleccionado<=0) controlador.crearCamion(marca.getText(), modelo.getText(), Integer.valueOf(año.getText()), dominio.getText(), Integer.valueOf(capacidad.getText()), Double.valueOf(costoPorKm.getText()), aptoParaLiquidos.isSelected());
				else controlador.actualizarCamion(idSeleccionado, marca.getText(), modelo.getText(), Integer.valueOf(año.getText()), dominio.getText(), Integer.valueOf(capacidad.getText()), Double.valueOf(costoPorKm.getText()), aptoParaLiquidos.isSelected());
				btnGuardar.setEnabled(false);
				btnCancelar.setEnabled(false);
				btnRegistrar.setEnabled(true);
				btnEditar.setEnabled(false);
				btnEliminar.setEnabled(false);
				marca.setEnabled(false);
				marca.setText("");
				modelo.setEnabled(false);
				modelo.setText("");
				año.setEnabled(false);
				año.setText("");
				dominio.setEnabled(false);
				dominio.setText("");
				capacidad.setEnabled(false);
				capacidad.setText("");
				costoPorKm.setEnabled(false);
				costoPorKm.setText("");
				aptoParaLiquidos.setEnabled(false);
				aptoParaLiquidos.setSelected(false);
				tablaCamiones.setEnabled(true);
			}
    	});
		
		this.btnCancelar.addActionListener(e -> {
			btnGuardar.setEnabled(false);
    		btnCancelar.setEnabled(false);
    		btnRegistrar.setEnabled(true);
    		btnEditar.setEnabled(false);
    		btnEliminar.setEnabled(false);
    		marca.setEnabled(false);
    		marca.setText("");
    		modelo.setEnabled(false);
    		modelo.setText("");
    		año.setEnabled(false);
    		año.setText("");
    		dominio.setEnabled(false);
    		dominio.setText("");
    		capacidad.setEnabled(false);
    		capacidad.setText("");
    		costoPorKm.setEnabled(false);
    		costoPorKm.setText("");
    		aptoParaLiquidos.setEnabled(false);
    		aptoParaLiquidos.setSelected(false);
    		tablaCamiones.setEnabled(true);
		});
		
		this.tablaCamiones.getSelectionModel().addListSelectionListener(lse -> {
    		if(gtm.getDatos()!=null && !gtm.getDatos().isEmpty() && lse.getFirstIndex()< gtm.getDatos().size()) {
	    		Camion aux = gtm.datos.get(lse.getFirstIndex());
	    		idSeleccionado = aux.getId();
	    		marca.setText(aux.getMarca());
	    		modelo.setText(aux.getModelo());
	    		año.setText(aux.getAño().toString());
	    		dominio.setText(aux.getDominio());
	    		capacidad.setText(aux.getCapacidad().toString());
	    		costoPorKm.setText(aux.getCostoPorKm().toString());
	    		aptoParaLiquidos.setSelected(aux.getAptoParaLiquidos());
	    		btnEditar.setEnabled(true);
	    		btnEliminar.setEnabled(true);
    		}
        });
		
	}
	
	private Boolean validarDatos() {
		Boolean resultado;
		Calendar cal = Calendar.getInstance();
		if(!marca.getText().isEmpty() & !modelo.getText().isEmpty() & Integer.valueOf(año.getText())>1900 & Integer.valueOf(año.getText()) <= cal.get(Calendar.YEAR) && !dominio.getText().isEmpty() & Integer.valueOf(capacidad.getText())>0 & Double.valueOf(costoPorKm.getText())>0) {
			resultado = true;
		}
		else {
			resultado=false;
			JOptionPane.showMessageDialog(null, "Se han ingresado datos con un formato no válido");
		}
		return resultado;
	}
	
    private GenericTableModel<Camion> crearModeloTabla(){
    	this.gtm = new GenericTableModel<Camion>();
    	List<GenericTableColumn> lista = new ArrayList<GenericTableColumn>();
    	lista.add(new GenericTableColumn("Id" , "getId"));
    	lista.add(new GenericTableColumn("Marca" , "getMarca"));
    	lista.add(new GenericTableColumn("Modelo" , "getModelo"));
    	lista.add(new GenericTableColumn("Dominio" , "getDominio"));
    	lista.add(new GenericTableColumn("Año" , "getAño"));
    	lista.add(new GenericTableColumn("Capacidad" , "getCapacidad"));
    	lista.add(new GenericTableColumn("Costo por km" , "getCostoPorKm"));
    	lista.add(new GenericTableColumn("Apto para líquidos" , "getAptoParaLiquidos"));
    	gtm.setColumnas(lista);
    	return gtm;
    }
    
    public void actualizarDatosTabla(List<Camion> lista) {
    	this.gtm.setDatos(lista);
    	this.gtm.fireTableDataChanged();
    }
}
