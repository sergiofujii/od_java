#ATENCAO! Nao esquecer de rodar o comando abaixo primeiro
# seleciona o banco. 
use gtdaod;

#todas as disciplinas
select * from disciplina;

#todos os alunos
select * from aluno;

#todos os registros de disciplinas cursadas
select * from aluno_cursa_disciplina;

#todos os alunos que se formaram
select * from aluno where coeficiente_progressao=100;

#todos os alunos que tirevam dependencias e se formaram

#todos os alunos que tiveram dependencia e desistiram

# TODO: todos os alunos e respectivo numero de dependencias
select aluno.matricula, aluno.curso from aluno, aluno_cursa_disciplina, disciplina where aluno.id = aluno_cursa_disciplina.aluno_id and disciplina.id=aluno_cursa_disciplina.disciplina_id and aluno_cursa_disciplina.situacao='Reprovado' group by aluno.matricula order by aluno.nome;

#