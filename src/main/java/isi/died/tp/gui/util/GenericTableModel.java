package isi.died.tp.gui.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class GenericTableModel<T> extends AbstractTableModel {
	
	public List<T> datos;
	public List<GenericTableColumn> columnas;

	
	public int getRowCount() {
		if(datos==null) return 0;
		return datos.size();
	}

	public int getColumnCount() {
		if (columnas == null ) return 0;
		return columnas.size();
	}
	
	@Override
	public String getColumnName(int column) {
		return this.columnas.get(column).getNombreMostrar();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		T fila = datos.get(rowIndex);
		Class<? extends Object> clase = fila.getClass();
		String nombreMetodo = this.columnas.get(columnIndex).getNombreGetter();
		Object resultado = null;
		try {
			Method metodo = clase.getDeclaredMethod(nombreMetodo);
			resultado = metodo.invoke(fila);
		} catch (SecurityException e) { 
				e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public List<T> getDatos() {
		return datos;
	}

	public void setDatos(List<T> datos) {
		this.datos = datos;
	}

	public List<GenericTableColumn> getColumnas() {
		return columnas;
	}

	public void setColumnas(List<GenericTableColumn> columnas) {
		this.columnas = columnas;
	}
	
}