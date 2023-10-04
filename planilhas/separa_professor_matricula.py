import csv
import re

# Abra o arquivo CSV original e crie um novo arquivo CSV
with open('novo_arquivo.csv', 'r', newline='') as input_csv, open('novo_arquivo_professor_matricula.csv', 'w', newline='') as output_csv:
    reader = csv.DictReader(input_csv, delimiter=';')
    fieldnames = reader.fieldnames + ['Matrícula']  # Adicione o campo "Matrícula" ao cabeçalho

    # Defina o escritor para o novo arquivo CSV
    writer = csv.DictWriter(output_csv, fieldnames=fieldnames, delimiter=';')
    writer.writeheader()

    # Padrão de expressão regular para encontrar a matrícula entre parênteses
    pattern = r'\((\d+)\)'

    for row in reader:
        professor_nome = row['Professores']

        # Use expressão regular para encontrar a matrícula
        match = re.search(pattern, professor_nome)
        if match:
            matricula = match.group(1)
            # Atualize a coluna "Professores" sem a matrícula
            row['Professores'] = re.sub(pattern, '', professor_nome).strip()
            row['Matrícula'] = matricula
        else:
            row['Matrícula'] = ''

        writer.writerow(row)
