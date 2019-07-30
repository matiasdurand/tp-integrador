package isi.died.tp.estructuras;

import java.util.Comparator;
import java.util.Map;

import isi.died.tp.dominio.Planta;

public class ValueComparator implements Comparator<Vertice<Planta>> {
	 
	Map<Vertice<Planta>, Double> base;

	public ValueComparator(Map<Vertice<Planta>, Double> base) {
		this.base = base;
	}

	public int compare(Vertice<Planta> a, Vertice<Planta> b) {
		if (base.get(a) >= base.get(b)) return -1;
	    else return 1;
	}
}
