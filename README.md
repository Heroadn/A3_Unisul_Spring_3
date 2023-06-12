# A3_Unisul_Spring_3
Projeto em spring 3, usando jwt e keycloaker como servidor de auth

>* O presente projeto de tem por objetivo o desenvolvimento de uma plataforma

    
**Configurações e recomendações do projeto:**
>*   Utilizar um servidor local, no projeto é o framework Java, Springboot 3.
>*   Utilizar docker onde ja vem incluso com mysql e servidor de autenticaçãp.
>*   Utilizar ferramenta para versionamento, no projeto é utilizado GIT.
>*   Utilizar um ambiente de trabalho para desenvolvimento, no projeto é utilizado o VSCode/IntelliJ

**Dependências do projeto:**
>*  JAVA - Backend
>*  Nome
>*  [Link](https://)

>* Keycloak 21.0.1
>*  Nome
>*  [Link](https://www.keycloak.org/)

 
**Rodar:**
>* Iniciar servidor keycloaker/mysql 
```bash
docker-compose up -d
``` 
>* Iniciar aplicação springboot
```bash
./gradlew bootRun
```
                
**TODO:**
>*   Resolver TODOS, redundancias no application.yml
>*   Levantamento de bugs e erros.
>*   Correção de bugs e erros.

**DOCKER**

## Iniciar serviços
```bash
docker-compose up -d
```

## Iniciar backend
```bash
./gradlew bootRun
```

## Parar serviços
```bash
docker-compose down
```

