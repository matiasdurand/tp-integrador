package isi.died.tp.estructuras;

public class Arista<T> {
	
	private Vertice<T> inicio;
	private Vertice<T> fin;
	private double distancia;
	private double tiempo;
	private double pesoMax;
	private double capResidualOrigen;
	private double capResidualDestino;

	public Arista(Vertice<T> ini, Vertice<T> fin, Double distancia, Double tiempo, Double pesoMax){
		this.inicio = ini;
		this.fin = fin;
		this.distancia = distancia;
		this.tiempo = tiempo;
		this.pesoMax = pesoMax;
		this.capResidualOrigen = -1;
		this.capResidualDestino = -1;
	} 
	
	public double getCapResidualOrigen() {
		return capResidualOrigen;
	}

	public double getCapResidualDestino() {
		return capResidualDestino;
	}

	public void setCapacidades(double capResidualOrigen, double capResidualDestino) {
		this.capResidualOrigen = capResidualOrigen;
		this.capResidualDestino = capResidualDestino;
	}
	
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

	@Override
	public String toString() {
		return "( "+this.inicio.getValor()+" --> "+this.fin.getValor()+" )";
	}
	
}