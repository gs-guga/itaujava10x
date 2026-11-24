# Desafio Técnico Itaú - Java10X

API REST desenvolvida em Java com Spring Boot para gerenciamento de transações financeiras e consulta de estatísticas, conforme as regras do Desafio Técnico Itaú Java10X.

O projeto utiliza armazenamento em memória, sem banco de dados ou cache externo, e possui testes automatizados, documentação da API, containerização e pipeline CI/CD.

## Tecnologias utilizadas

- Java 25
- Spring Boot
- Maven
- JUnit
- Mockito
- MockMvc
- Docker
- GitHub Actions
- Docker Hub
- Swagger / OpenAPI
- Spring Boot Actuator
- Lombok

## Funcionalidades

- Receber transações financeiras.
- Validar os dados das transações.
- Armazenar transações em memória.
- Remover todas as transações armazenadas.
- Calcular estatísticas das transações dentro de um intervalo configurável.
- Registrar logs de operações e erros.
- Medir o tempo de execução do cálculo das estatísticas.
- Disponibilizar documentação interativa da API.
- Disponibilizar endpoint de healthcheck.

## Endpoints

### POST `/transacao`

Recebe uma transação e armazena em memória.

Exemplo de requisição:

```json
{
  "valor": 123.45,
  "dataHora": "2020-08-07T12:34:56.789-03:00"
}
```

Validações:

- O valor deve estar presente.
- A data e hora devem estar presentes.
- O valor não pode ser negativo.
- A data e hora não podem estar no futuro.

Respostas esperadas:

- `201 Created`: transação criada.
- `422 Unprocessable Entity`: transação inválida.
- `400 Bad Request`: JSON inválido ou malformado.

### DELETE `/transacao`

Remove todas as transações armazenadas em memória.

Resposta:

- `200 OK`, sem corpo.

### GET `/estatistica`

Retorna estatísticas das transações realizadas dentro do intervalo configurado.

Exemplo de resposta:

```json
{
  "count": 10,
  "sum": 1234.56,
  "avg": 123.456,
  "min": 12.34,
  "max": 123.56
}
```

Caso não existam transações dentro do intervalo, todos os valores retornados serão zero.

## Configuração

O intervalo utilizado para calcular as estatísticas pode ser configurado no arquivo `application.yml`.

```yaml
estatistica:
  segundos: 60
```

O valor representa a quantidade de segundos considerados no cálculo.

A configuração é validada para impedir valores nulos ou menores ou iguais a zero.

## Executando localmente

### Pré-requisitos

- Java 25
- Git

Clone o repositório:

```bash
git clone https://github.com/devcarvalh0/itaujava10x.git
```

Entre na pasta do projeto:

```bash
cd itaujava10x
```

Execute a aplicação no Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em:

```text
http://localhost:8080
```

## Executando os testes

Para executar todos os testes automatizados no Windows:

```powershell
.\mvnw.cmd clean test
```

No Linux ou macOS:

```bash
./mvnw clean test
```

Os testes verificam as regras de negócio, os cálculos de estatísticas, os endpoints e os cenários de erro.

## Docker

O projeto possui um `Dockerfile` para construção da imagem da aplicação.

### Executando a imagem publicada

Baixe a imagem do Docker Hub:

```bash
docker pull devcarvalh0/itaujava10x:latest
```

Execute o container:

```bash
docker run -d -p 8080:8080 --name itaujava10x devcarvalh0/itaujava10x:latest
```

A aplicação estará disponível em:

```text
http://localhost:8080
```

Para parar o container:

```bash
docker stop itaujava10x
```

Para removê-lo:

```bash
docker rm itaujava10x
```

## Healthcheck

A aplicação utiliza Spring Boot Actuator para disponibilizar o endpoint de saúde.

```http
GET /actuator/health
```

Exemplo de resposta:

```json
{
  "status": "UP"
}
```

## Documentação da API

A documentação interativa é disponibilizada pelo Swagger UI.

Com a aplicação em execução, acesse:

```text
http://localhost:8080/swagger-ui/index.html
```

A documentação permite visualizar os endpoints, seus parâmetros e exemplos de requisição e resposta.

## Logs e performance

A aplicação registra logs relacionados a:

- Criação de transações.
- Erros e validações.
- Cálculo de estatísticas.
- Tempo de execução do cálculo das estatísticas.

O tempo de cálculo é medido utilizando `System.nanoTime()` e registrado em milissegundos.

## CI/CD

O projeto utiliza GitHub Actions para automatizar o processo de integração e publicação.

O pipeline realiza:

1. Checkout do código.
2. Configuração do Java 25.
3. Execução dos testes automatizados.
4. Construção da imagem Docker.
5. Publicação da imagem no Docker Hub.

A publicação da imagem ocorre após a aprovação dos testes, em pushes para a branch `main`.

## Observação

As transações são armazenadas somente em memória. Portanto, os dados são perdidos quando a aplicação é reiniciada, conforme as regras do desafio.

## Autor

Desenvolvido como parte do Desafio Técnico Itaú Java10X.
