package isi.died.tp.estructuras;

import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dominio.Insumo;

public abstract class Arbol<E extends Comparable<E>> {

	protected E valor;
	

	public abstract List<E> preOrden();

	public abstract List<E> inOrden();

	public abstract List<E> posOrden();

	public abstract boolean esVacio();

	public abstract E valor();

	public abstract Arbol<E> izquierdo();

	public abstract Arbol<E> derecho();
	
	public abstract boolean contiene(E unValor);

	public abstract boolean equals(Arbol<E> unArbol);
	
	public abstract void agregar(E a);

	public abstract int profundidad();

	public abstract int cuentaNodosDeNivel(int nivel);

	public abstract boolean esCompleto();

	public abstract boolean esLleno();
	
	public abstract ArrayList<Insumo> rango(String nombre, Double costoMinimo, Double costoMaximo, Integer stockMinimo, Integer stockMaximo);
	
}