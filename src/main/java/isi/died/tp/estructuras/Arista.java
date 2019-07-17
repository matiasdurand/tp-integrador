package isi.died.tp.estructuras;

public class Arista<T> {
	private Vertice<T> inicio;
	private Vertice<T> fin;
	private double distancia;
	private double tiempo;
	private double pesoMax;

	public Arista(Vertice<T> ini, Vertice<T> fin, Double distancia, Double tiempo, Double pesoMax){
		this.inicio = ini;
		this.fin = fin;
		this.distancia = distancia;
		this.tiempo = tiempo;
		this.pesoMax = pesoMax;
	} 
	
	/*public Arista(Vertice<T> ini, Vertice<T> fin){
		this();
		this.inicio = ini;
		this.fin = fin;
	}*/

	/*public Arista(Vertice<T> ini,Vertice<T> fin,Number val){
		this(ini,fin);
		this.valor= val;
	}*/
	
	public Vertice<T> getInicio() {
		return inicio;
	}

	public void setInicio(Vertice<T> inicio) {
		this.inicio = inicio;
	}

	public Vertice<T> getFin() {
		return fin;
	}

	public void setFin(Vertice<T> fin) {
		this.fin = fin;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public Double getTiempo() {
		return tiempo;
	}

	public void setTiempo(Double tiempo) {
		this.tiempo = tiempo;
	}

	public Double getPesoMax() {
		return pesoMax;
	}

	public void setPesoMax(Double pesoMax) {
		this.pesoMax = pesoMax;
	}

	/*@Override
	public boolean equals(Object obj) {
		return (obj instanceof Arista<?>) && ((Arista<?>)obj).getValor().equals(this.valor); 
	}*/

	/*@Override
	public String toString() {
		return "( "+this.inicio.getValor()+" --> "+this.fin.getValor()+" )";
	}*/
	
}