package isi.died.tp.estructuras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.TreeMap;
import java.util.stream.Collectors;

import isi.died.tp.dominio.Insumo; 
import isi.died.tp.dominio.Planta;
import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.Grafo;
import isi.died.tp.estructuras.Vertice; 
 
public class GrafoPlantas extends Grafo<Planta> {
	
	private static GrafoPlantas _INSTANCIA = null;
	
	private GrafoPlantas() {
		
	}
	
	public static GrafoPlantas getInstance() {
		if(_INSTANCIA==null) _INSTANCIA = new GrafoPlantas();
		return _INSTANCIA;
	}
 
	public List<Arista<Planta>> getAristas(){
		return aristas;
	}
	
	private Vertice<Planta> getAcopioInicial() {
		return vertices.stream().filter(v->v.getValor().getNombre().equals("Acopio Inicial")).collect(Collectors.toList()).get(0);
	}
	
	private Vertice<Planta> getAcopioFinal() {
		return vertices.stream().filter(v->v.getValor().getNombre().equals("Acopio Final")).collect(Collectors.toList()).get(0);
	}
	
	/*public void imprimirDistanciaAdyacentes(Planta inicial) {
	  
	  List<Planta> adyacentes = super.getAdyacentes(inicial);
	  
	  for(Planta unAdyacente: adyacentes) { 
		  Arista<Planta> camino = super.buscarArista(inicial, unAdyacente);
		  System.out.println("camino de "+inicial.getNombre()+" a "+ unAdyacente.getNombre()+ " tiene valor de "+ camino.getValor() );            
        } 
	  
    }*/
  
  	public Planta buscarPlanta(Planta inicial, Insumo i, Integer saltos) {
  		
  		Planta resultado = null;
		Stack<Vertice<Planta>> pendientes = new Stack<Vertice<Planta>>();
		HashSet<Vertice<Planta>> marcados = new HashSet<Vertice<Planta>>();
		marcados.add(getNodo(inicial));
		pendientes.push(getNodo(inicial));
		
		while(saltos>=0 && resultado==null && !pendientes.isEmpty()){
			Vertice<Planta> aRevisar = pendientes.pop();
			if(aRevisar.getValor().necesitaInsumo(i)) resultado = aRevisar.getValor();
			if(resultado==null) {
				List<Vertice<Planta>> adyacentes = getAdyacentes(aRevisar);
				for(Vertice<Planta> v: adyacentes){
					if(!marcados.contains(v)){ 
						pendientes.push(v);
						marcados.add(v);
					}
				}
			}
			saltos--;
		}		
		
		return resultado;
		
  	}
	
	public List<List<Planta>> buscarMejoresCaminos(List<Planta> plantas, Boolean priorizarDistancia) {
		List<Vertice<Planta>> verticesAIncluir = new ArrayList<Vertice<Planta>>();
		for(Planta p: plantas) verticesAIncluir.add(new Vertice<Planta>(p));
		return mejoresCaminos(getAcopioInicial(), getAcopioFinal(), verticesAIncluir, priorizarDistancia);
	}

	private List<List<Planta>> mejoresCaminos(Vertice<Planta> v1, Vertice<Planta> v2, List<Vertice<Planta>> verticesAIncluir, Boolean priorizarDistancia){
		
		List<List<Planta>> caminosOptimos = new ArrayList<List<Planta>>();
		
		HashMap<List<Planta>, Double> caminos = new HashMap<List<Planta>, Double>();
    	
		List<Vertice<Planta>> marcados = new ArrayList<Vertice<Planta>>();
    	marcados.add(v1);
    	
    	if(priorizarDistancia) buscarCaminosD(v1, v2, marcados, verticesAIncluir, caminos, 0.0);
    	else buscarCaminosT(v1, v2, marcados, verticesAIncluir, caminos, 0.0);

    	if(!caminos.isEmpty()) {
    		Double menor = Collections.min(caminos.values());
    		for(Entry<List<Planta>, Double> camino: caminos.entrySet()) if(camino.getValue().equals(menor)) caminosOptimos.add(camino.getKey());
    	}
		
    	
    	return caminosOptimos;
    }

