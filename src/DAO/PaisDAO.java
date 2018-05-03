package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Factory.ConnectionFactory;
import Model.Pais;

public class PaisDAO {
	// CRUDS
	public int criar(Pais pais) {
		String sqlInsert = "INSERT INTO pais(nome,populacao,area) VALUES(?,?,?)";
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
			stm.setString(1, pais.getNome());
			stm.setLong(2, pais.getPopulacao());
			stm.setDouble(3, pais.getArea());
			stm.execute();

			String sqlQuery = "SELECT LAST_INSERT_ID()";
			try (PreparedStatement stm2 = conn.prepareStatement(sqlQuery); ResultSet rs = stm2.executeQuery();) {
				if (rs.next()) {
					pais.setId(rs.getInt(1));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pais.getId();

	}

	public void atualizar(Pais pais) {
		String sqlUpdate = "UPDATE pais SET nome=?,populacao=?,area=? WHERE  id=?";

		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
			stm.setString(1, pais.getNome());
			stm.setLong(2, pais.getPopulacao());
			stm.setDouble(3, pais.getArea());
			stm.setInt(4, pais.getId());
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excluir(int id) {
		String sqlDelete = "DELETE FROM pais WHERE id = ?";

		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
			stm.setInt(1, id);
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Pais carregar(int id) {

		Pais pais = new Pais();
		pais.setId(id);
		String sqlSelect = "SELECT nome,populacao,area FROM pais WHERE pais.id =?";

		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {

			stm.setInt(1, pais.getId());
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					pais.setNome(rs.getString("nome"));
					pais.setPopulacao(rs.getLong("populacao"));
					pais.setArea(rs.getDouble("area"));
				} else {
					pais.setId(-1);
					pais.setNome(null);
					pais.setPopulacao(-1);
					pais.setArea(-1);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return pais;

	}

	public void paisHabitantes(Pais pais) {

		String sqlGet = "Select * from pais where populacao = (Select Max(populacao) from pais)";

		try (Connection conn = ConnectionFactory.obtemConexao()) {
			PreparedStatement stm = conn.prepareStatement(sqlGet);

			ResultSet rs = stm.executeQuery();

			if (rs.next()) {
				// enviando para os metodos set o valor coletado da query
				pais.setNome(rs.getString("nome"));
				pais.setPopulacao(rs.getLong("populacao"));
				pais.setId(rs.getInt("id"));
				pais.setArea(rs.getDouble("area"));
			} else {
				System.out.println("Error Result Set");
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void paisMenorArea(Pais pais) {

		String sqlGet = "Select * from pais where area = (Select Min(area) from pais)";

		try (Connection conn = ConnectionFactory.obtemConexao()) {
			PreparedStatement stm = conn.prepareStatement(sqlGet);

			ResultSet rs = stm.executeQuery();

			if (rs.next()) {
				// enviando para os metodos set o valor coletado da query
				pais.setNome(rs.getString("nome"));
				pais.setPopulacao(rs.getLong("populacao"));
				pais.setId(rs.getInt("id"));
				pais.setArea(rs.getDouble("area"));
			} else {
				System.out.println("Error Result Set");
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public String[] paisVetPaises() {

		String sqlGet = "Select nome from pais order by nome";
		String[] array = new String[3];
		int cont = 0;

		try (Connection conn = ConnectionFactory.obtemConexao()) {
			PreparedStatement stm = conn.prepareStatement(sqlGet);

			ResultSet rs = stm.executeQuery();

			while (rs.next() && cont < 3) {

				array[cont] = rs.getString("nome");
				cont++;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return array;
	}

	public ArrayList<Pais> listarPaises() {
		Pais pais;
		ArrayList<Pais> lista = new ArrayList<>();
		String sqlSelect = "SELECT id, nome, populacao, area FROM pais ";
		// usando o try with resources do Java 7, que fecha o que abriu
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			try (ResultSet rs = stm.executeQuery();) {
				while (rs.next()) {
					pais = new Pais();
					pais.setId(rs.getInt("id"));
					pais.setNome(rs.getString("nome"));
					pais.setPopulacao(rs.getLong("populacao"));
					pais.setArea(rs.getDouble("area"));
					lista.add(pais);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return lista;
	}

	public ArrayList<Pais> listarPaises(String chave) {
		Pais pais;
		ArrayList<Pais> lista = new ArrayList<>();
		String sqlSelect = "SELECT id, nome, populacao, area FROM pais where upper(nome) like ?";
		// usando o try with resources do Java 7, que fecha o que abriu
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			stm.setString(1, "%" + chave.toUpperCase() + "%");
			try (ResultSet rs = stm.executeQuery();) {
				while (rs.next()) {
					pais = new Pais();
					pais.setId(rs.getInt("id"));
					pais.setNome(rs.getString("nome"));
					pais.setPopulacao(rs.getLong("populacao"));
					pais.setArea(rs.getDouble("area"));
					lista.add(pais);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return lista;
	}

}
