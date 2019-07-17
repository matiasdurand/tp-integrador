package isi.died.tp.dao;

import java.util.List;

import isi.died.tp.dominio.Planta;

public interface PlantaDao {

	public void crear(Planta i);
	public void actualizar(Planta i);
	public void borrar(Integer id);
	public Planta buscar(Integer id);
	public List<Planta> buscarTodas();
	
}
