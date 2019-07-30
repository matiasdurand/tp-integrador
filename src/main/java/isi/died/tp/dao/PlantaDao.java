package isi.died.tp.dao;

import java.util.List;

import isi.died.tp.dominio.Planta;

public interface PlantaDao {

	public Planta crear(Planta i);
	public Planta actualizar(Planta i);
	public void borrar(Integer id);
	public Planta buscar(Integer id);
	public void cargarStock(Integer idPlanta, Integer idInsumo, Integer cantidad, Integer puntoPedido);
	public List<Planta> buscarTodas();
	
}
