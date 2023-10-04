package gt.od;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTML;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HistoricoParser {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("USAGE: you must pass the name of the html file as parameter");
			System.exit(0);
		}
		// String bypass = "../historicos/Larissa.html";
		String bypass = args[0];

		// Document doc = Jsoup.parse(new File(args[0]), "iso-8859-1");
		Document doc = Jsoup.parse(new File(bypass), "UTF-8");
		Element dadosAluno = new Element("<a href='#'>fudeu</a>");
		Element dadosCurso = new Element("<a href='#'>fudeu</a>");
		Element numGerais = new Element("<a href='#'>fudeu</a>");
		List<Element> disciplinasNaMatriz = new ArrayList<>();
		List<Element> disciplinasForaDaMatriz = new ArrayList<>();
		List<Element> infoCH = new ArrayList<>();

		// crio o objeto aluno
		Aluno aluno = new Aluno();

		int i = 0;
		int STATE = 0;
		// classificando os elementos da página HTML

		// pegar o primeiro elemento com a tag <dl>
		Element dl = doc.getElementsByTag("dl").first();

		// pegar os elementos dd
		// Elements dd = dl.getElementsByTag("dd");

		// processar dados do aluno usando
		processarDadosAluno(aluno, dl);

		Elements h3Elements = doc.getElementsByTag("h3");


		
		for (Element h3 : h3Elements) {
			if (h3.text().contains("Componentes Curriculares")) {

				System.out.println(h3.text() + "\n");
				Element div = h3.nextElementSibling();

				// Selecione o elemento <div> que contém o elemento filho <p>
				Element divDaTabela = div.select("div").first(); 
				Element tabela = divDaTabela.select("table").first();

				Element thead = tabela.select("thead").first();
				if (thead != null) {
					Elements linhasCabecalho = thead.select("tr");
					for (Element linhaCabecalho : linhasCabecalho) {
						// Acesse as células do cabeçalho e faça o que desejar
						Elements celulasCabecalho = linhaCabecalho.select("th");
						for (Element celulaCabecalho : celulasCabecalho) {
							System.out.println("Cabeçalho: " + celulaCabecalho.text());
						}
					}
				}

				// Acesse o elemento <tbody> e as linhas dos dados
				Element tbody = tabela.select("tbody").first();
				if (tbody != null) {
					Elements linhasDados = tbody.select("tr");
					for (Element linhaDados : linhasDados) {
						// Acesse as células dos dados e faça o que desejar
						Elements celulasDados = linhaDados.select("td");
						for (Element celulaDado : celulasDados) {
							System.out.println("Dado: " + celulaDado.text());
						}
					}
				}
				// processarDisciplina(aluno, h3);
			}
		}

		// ----------------------------

		// Selecione os elementos <div> dentro de <dl>
		// Elements divElements = dl.select("div");

		// // Itere sobre os elementos <div>
		// for (Element div : divElements) {
		// // Selecione os elementos <dt> e <dd> dentro de <div>
		// Element dtElement = div.selectFirst("dt");
		// Element ddElement = div.selectFirst("dd");

		// // Verifique se os elementos <dt> e <dd> foram encontrados
		// if (dtElement != null && ddElement != null) {
		// // Imprima o texto de <dt> e <dd>
		// System.out.println("Texto de <dt>: " + dtElement.text().trim());
		// System.out.println("Texto de <dd>: " + ddElement.text().trim());
		// }
		// }

		// // mostrar os elementos dd
		// for (Element e : dd) {
		// System.out.println(e.text());
		// }

		// for (Element e : doc.getAllElements()) { // conteudoTexto eh a class de um tr
		for (Element e : doc.select("div.table-responsive tr")) {
			// if (e.hasAttr("class") && e.attr("class").equals("table-responsive")) {
			// System.out.println(e.toString() + "\n");
			// IDENTIFICANDO A PARTE INICIAL.
			// if (e.classNames().contains("conteudoTexto") &&
			// !e.attr("bgcolor").equals("")) {
			// if (e.tag().toString().equals("table")) {

			// System.out.println(e.toString()+ "\n");

			// if (i <= 3) {
			// switch (i) {
			// case 0:
			// dadosAluno = e;
			// System.out.println("********************************** " + i + "
			// **********************************");
			// System.out.println(e.toString());
			// i++;
			// processarDadosAluno(aluno, e);
			// break;
			// case 1:
			// dadosCurso = e;
			// System.out.println("********************************** " + i + "
			// **********************************");
			// System.out.println(e.toString());
			// processarDadosCurso(aluno, e);
			// i++;
			// break;
			// case 2:
			// numGerais = e;
			// System.out.println("********************************** " + i + "
			// **********************************");
			// System.out.println(e.toString());
			// processarNumerosGerais(aluno, e);
			// i++;
			// break;
			// case 3: // faz nada
			// break;
			// }
			// }
			switch (STATE) {
				case 0:
					break;// so ignora
				case 1:
					disciplinasNaMatriz.add(e);
					// System.out.println(e.toString());
					// System.out.println("**********************************");
					break;
				case 2:
					disciplinasForaDaMatriz.add(e);
					// System.out.println(e.toString());
					// System.out.println("**********************************");
					break;
				case 3:
					infoCH.add(e);
					// System.out.println(e.toString());
					// System.out.println("**********************************");
					break;
			}
			// }
			// }

			if (e.classNames().contains("conteudoTitulo")) {
				if (e.text().contains("Disciplinas cursadas na matriz curricular:")) {
					// System.out.println("ENTRANDO NO ESTADO 1");
					// System.out.println( e.toString());
					STATE = 1;
				} else if (e.text().contains("Disciplinas cursadas fora da matriz curricular:")) {
					// System.out.println("ENTRANDO NO ESTADO 2");
					// System.out.println( e.toString());
					STATE = 2;
				} else if (e.text().contains("Carga Hor")) {
					// System.out.println("ENTRANDO NO ESTADO 3");
					// System.out.println( e.toString());
					STATE = 3;
				}
			}
		}
		String[] pathExploded = bypass.split("/");

		// System.out.print("MATRICULA: " + pathExploded[pathExploded.length
		// -1].replace(".html", ""));
		// System.out.print(", No. Materias na Matriz: " + disciplinasNaMatriz.size());
		for (Element e : disciplinasNaMatriz) { // conteudoTexto eh a class de um tr
			processarDisciplina(aluno, e);
		}

		// System.out.print(", Fora da Matriz: " + disciplinasForaDaMatriz.size());
		for (Element e : disciplinasForaDaMatriz) { // conteudoTexto eh a class de um tr
			System.out.print("ATENCAO: nao registrada: ");
			System.out.println(e.toString());
		}

		// System.out.println(". Registrando CH OBR e Estagio.");
		for (Element e : infoCH) { // conteudoTexto eh a class de um tr
			if (e.text().contains("Cumprida")) { // se o primeiro td tem o texto "Cumprida", processa a linha
				processarInfoCH(aluno, e);
			}
		}

		aluno.salvar();
	}

	// public static void processarDadosAluno(Aluno aluno, Element tr) {
	// ArrayList<Element> infoAluno = new ArrayList<>();
	// // System.out.println("**********************************");
	// for (Element e : tr.getElementsByTag("td")) {
	// infoAluno.add(e);
	// }
	// aluno.setMatricula(infoAluno.get(0).text().trim());
	// aluno.setNome(infoAluno.get(1).text().trim());
	// aluno.setDataNasc(infoAluno.get(2).text().trim());
	// }

	// FEITO
	public static void processarDadosAluno(Aluno aluno, Element dl) {
		ArrayList<Element> infoAluno = new ArrayList<>();
		System.out.println("**********************************");
		for (Element e : dl.getElementsByTag("dd")) {
			infoAluno.add(e);
		}

		aluno.setNome(infoAluno.get(0).text().trim());
		aluno.setMatricula(infoAluno.get(1).text().trim());
		aluno.setIngresso(infoAluno.get(2).text().trim());
		aluno.setEmail(infoAluno.get(3).text().trim());
		aluno.setCpf(infoAluno.get(5).text().trim());
		aluno.setPeriodo(infoAluno.get(6).text().trim());
		aluno.setCurso(infoAluno.get(8).text().trim());
		aluno.setMatriz(infoAluno.get(9).text().trim());
		aluno.setSituacaoSistemica(infoAluno.get(11).text().trim());
	}

	public static void processarDadosCurso(Aluno aluno, Element tr) {
		ArrayList<Element> infoCurso = new ArrayList<>();
		// System.out.println("**********************************");
		for (Element e : tr.getElementsByTag("td")) {
			infoCurso.add(e);
		}
		aluno.setCurso(infoCurso.get(0).text().trim());
	}

	public static void processarNumerosGerais(Aluno aluno, Element tr) {
		ArrayList<Element> infoGeral = new ArrayList<>();
		// System.out.println("**********************************");
		for (Element e : tr.getElementsByTag("td")) {
			infoGeral.add(e);
		}
		// aluno.setCoeficienteRendimento(Float.parseFloat(infoGeral.get(0).text().trim()));
		// aluno.setCoeficienteProgressao(Float.parseFloat(infoGeral.get(1).text().trim()));
	}

	public static void processarDisciplina(Aluno aluno, Element tr) {
		ArrayList<Element> infoDisciplina = new ArrayList<>();
		// System.out.println("**********************************");
		for (Element e : tr.getElementsByTag("td")) {
			infoDisciplina.add(e);
			// System.out.println(e + "\n");
		}

		Disciplina disciplina = new Disciplina();
		// processando ano
		String ano = infoDisciplina.get(0).text().trim();
		if (ano.isEmpty()) {
			ano = "1900";
		} else if (ano.contains("/")) {
			ano = ano.split("/")[0];
		}
		disciplina.setAno(Integer.parseInt(ano));
		String periodoStr = infoDisciplina.get(1).text().trim();
		// em alguns casos de equivalencia, o periodo pode aparecer vazio
		disciplina.setPeriodo(Integer.parseInt(!periodoStr.isEmpty() ? periodoStr : "-1"));

		disciplina.setCodigo(infoDisciplina.get(2).text().trim());
		disciplina.setNomeDisciplina(infoDisciplina.get(3).text().trim());
		disciplina.setChPrevista(Integer.parseInt(infoDisciplina.get(5).text().trim()));
		String notas = infoDisciplina.get(7).text().trim();
		String nfe1 = "0", nfe2 = "0";
		if (notas.contains("-")) {
			nfe1 = notas.split("-")[0].trim();
			nfe2 = notas.split("-")[1].trim();
		}
		disciplina.setNf1e(Float.parseFloat(nfe1));
		disciplina.setNf1e(Float.parseFloat(nfe2));
		String creditosStr = infoDisciplina.get(6).text().trim();
		// disciplinas em que foi feito equivalencia creditos é zero. Olhar disciplinas
		// fora da matriz
		disciplina.setCreditos(Integer.parseInt(!creditosStr.isEmpty() ? creditosStr : "0"));

		String freqenciaStr = infoDisciplina.get(8).text().trim();
		// disciplinas nao cursada tem frequencia vazia.
		disciplina.setFrequencia(Float.parseFloat(!freqenciaStr.isEmpty() ? freqenciaStr : "0"));
		disciplina.setSituacao(infoDisciplina.get(9).text().trim());

		aluno.getDisciplinas().add(disciplina); // ??
	}

	public static void processarInfoCH(Aluno aluno, Element tr) {
		ArrayList<Element> infoCh = new ArrayList<>();
		// System.out.println("**********************************");
		for (Element e : tr.getElementsByTag("td")) {
			infoCh.add(e);
		}
		// aluno.setChObrigatoriaExigida(Integer.parseInt(infoCh.get(1).text().trim()));
		// aluno.setChEstagioExigida(Integer.parseInt(infoCh.get(1).text().trim()));
		// aluno.setChTotalExigida(Integer.parseInt(infoCh.get(1).text().trim()));
		// aluno.setChObrigatoriaCumprida(Integer.parseInt(infoCh.get(1).text().trim()));
		// aluno.setChEstagioCumprida(Integer.parseInt(infoCh.get(3).text().trim()));
		// aluno.setChTotalCumprida(Integer.parseInt(infoCh.get(7).text().trim()));

	}

}
