# :books: API Restful para treinamento de testes de APIs
<div align="center" display="flex" style="justify-content:flex-start;">
      <img align="center" alt="js" height="200" width="200" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" />
</div>

<p align="center">
 <a href="#desc">Descrição</a> •
 <a href="#about">O que a API faz?</a> •
 <a href="#rotas">Rotas</a> •
 <a href="#exemplos">Modelos</a> •
 <a href="#tecnologias">Tecnologias</a> • 
 <a href="#prerequisitos">Pré requisitos</a> • 
 <a href="#executando">Executar o projeto</a> • 
 <a href="#autor">Autor</a>
</p>

<div id="desc"/>

## 📝 Descrição
Este projeto é uma API restful desenvolvida utilizando o framework Spring Boot, na linguagem de programação java. <br>
O intuito de sua produção é para que fique disponível para que sejam efetuados testes de API Restful. <br>
O link de acesso ao Heroku pode ser acessado [aqui](https://api-course-test-automatized.herokuapp.com) (temporariamente indisponível para manutenção) <br>
A documentação SWAGGER da aplicação está disponível nesse [link](https://api-course-test-automatized.herokuapp.com/swagger-ui.html) (temporariamente indisponível para manutenção)

<div id="about"/>

## ⚙️ O que a API faz?
A API Restful da suporte a aplicações de postagens. <br>
Consiste em dois módulos: usuário e publicações (posters), onde um usuário cria pode criar uma ou várias publicações <br>
Para cadastrar um usuário é necessário ter: nome, email e senha como campos obrigatórios e, caso deseje, biografia, site e url da imagem de perfil<br>
Para cadastrar uma publicação é necessário ter: titulo e texto <br>

<div id="exemplos"/>

## 📑 Exemplos
##### JSON para criação de usuário: <br>
```json
{
   "name": "Raimundo",
   "lastname": "Lugério",
   "email": "raimundo@dcx.ufpb.br",
   "password": "umasenhadificil",
   "bio": "a litle nice guy",
   "site": "http://github.com/MarosLudgerio",
   "urlImage": "http://github.com/MarosLudgerio.png"
}
```

##### JSON para criação de poster: <br>
```json
{
   "titulo": "poster 1",
   "texto": "texto"
}
```

<div id="rotas" />

## :busstop: Rotas
#### Login
- [ ] POST /auth/login
#### Usuário
- [ ] POST /api/users
- [ ] GET /api/users
- [ ] GET /api/users/details
- [ ] PUT /api/users
#### Publicações
- [ ] GET /api/posters
- [ ] GET /api/posters/id
- [ ] POST /api/posters
- [ ] PUT /api/posters/id
- [ ] DELETE /api/posters/id


<div id="tecnologias"/>

## ✨ Tecnologias

-   [ ] [Java](https://www.java.com/pt-BR/)
-   [ ] [Spring Boot](https://spring.io/)
-   [ ] [Thymeleaf](https://www.thymeleaf.org/)
-   [ ] [PostgreSQL](https://www.postgresql.org/)
-   [ ] [Project Lombok](https://projectlombok.org/)
-   [ ] [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
-   [ ] [Swagger](https://swagger.io/)
-   [ ] [Json Web Token](https://jwt.io/)
-   [ ] [Hibernate](https://hibernate.org/)
-   [ ] [Model Mapper](http://modelmapper.org/)

<div id="prerequisitos"/>

## 📑 Pré requisitos

Para executar o projeto localmente, é necessário ter:
1. PostgreSQL instalado
2. Banco criado (para perfil Dev)
3. Java 11
4. Arquivo `application.properties` selecione o perfil que deseja ativar para API
   1. `spring.profiles.active=` 

   2. Opções de perfis: dev e test.
      1. O perfil `dev` precisa ser configurado corretamente no arquivo `application-dev.properties`
      2. O perfil `test` a aplicação irá executar em um banco em memória, todos os dados serão excluídos quando a aplicação parar a execução
 

<div id="executando" />

## ▶️ Executando o projeto

Para acessar a API Restful remotamente, basta clicar [aqui](https://api-course-test-automatized.herokuapp.com) <br>
> NOTE: Normalmente, a aplicação demora uns minutos para inciar

Para executar localmente, siga os passos:
```sh
$ git clone https://github.com/MarcosLudgerio/api-automation-test.git
$ cd api-automation-test
$ ./mvnw install
$ ./mvnw spring-boot:run
```

<div id="autor" />

## 👩‍💻 Autor 

<table>
   <tr>
     <td align="center">
        <a href="https://github.com/MarcosLudgerio">
         <img style="border-radius: 50%;" src="https://avatars0.githubusercontent.com/u/43012976?s=460&u=1163c04d9f35b577063b3f6550ae520c4dd2f866&v=4" width="100px;" alt=""/>
        </a>
        <br/><sub><b>Marcos Ludgério</b></sub>
     </td>
   </tr>
</table>