	private void buscarCaminosD(Vertice<Planta> v1, Vertice<Planta> v2, List<Vertice<Planta>> marcados, List<Vertice<Planta>> verticesAIncluir, HashMap<List<Planta>, Double> caminos, Double distancia) {
		List<Vertice<Planta>> adyacentes = getAdyacentes(v1);
    	List<Vertice<Planta>> copiaMarcados = null;
    	for(Vertice<Planta> ady: adyacentes) {
    		copiaMarcados = marcados.stream().collect(Collectors.toList());
    		if(ady.equals(v2)) {
    			copiaMarcados.add(v2);
    			if(copiaMarcados.containsAll(verticesAIncluir)) caminos.put(copiaMarcados.stream().map(Vertice::getValor).collect(Collectors.toList()), distancia + buscarArista(v1, ady).getDistancia());
    		}
    		else {
    			if(!copiaMarcados.contains(ady)) {
    				copiaMarcados.add(ady);
    				buscarCaminosD(ady, v2, copiaMarcados, verticesAIncluir , caminos, distancia + buscarArista(v1, ady).getDistancia());
    		    }
    		}
    	}
    }
	
	private void buscarCaminosT(Vertice<Planta> v1, Vertice<Planta> v2, List<Vertice<Planta>> marcados, List<Vertice<Planta>> verticesAIncluir, HashMap<List<Planta>, Double> caminos, Double tiempo) {
		List<Vertice<Planta>> adyacentes = getAdyacentes(v1);
    	List<Vertice<Planta>> copiaMarcados = null;
    	for(Vertice<Planta> ady: adyacentes) {
    		copiaMarcados = marcados.stream().collect(Collectors.toList());
    		if(ady.equals(v2)) {
    			copiaMarcados.add(v2);
    			if(copiaMarcados.containsAll(verticesAIncluir)) caminos.put(copiaMarcados.stream().map(Vertice::getValor).collect(Collectors.toList()), buscarArista(v1, ady).getTiempo() + tiempo);
    		}
    		else {
    			if(!copiaMarcados.contains(ady)) {
    				copiaMarcados.add(ady);
    				buscarCaminosT(ady, v2, copiaMarcados, verticesAIncluir , caminos, buscarArista(v1, ady).getTiempo());
    		    }
    		}
    	}
    }

	public HashMap<List<Planta>, Double[]> buscarCaminos(Planta origen, Planta destino) {
		Vertice<Planta> vOrigen = new Vertice<Planta>(origen);
		Vertice<Planta> vDestino = new Vertice<Planta>(destino);
		HashMap<List<Planta>, Double[]> caminos = new HashMap<List<Planta>, Double[]>();
		List<Vertice<Planta>> marcados = new ArrayList<Vertice<Planta>>();
    	marcados.add(vOrigen);
    	buscarCaminosAux(vOrigen, vDestino, marcados, caminos, 0.0, 0.0, 0.0);
    	return caminos;
	}
	
	private void buscarCaminosAux(Vertice<Planta> v1, Vertice<Planta> v2, List<Vertice<Planta>> marcados, HashMap<List<Planta>, Double[]> caminos, Double d, Double t, Double p) {
		List<Vertice<Planta>> adyacentes = getAdyacentes(v1);
    	List<Vertice<Planta>> copiaMarcados = null;
    	for(Vertice<Planta> ady: adyacentes) {
    		copiaMarcados = marcados.stream().collect(Collectors.toList());
    		if(ady.equals(v2)) {
    			copiaMarcados.add(v2);
    			Arista<Planta> a = buscarArista(v1, ady);
    			Double[] info = {d+a.getDistancia(), t+a.getTiempo(), p+a.getPesoMax()};
    			caminos.put(copiaMarcados.stream().map(Vertice::getValor).collect(Collectors.toList()), info);
    		}
    		else {
    			if(!copiaMarcados.contains(ady)) {
    				copiaMarcados.add(ady);
    				Arista<Planta> a = buscarArista(v1, ady);
    				buscarCaminosAux(ady, v2, copiaMarcados, caminos, d+a.getDistancia(), t+a.getTiempo(), p+a.getPesoMax());
    		    }
    		}
    	}
    }
	
	public List<Planta> pageRank(){
		Double factorAmortiguacion = 0.5;
		Map<Vertice<Planta>, Double> pageRanks = new HashMap<Vertice<Planta>, Double>();
		Map<Vertice<Planta>, List<Vertice<Planta>>> dirigidos = new HashMap<Vertice<Planta>, List<Vertice<Planta>>>();
		Map<Vertice<Planta>, Integer> salientes = new HashMap<Vertice<Planta>, Integer>();
		for(Vertice<Planta> v: vertices) {
			pageRanks.put(v, 1.0);
			dirigidos.put(v, dirigidos(v));
			salientes.put(v, getAdyacentes(v).size());
		}
		int iteracion=0;
		while(iteracion<15) {
			Map<Vertice<Planta>, Double> auxiliar = pageRanks;
			for(Entry<Vertice<Planta>, Double> entry: pageRanks.entrySet()) {
				Double sumatoria = 0.0;
				for(Vertice<Planta> v: dirigidos.get(entry.getKey())) sumatoria += (auxiliar.get(v)/salientes.get(v));
				entry.setValue((1 - factorAmortiguacion) + factorAmortiguacion*sumatoria);
			}
			iteracion++;
		}
		ValueComparator bvc = new ValueComparator(pageRanks);
		Map<Vertice<Planta>, Double> ordenados = new TreeMap<Vertice<Planta>, Double>(bvc);
		ordenados.putAll(pageRanks);
		System.out.println(ordenados.keySet().stream().map(v->v.getValor()).collect(Collectors.toList()));
		System.out.println(ordenados.values());	
		List<Planta> resultado = ordenados.keySet().stream().map(v->v.getValor()).collect(Collectors.toList());
		return resultado;
	}
	
