package isi.died.tp.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import isi.died.tp.aplicacion.Aplicacion;
import isi.died.tp.controladores.ControladorInsumos;
import isi.died.tp.controladores.ControladorPaneles;
import isi.died.tp.dominio.Insumo.UnidadDeMedida;

public class PanelEditarInsumo extends JPanel {
	
	private JLabel lblPanelTitulo;
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JLabel lblCosto;
	private JLabel lblStock;
	private JLabel lblPeso;
	private JLabel lblDensidad;
	private JTextField nombre;
	private JTextField costo;
	private JTextField stock;
	private JTextField peso;
	private JTextField densidad;
	private JTextArea descripcion;
	private JComboBox<UnidadDeMedida> cmboxUDM;
	private JRadioButton rbtnRefrigerado;
	private JRadioButton rbtnNoRefrigerado;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private ControladorInsumos controlador;
	private Integer idSeleccionado;
	private Boolean insumoLiquido;
	
	public PanelEditarInsumo(Integer idInsumo) {
		super();
		controlador = ControladorInsumos.getInstance();
		controlador.setpEInsumo(this);
		idSeleccionado = idInsumo;
		insumoLiquido = controlador.esLiquido(idInsumo);
		armar();
		configurarEventos();
	}
	
	private void armar() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		//CONFIGURAR APARIENCIA DEL PANEL. MOSTRAR LOS DATOS DEL INSUMO SELECCIONADO.
	}
	
	private void configurarEventos() {
		btnGuardar.addActionListener( e -> {
			if(controlador.validarDatos(insumoLiquido, nombre.getText(), descripcion.getText(), costo.getText(), stock.getText(), peso.getText(), densidad.getText())) {
				if(insumoLiquido) controlador.actualizarInsumo(idSeleccionado, nombre.getText(), descripcion.getText(), (UnidadDeMedida)cmboxUDM.getSelectedItem(), Double.parseDouble(costo.getText()), Integer.parseInt(stock.getText()), -1.00, rbtnRefrigerado.isSelected(), Double.parseDouble(densidad.getText()));
				else controlador.actualizarInsumo(idSeleccionado, nombre.getText(), descripcion.getText(), (UnidadDeMedida)cmboxUDM.getSelectedItem(), Double.parseDouble(costo.getText()), Integer.parseInt(stock.getText()), Double.parseDouble(peso.getText()), rbtnRefrigerado.isSelected(), -1.00);
			}
		});
		btnCancelar.addActionListener( e -> {
			Aplicacion.f.getContentPane().removeAll();
			Aplicacion.f.getContentPane().add(ControladorPaneles.getInstance().getPanelInsumo());
			Aplicacion.f.pack();
		});
	}
}
