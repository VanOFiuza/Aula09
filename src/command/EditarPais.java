package command;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.Pais;
import Service.PaisService;

public class EditarPais implements Command {

	@Override
	public void executar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String pId = request.getParameter("id");
		String pNome = request.getParameter("nome");
		String pPop = request.getParameter("pop");
		String pArea = request.getParameter("area");

		int id = -1;
		long pop = -1;
		double area = -1;
		try {
			id = Integer.parseInt(pId);
			
			
		} catch (NumberFormatException e) {
		}
		
		try {
			pop = Long.parseLong(pPop);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			area = Double.parseDouble(pArea);
		} catch (Exception e) {
			// TODO: handle exception
		}


		Pais pais = new Pais();
		pais.setId(id);
		pais.setNome(pNome);
		pais.setPopulacao(pop);
		pais.setArea(area);
		PaisService ps = new PaisService();
		RequestDispatcher view = null;
		HttpSession session = request.getSession();
		
		pais = ps.carregar(pais.getId());
		request.setAttribute("pais", pais);
		view = request.getRequestDispatcher("AlterarPais.jsp");		
		
		view.forward(request, response);

	}

	public int busca(Pais pais, ArrayList<Pais> lista) {
		Pais to;
		for(int i = 0; i < lista.size(); i++){
			to = lista.get(i);
			if(to.getId() == pais.getId()){
				return i;
			}
		}
		return -1;
	}

}
