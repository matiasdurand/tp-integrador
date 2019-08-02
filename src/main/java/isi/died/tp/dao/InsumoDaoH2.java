package isi.died.tp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dominio.Insumo;
import isi.died.tp.dominio.Insumo.UnidadDeMedida;
import isi.died.tp.dominio.Liquido;

public class InsumoDaoH2 implements InsumoDao{

	private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS Insumo (ID IDENTITY NOT NULL PRIMARY KEY, NOMBRE VARCHAR(30), DESCRIPCION VARCHAR(140), UNIDAD_DE_MEDIDA ENUM('Gramo', 'Kilogramo', 'Metro', 'MetroCuadrado', 'MetroCubico', 'Litro', 'Pieza'), COSTO DOUBLE, STOCK INTEGER, PESO DOUBLE, ES_REFRIGERADO BOOLEAN, DENSIDAD DOUBLE)";
	private static final String SQL_INSERT = "INSERT INTO Insumo (NOMBRE,DESCRIPCION,UNIDAD_DE_MEDIDA,COSTO,STOCK,PESO,ES_REFRIGERADO,DENSIDAD) VALUES (?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE Insumo SET NOMBRE =?, DESCRIPCION=?, UNIDAD_DE_MEDIDA=?, COSTO=?, STOCK=?, PESO=?, ES_REFRIGERADO=?, DENSIDAD=? WHERE ID = ?";
	private static final String SQL_DELETE = "DELETE FROM Insumo WHERE ID =?";
	private static final String SQL_SELECT = "SELECT ID, NOMBRE, DESCRIPCION, UNIDAD_DE_MEDIDA, COSTO, STOCK, PESO, ES_REFRIGERADO, DENSIDAD FROM Insumo";
		
	public InsumoDaoH2() {
		try(Connection conn = DBConnection.get()){
			try(Statement st = conn.createStatement()){
				st.executeUpdate(SQL_CREATE);				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public Insumo crear(Insumo i) {
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_INSERT,PreparedStatement.RETURN_GENERATED_KEYS)){
				pst.setString(1, i.getNombre());
				pst.setString(2, i.getDescripcion());
				pst.setString(3, i.getUnidadDeMedida().toString());
				pst.setDouble(4, i.getCosto());
				pst.setInt(5, i.getStock());
				pst.setDouble(6, i.getPeso());
				pst.setBoolean(7, i.getEsRefrigerado());
				pst.setDouble(8, i.getDensidad());
				pst.executeUpdate();
				try(ResultSet rs = pst.getGeneratedKeys()){
					if(rs.next()) {
						i.setId(rs.getInt(1));
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return i;
	}
	
	@Override
	public Insumo actualizar(Insumo i) {
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_UPDATE)){
				pst.setString(1, i.getNombre());
				pst.setString(2, i.getDescripcion());
				pst.setString(3, i.getUnidadDeMedida().toString());
				pst.setDouble(4, i.getCosto());
				pst.setInt(5, i.getStock());
				pst.setDouble(6, i.getPeso());
				pst.setBoolean(7, i.getEsRefrigerado());
				pst.setDouble(8, i.getDensidad());
				pst.setInt(9, i.getId());
				pst.executeUpdate();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return i;
	}

	@Override
	public void borrar(Integer id) {
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_DELETE)){
				pst.setInt(1, id);
				pst.executeUpdate();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public Insumo buscar(Integer id) {
		Insumo resultado = null;
		String sqlById = SQL_SELECT + " WHERE ID = ?";
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(sqlById)){
				pst.setInt(1, id);
				try(ResultSet rs = pst.executeQuery()){
					if(rs.next()) {
						if(rs.getDouble("DENSIDAD")>0) {
							resultado = new Liquido();
							resultado.setEsLiquido(true);
						}
						else { 
							resultado = new Insumo();
							resultado.setEsLiquido(false);
						}
						resultado.setId(rs.getInt("ID"));
						resultado.setNombre(rs.getString("NOMBRE"));
						resultado.setDescripcion(rs.getString("DESCRIPCION"));
						resultado.setUnidadDeMedida(rs.getString("UNIDAD_DE_MEDIDA"));
						resultado.setCosto(Double.valueOf(rs.getString("COSTO")));
						resultado.setStock(rs.getInt("STOCK"));
						resultado.setPeso(rs.getDouble("PESO"));
						resultado.setEsRefrigerado(rs.getBoolean("ES_REFRIGERADO"));
						resultado.setDensidad(rs.getDouble("DENSIDAD"));
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado;
	}
	
	@Override
	public List<Insumo> buscarTodos() {
		List<Insumo> resultado = new ArrayList<Insumo>();
		try(Connection conn = DBConnection.get()){
			try(PreparedStatement pst = conn.prepareStatement(SQL_SELECT)){
				try(ResultSet rs = pst.executeQuery()){
					while(rs.next()) {
						Insumo aux;
						if(rs.getDouble("DENSIDAD")>0) {
							aux = new Liquido();
							aux.setEsLiquido(true);
						}
						else {
							aux = new Insumo();
							aux.setEsLiquido(false);
						}
						aux.setId(rs.getInt("ID"));
						aux.setNombre(rs.getString("NOMBRE"));
						aux.setDescripcion(rs.getString("DESCRIPCION"));
						aux.setUnidadDeMedida(rs.getString("UNIDAD_DE_MEDIDA"));
						aux.setCosto(Double.valueOf(rs.getString("COSTO")));
						aux.setStock(rs.getInt("STOCK"));
						aux.setPeso(rs.getDouble("PESO"));
						aux.setEsRefrigerado(rs.getBoolean("ES_REFRIGERADO"));
						aux.setDensidad(rs.getDouble("DENSIDAD"));
						resultado.add(aux);
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado;	
	}
}
