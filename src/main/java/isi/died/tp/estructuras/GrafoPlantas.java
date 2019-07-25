package isi.died.tp.estructuras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;
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
		return vertices.stream().filter(v->v.getValor().getNombre().equals("PlantaAcopioI")).collect(Collectors.toList()).get(0);
	}
	
	private Vertice<Planta> getAcopioFinal() {
		return vertices.stream().filter(v->v.getValor().getNombre().equals("PlantaAcopioF")).collect(Collectors.toList()).get(0);
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
	
}

