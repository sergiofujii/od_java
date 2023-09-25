package gt.od;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Aluno {

	private Connection connection = null;
	private int id;
	private String matricula;
	private String nome;
	// private String dataNasc;
	private String curso;
	// private float  coeficienteRendimento;
	// private float  coeficienteProgressao;
	// private int chTotalCumprida;
	// private int chEstagioCumprida;
	// private int chObrigatoriaCumprida;

	private String ingresso;
	private String periodo;
	private String matriz;
	private String cpf;
	private String email;
	private String situacao_sistemica;


	private List<Disciplina> disciplinas;

	public void setId(int id) {
		this.id = id;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	// Getter para o atributo "ingresso"
	public String getIngresso() {
		return ingresso;
	}

	// Setter para o atributo "ingresso"
	public void setIngresso(String ingresso) {
		this.ingresso = ingresso;
	}

	// Getter para o atributo "periodo"
	public String getPeriodo() {
		return periodo;
	}

	// Setter para o atributo "periodo"
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	// Getter para o atributo "matriz"
	public String getMatriz() {
		return matriz;
	}

	// Setter para o atributo "matriz"
	public void setMatriz(String matriz) {
		this.matriz = matriz;
	}

	// Getter para o atributo "cpf"
	public String getCpf() {
		return cpf;
	}

	// Setter para o atributo "cpf"
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	// Getter para o atributo "email"
	public String getEmail() {
		return email;
	}

	// Setter para o atributo "email"
	public void setEmail(String email) {
		this.email = email;
	}

	// Getter para o atributo "situacao_sistemica"
	public String getSituacaoSistemica() {
		return situacao_sistemica;
	}

	// Setter para o atributo "situacao_sistemica"
	public void setSituacaoSistemica(String situacao_sistemica) {
		this.situacao_sistemica = situacao_sistemica;
	}

	private void closeConnection() {
		try {
			connection.close();
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}
	}

    public Aluno(){
		disciplinas = new ArrayList<>();

    }
	
	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public List<Disciplina> getDisciplinas() {
		return this.disciplinas;
	}

	public void salvar(){
		openConnection();
		salvarAluno();
		// salvarDisciplinasCursadas();
		closeConnection();
	}

	private void salvarAluno(){
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			String query = "select * from alunos where matricula='" + this.matricula + "';";
			rs = stmt.executeQuery(query);
			if (!rs.next()) {
				query = "INSERT INTO alunos (nome, matricula, curso, ingresso, periodo, matriz, cpf, email, situacao_sistemica) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, this.nome);
				ps.setString(2, this.matricula);
				ps.setString(3, this.curso);
				ps.setString(4, this.ingresso);
				ps.setString(5, this.periodo);
				ps.setString(6, this.matriz);
				ps.setString(7, this.cpf);
				ps.setString(8, this.email);
				ps.setString(9, this.situacao_sistemica);


				ps.executeUpdate();
				ResultSet generatedKeys = ps.getGeneratedKeys();
                if(generatedKeys.next()){
                    this.id = generatedKeys.getInt(1);
                }
			} else {
				// System.out.println("ALUNO PRE EXISTENTE");
				//then load its ID
				this.id = rs.getInt("id");
			}
		} catch (SQLException ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
		}
	}

	private void salvarDisciplinasCursadas(){
		Statement stmt = null;
		ResultSet rs = null;

		for (Disciplina disciplina : disciplinas) {
			// salva no banco se nao existe, e atualiza o ID.
			disciplina.salvar(this.connection); 
			// agora registra na tabela aluno_cursa_disciplina
			try {
				stmt = connection.createStatement();
				String query = "select * from aluno_cursa_disciplina where aluno_id=" + this.id 
							+ " and disciplina_id="+disciplina.getId()+" and ano="+disciplina.getAno()+";";
				rs = stmt.executeQuery(query);
				if (!rs.next()) {
					query = "INSERT INTO `aluno_cursa_disciplina` (`aluno_id`, `disciplina_id`, `ano`, `nf1e`, `nf2e`"+
					", `frequencia`, `situacao`) VALUES (?, ?, ?, ?, ?, ?, ?)";
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setInt(1, this.id);
					ps.setInt(2, disciplina.getId());
					ps.setInt(3, disciplina.getAno());
					ps.setFloat(4, disciplina.getNf1e());
					ps.setFloat(5, disciplina.getNf2e());
					ps.setFloat(6, disciplina.getFrequencia());
					ps.setString(7, disciplina.getSituacao());
					ps.executeUpdate();
				} else { // se já tem o registro, não faz nada
					// System.out.println("REGISTRO PRE EXISTENTE");
				}
			} catch (SQLException ex) {
				System.out.println(ex.toString());
				ex.printStackTrace();
			}
		}
	}


	private void openConnection() {
		try {
			// Carregando o JDBC Driver padrão
			// String driverName = "com.mysql.jdbc.Driver";
			String driverName = "com.mysql.cj.jdbc.Driver";
			Class.forName(driverName);
			// Configurando a nossa conexão com um banco de dados//
			String serverName = "localhost"; // caminho do servidor do BD
			String mydatabase = "od_suap"; // nome do seu banco de dados
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
			String username = "gtdaod"; // nome de um usuário de seu BD
			String password = "senhagtdaod"; // sua senha de acesso
			connection = DriverManager.getConnection(url, username, password);
			// Testa sua conexão//
			if (connection == null) {
				System.out.println("STATUS--->Não foi possivel realizar conexão");
			} else {
				// System.out.println("STATUS--->Conectado com sucesso!");
			}
		} catch (ClassNotFoundException e) { // Driver não encontrado
			System.out.println("O driver expecificado nao foi encontrado.");
		} catch (SQLException e) {
			// Não conseguindo se conectar ao banco
			System.out.println("Nao foi possivel conectar ao Banco de Dados.");
		}
	}
}