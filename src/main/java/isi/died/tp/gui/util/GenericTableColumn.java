package isi.died.tp.gui.util;

public class GenericTableColumn {
	
	 //Nombre con el que se muestra la columna en la tabla
	private String nombreMostrar;
	
	//Nombre del método getter para obtener el valor a mostrar
	private String nombreGetter;
	
	public GenericTableColumn(String nombreMostrar, String nombreGetter) {
		super();
		this.nombreMostrar = nombreMostrar;
		this.nombreGetter = nombreGetter;
	}
	
	public GenericTableColumn() {
		super();
	}

	public String getNombreMostrar() {
		return nombreMostrar;
	}
	
	public void setNombreMostrar(String nombreMostrar) {
		this.nombreMostrar = nombreMostrar;
	}

	public String getNombreGetter() {
		return nombreGetter;
	}

	public void setNombreGetter(String nombreGetter) {
		this.nombreGetter = nombreGetter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreGetter == null) ? 0 : nombreGetter.hashCode());
		result = prime * result + ((nombreMostrar == null) ? 0 : nombreMostrar.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericTableColumn other = (GenericTableColumn) obj;
		if (nombreGetter == null) {
			if (other.nombreGetter != null)
				return false;
		} else if (!nombreGetter.equals(other.nombreGetter))
			return false;
		if (nombreMostrar == null) {
			if (other.nombreMostrar != null)
				return false;
		} else if (!nombreMostrar.equals(other.nombreMostrar))
			return false;
		return true;
	}

}
