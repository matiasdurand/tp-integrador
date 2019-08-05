package isi.died.tp.estructuras;

import java.util.Comparator;
import java.util.Map;

import isi.died.tp.dominio.Planta;

public class ValueComparator implements Comparator<Vertice<Planta>> {
	 
	Map<Vertice<Planta>, Double> base;

	public ValueComparator(Map<Vertice<Planta>, Double> base) {
		this.base = base;
	}

	public int compare(Vertice<Planta> v1, Vertice<Planta> v2) {
		if (base.get(v1) >= base.get(v2)) return -1;
	    else return 1;
	}
	
}