	private List<Vertice<Planta>> dirigidos(Vertice<Planta> v) {
		List<Vertice<Planta>> dirigidos = new ArrayList<Vertice<Planta>>();
		for(Arista<Planta> a: aristas) {
			if(a.getFin().equals(v)) dirigidos.add(a.getInicio());
		}
		return dirigidos;
	}

	public Double flujoMaximo() {
		GrafoPlantas grafoResidual = new GrafoPlantas();
		Double flujoMaximo = 0.0;
		for(Vertice<Planta> v: vertices) grafoResidual.addNodo(v.getValor());
		for(Arista<Planta> a: aristas) grafoResidual.conectar(a.getInicio(), a.getFin(), a.getDistancia(), a.getTiempo(), a.getPesoMax(), a.getPesoMax(), 0.0);
		List<Arista<Planta>> trayectoriaDeAumento = grafoResidual.trayectoriaDeAumento(getAcopioInicial(), getAcopioFinal());
		while(!trayectoriaDeAumento.isEmpty()) {
			List<Double> capResiduales = trayectoriaDeAumento.stream().map(Arista::getCapResidualOrigen).collect(Collectors.toList());
			Double capMinima = Collections.min(capResiduales);
			for(Arista<Planta> a: grafoResidual.getAristas()) {
				if(trayectoriaDeAumento.contains(a)) a.setCapacidades(a.getCapResidualOrigen()-capMinima, a.getCapResidualDestino()+capMinima);	
			}
			flujoMaximo += capMinima;
			trayectoriaDeAumento = grafoResidual.trayectoriaDeAumento(getAcopioInicial(), getAcopioFinal());
		}
		return flujoMaximo;
	}
	
	private List<Arista<Planta>> trayectoriaDeAumento(Vertice<Planta> origen, Vertice<Planta> destino) {
		List<List<Arista<Planta>>> trayectorias = new ArrayList<List<Arista<Planta>>>();
		List<Arista<Planta>> auxiliar = new ArrayList<Arista<Planta>>();
		List<Vertice<Planta>> marcados = new ArrayList<Vertice<Planta>>();
    	marcados.add(origen);
    	buscarTrayectorias(origen, destino, marcados, auxiliar, trayectorias);
    	if(trayectorias.isEmpty()) return new ArrayList<Arista<Planta>>();
    	else return trayectorias.get(0);
	}
	
	private void buscarTrayectorias(Vertice<Planta> v1, Vertice<Planta> v2, List<Vertice<Planta>> marcados, List<Arista<Planta>> trayectoria, List<List<Arista<Planta>>> trayectorias) {
		List<Vertice<Planta>> adyacentes = getAdyacentes(v1);
    	List<Vertice<Planta>> copiaMarcados = null;
    	List<Arista<Planta>> copiaTrayectoria = null;
    	for(Vertice<Planta> ady: adyacentes) {
    		copiaMarcados = marcados.stream().collect(Collectors.toList());
    		copiaTrayectoria = trayectoria.stream().collect(Collectors.toList());
    		Arista<Planta> a = buscarArista(v1, ady);
    		if(a.getCapResidualOrigen()>0) {
    			if(ady.equals(v2)) {
        			copiaTrayectoria.add(a);
        			trayectorias.add(copiaTrayectoria);
        		}
        		else {
        			if(!copiaMarcados.contains(ady)) {
        				copiaMarcados.add(ady);
        				copiaTrayectoria.add(a);
        				buscarTrayectorias(ady, v2, copiaMarcados, copiaTrayectoria, trayectorias);
        		    }
        		}
    		}
    	}
    }

	public void actualizar(Planta nodo) {
		for(Vertice<Planta> v: vertices) if(v.getValor().getId().equals(nodo.getId())) v.setValor(nodo);
	}
	
}

