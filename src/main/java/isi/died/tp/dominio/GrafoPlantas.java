package isi.died.tp.dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.OptionalDouble;
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
 
	@Override
	public List<Arista<Planta>> getAristas(){
		return aristas;
	}
	
	/*public void imprimirDistanciaAdyacentes(Planta inicial) {
	  
	  List<Planta> adyacentes = super.getAdyacentes(inicial);
	  
	  for(Planta unAdyacente: adyacentes) { 
		  Arista<Planta> camino = super.buscarArista(inicial, unAdyacente);
		  System.out.println("camino de "+inicial.getNombre()+" a "+ unAdyacente.getNombre()+ " tiene valor de "+ camino.getValor() );            
        } 
	  
    }*/
  
	@Override
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
  	
    // b
  	//public Planta buscarPlanta(Planta inicial, Insumo i) {
  		
  	//} 
    // c
  	//public Planta buscarPlanta(Insumo i) {
  		
  	//}
	
	
	/*public List<List<Planta>> buscarMejorCamino(List<Planta> plantas, Boolean priorizarDistancia) {
		
		Double menorDistancia = Double.MAX_VALUE;
		List<Vertice<Planta>> caminoOptimo = new ArrayList<Vertice<Planta>>();
		
		List<Vertice<Planta>> verticesAIncluir = new ArrayList<Vertice<Planta>>();
		for(Planta p: plantas) verticesAIncluir.add(new Vertice<Planta>(p));
		
		HashMap<List<Vertice<Planta>>, Double> caminos = caminosPosibles(getPlantaAcopioInicial(), getPlantaAcopioFinal(), priorizarDistancia);
		
		for(Entry<List<Vertice<Planta>>, Double> camino: caminos.entrySet()) {
			if(camino.getKey().containsAll(verticesAIncluir) && camino.getValue()<menorDistancia) {
				caminoOptimo = camino.getKey();
				menorDistancia = camino.getValue();
			}
		}
		
		List<List<Planta>> resultado = new ArrayList<List<Planta>>();
		
		if(!caminoOptimo.isEmpty()) {
			resultado.add(caminoOptimo.stream().map(Vertice::getValor).collect(Collectors.toList()));
			return resultado;
		}
		else {
			
		}
		
		else 

		
		
		return null;
	}*/
	
	@Override
	public HashMap<List<Vertice<Planta>>, Double> caminosPosibles(Vertice<Planta> v1, Vertice<Planta> v2){
		HashMap<List<Vertice<Planta>>, Double> caminosPosibles = new HashMap<List<Vertice<Planta>>, Double>();
    	List<Vertice<Planta>> marcados = new ArrayList<Vertice<Planta>>();
      marcados.add(v1);
      buscarCaminosPosibles(v1, v2, marcados, caminosPosibles, 0.0);
      return caminosPosibles;
    }

	private void buscarCaminosPosibles(Vertice<Planta> v1, Vertice<Planta> v2, List<Vertice<Planta>> marcados, HashMap<List<Vertice<Planta>>, Double> caminosPosibles, Double distancia) {
		List<Vertice<Planta>> adyacentes = getAdyacentes(v1);
    	List<Vertice<Planta>> copiaMarcados = null;
    	for(Vertice<Planta> ady: adyacentes) {
    		copiaMarcados = marcados.stream().collect(Collectors.toList());
    		if(ady.equals(v2)) {
    			copiaMarcados.add(v2);
    			caminosPosibles.put(copiaMarcados, buscarArista(v1, ady).getDistancia() + distancia);
    		}
    		else {
    			if(!copiaMarcados.contains(ady)) {
    				copiaMarcados.add(ady);
    				buscarCaminosPosibles(ady, v2, copiaMarcados, caminosPosibles, buscarArista(v1, ady).getDistancia());
    		    }
    		}
    	 }
    }

}
