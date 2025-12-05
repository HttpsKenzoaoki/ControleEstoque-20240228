# ControleEstoque-20240228

O arquivo README.md deve conter instruções claras de como inicializar e testar a API (e.g., dependências necessárias e comandos de execução).

## Dependências necessárias
- Java 17 (JDK 17) ou superior
- Maven (pode usar o wrapper `mvnw` incluído no projeto)
- Git (para clonar o repositório)

Verificar Java:
```bash
java -version
```

## Como inicializar a aplicação

1) Clonar o repositório:
```bash
git clone https://github.com/HttpsKenzoaoki/ControleEstoque-20240228.git
cd ControleEstoque-20240228
```

2) Executar com Maven (wrapper):
```bash
./mvnw spring-boot:run
```
Se estiver no Windows:
```bash
mvnw.cmd spring-boot:run
```

3) A API ficará disponível em:
```
http://localhost:8080
```

## Como testar a API

- Rodar testes automatizados:
```bash
./mvnw clean test
```

- Caso existam endpoints REST, você pode testar com `curl`. Exemplos genéricos (ajuste conforme seus endpoints):
```bash
# Listar recursos (exemplo)
curl http://localhost:8080/api/recursos

# Criar recurso (exemplo)
curl -X POST http://localhost:8080/api/recursos \
  -H "Content-Type: application/json" \
  -d '{"campo":"valor"}'
```

Observação: substitua `/api/recursos` pelos caminhos reais dos seus controllers.