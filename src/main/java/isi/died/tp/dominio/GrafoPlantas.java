package isi.died.tp.dominio;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import isi.died.tp.dominio.Insumo; 
import isi.died.tp.dominio.Planta;
import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.Grafo;
import isi.died.tp.estructuras.Vertice; 
 
public class GrafoPlantas extends Grafo<Planta> { 
 
	public void imprimirDistanciaAdyacentes(Planta inicial) {
	  
	  List<Planta> adyacentes = super.getAdyacentes(inicial);
	  
	  for(Planta unAdyacente: adyacentes) { 
		  Arista<Planta> camino = super.buscarArista(inicial, unAdyacente);
		  System.out.println("camino de "+inicial.getNombre()+" a "+ unAdyacente.getNombre()+ " tiene valor de "+ camino.getValor() );            
        } 
	  
    }
  
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

}
