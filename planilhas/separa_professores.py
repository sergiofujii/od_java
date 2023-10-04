import csv

# Abra o arquivo CSV de entrada e crie um novo arquivo CSV de saída
with open('professores_informatica_unico.csv', 'r', newline='') as input_csv, open('novo_arquivo.csv', 'w', newline='') as output_csv:
    reader = csv.DictReader(input_csv, delimiter=';')
    fieldnames = reader.fieldnames
    writer = csv.DictWriter(output_csv, fieldnames=fieldnames, delimiter=';')
    writer.writeheader()

    for row in reader:
        disciplina = row['Disciplina']
        professores = row['Professores'].split(',')

        # Replicar a linha para cada professor
        for professor in professores:
            new_row = row.copy()
            new_row['Professores'] = professor.strip()
            writer.writerow(new_row)


# # Conecte-se ao banco de dados MySQL
# conn = mysql.connector.connect(
#     host='localhost',
#     user='gtdaod',
#     password='senhagtdaod',
#     database='od_suap'
# )