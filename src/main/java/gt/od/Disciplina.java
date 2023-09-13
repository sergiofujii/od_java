package gt.od;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

// import java.util.Map;

public class Disciplina{
    // public static Map<String,Disciplina> disciplinas;
    private int id;
    private String codigo;
    private String nomeDisciplina;
    private int chPrevista;
    private int periodo;
    private int creditos;
    private float nf1e;
    private float nf2e;
    private float frequencia;
    private int ano;
    private String situacao;

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public void setChPrevista(int chPrevista) {
        this.chPrevista = chPrevista;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public float getNf1e() {
        return this.nf1e;
    }

    public void setNf1e(float nf1e) {
        this.nf1e = nf1e;
    }

    public float getNf2e() {
        return this.nf2e;
    }

    public void setNf2e(float nf2e) {
        this.nf2e = nf2e;
    }

    public float getFrequencia() {
        return this.frequencia;
    }

    public void setFrequencia(float frequencia) {
        this.frequencia = frequencia;
    }

    public int getAno() {
        return this.ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getSituacao() {
        return this.situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public void salvar(Connection connection){
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			String query = "select * from disciplina where codigo='" + this.codigo + "';";
			rs = stmt.executeQuery(query);
			if (!rs.next()) { // se a disciplina ainda nao foi cadastrada.
				query = "INSERT INTO `disciplina` (`codigo`, `nome_disciplina`, `ch_prevista`, `periodo`, `creditos`" + 
				") " + "VALUES (?, ?, ?, ?, ?)";
				PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, this.codigo);
				ps.setString(2, this.nomeDisciplina);
				ps.setInt(3, this.chPrevista);
				ps.setInt(4, this.periodo);
				ps.setInt(5, this.creditos);
				ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if(generatedKeys.next()){
                    this.id = generatedKeys.getInt(1);
                }
                //pegar o id da ultima insercao em salvar em ID.
			} else {
				this.id = rs.getInt("id");
			}
		} catch (SQLException ex) {
            // JOptionPane.showMessageDialog(null, this.nomeDisciplina);
			System.out.println(ex.toString());
            ex.printStackTrace();
		}
	}
}