package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Model.Pais;
import Service.PaisService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Teste {

	Pais pais, copia;
	PaisService paisService;
	static int id = 0;

	@Before
	public void setUp() throws Exception {
		System.out.println("setup");
		pais = new Pais(id, "Holanda", 10003, 126.021);
		pais.setId(id);
		pais.setNome("Holanda");
		pais.setPopulacao(10003);
		pais.setArea(126.021);

		copia = new Pais(id, "Holanda", 10003, 126.021);
		copia.setId(id);
		copia.setNome("Holanda");
		copia.setPopulacao(10003);
		copia.setArea(126.021);

	  	paisService = new PaisService();
		System.out.println(pais);
		System.out.println(copia);
		System.out.println(id);

	}

	@Test
	public void test00Carregar() {
		System.out.println("carregar");
		Pais fixture = new Pais(1, "Brasil", 9002145, 102.54);
		fixture.setId(1);
		fixture.setNome("Brasil");
		fixture.setPopulacao(9002145);
		fixture.setArea(102.54);
		PaisService novoService = new PaisService();
		Pais novo = novoService.carregar(1);
		assertEquals("Teste Inclusão", novo, fixture);

	}

	@Test
	public void test01Criar() {
		System.out.println("criar");

		id = paisService.criar(pais);
		System.out.println(id);
		copia.setId(id);
		assertEquals("teste criacao", pais, copia);
	}

	@Test
	public void test02Atualizar() {
		System.out.println("atualizar");
		pais.setArea(1212455.2);
		copia.setArea(1212455.2);
		pais.setNome("Peru");
		copia.setNome("Peru");
		paisService.atualizar(pais);
		pais = paisService.carregar(pais.getId());
		assertEquals("testa atualizacao", pais, copia);
	}

	@Test

	public void test03Excluir() {
		System.out.println("excluir");
		copia.setId(-1);
		copia.setNome(null);
		copia.setPopulacao(-1);
		copia.setArea(-1);
		paisService.excluir(id);
		pais = paisService.carregar(pais.getId());
		assertEquals("testa exclusao", pais, copia);
	}

	@Test
	public void test04MaiorPop() {
		System.out.println("Maior populacao");
		paisService.paisHabitantes(pais);
		long pop = pais.getPopulacao();

		copia.setPopulacao(pop);

		assertEquals(pais.getPopulacao(), copia.getPopulacao(), "Testando maior populacao");
	}

	@Test
	public void test05MenorArea() {
		System.out.println("Menor Area");
		paisService.paisMenorArea(copia);
		double area = pais.getArea();
		copia.setArea(area);
		assertEquals(pais.getArea(), copia.getArea(), "Testando menor area");

	}

	@Test

	public void test06Array() {
		System.out.println("Vetor de paises");
		String[] array = paisService.paisVetPaises();
		assertEquals(array.length, 3, "Testando Vetor de paises");
	}

}
