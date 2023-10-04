import csv
import mysql.connector

# Função para inserir ou buscar um professor no banco de dados
def get_or_insert_professor(cursor, professor_nome):

    # Se a tabela estiver vazia, zera o auto_increment
    cursor.execute("SELECT COUNT(*) FROM Professores")
    count = cursor.fetchone()[0]
    if count == 0:
        cursor.execute("ALTER TABLE Professores AUTO_INCREMENT = 1")

    cursor.execute("SELECT id FROM Professores WHERE nome = %s", (professor_nome,))
    professor_id = cursor.fetchone()
    if professor_id is None:
        cursor.execute("INSERT INTO Professores (nome) VALUES (%s)", (professor_nome,))
        return cursor.lastrowid
    else:
        return professor_id[0]

# Função para inserir ou buscar uma disciplina no banco de dados
def get_or_insert_disciplina(cursor, disciplina_nome):

    # Se a tabela estiver vazia, zera o auto_increment
    cursor.execute("SELECT COUNT(*) FROM Disciplinas")
    count = cursor.fetchone()[0]
    if count == 0:
        cursor.execute("ALTER TABLE Disciplinas AUTO_INCREMENT = 1")

    cursor.execute("SELECT id FROM Disciplinas WHERE nome = %s", (disciplina_nome,))
    disciplina_id = cursor.fetchone()

    if disciplina_id is None:
        cursor.execute("INSERT INTO Disciplinas (nome) VALUES (%s)", (disciplina_nome,))
        return cursor.lastrowid
    else:
        return disciplina_id[0]

# Conecte-se ao banco de dados MySQL
conn = mysql.connector.connect(
    host='localhost',
    user='gtdaod',
    password='senhagtdaod',
    database='od_suap'
)

cursor = conn.cursor()

# Comandos SQL para criar as tabelas, se elas não existirem
create_professores_table = """
CREATE TABLE IF NOT EXISTS Professores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);
"""

create_disciplinas_table = """
CREATE TABLE IF NOT EXISTS Disciplinas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);
"""

create_professores_disciplinas_table = """
CREATE TABLE IF NOT EXISTS Professores_Disciplinas (
    professor_id INT,
    disciplina_id INT,
    PRIMARY KEY (professor_id, disciplina_id),
    FOREIGN KEY (professor_id) REFERENCES Professores(id),
    FOREIGN KEY (disciplina_id) REFERENCES Disciplinas(id)
);
"""

# Execute os comandos SQL para criar as tabelas
cursor.execute(create_professores_table)
cursor.execute(create_disciplinas_table)
cursor.execute(create_professores_disciplinas_table)

# Abra o arquivo CSV modificado
with open('novo_arquivo.csv', 'r', newline='') as csv_file:
    reader = csv.DictReader(csv_file, delimiter=';')
    
    for row in reader:
        disciplina_nome = row['Disciplina']
        professor_nome = row['Professores']

        # Inserir ou buscar o professor e a disciplina
        professor_id = get_or_insert_professor(cursor, professor_nome)
        disciplina_id = get_or_insert_disciplina(cursor, disciplina_nome)

        # Antes de inserir, verifique se a combinação professor_id e disciplina_id já existe
        cursor.execute("SELECT * FROM Professores_Disciplinas WHERE professor_id = %s AND disciplina_id = %s",
                    (professor_id, disciplina_id))
        existing_record = cursor.fetchone()

        if existing_record is None:
            # A combinação não existe, então insira
            cursor.execute("INSERT INTO Professores_Disciplinas (professor_id, disciplina_id) VALUES (%s, %s)",
                        (professor_id, disciplina_id))
        else:
            print("A combinação já existe na tabela.")


# Faça commit das alterações e feche a conexão
conn.commit()
conn.close()
