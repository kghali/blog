# Getting Started
This project contains:
* REST API developed using Spring Boot 2.1.5, Spring Data JPA and Hibernate
* Web application that consumes this API developed using Angular 7.2.0 and Bootstrap 3.3.6
### Prerequisites
* JDK 8
* MySQL
* Apache Maven
* Node
* NPM
* Angular CLI

### How to start the application
To launch project in development mode:
####Launch the REST API
* go to <b>src/main/resources/hibernate.properties</b> and change database credentials with yours 
  <pre>
  jdbc.user=root
  jdbc.pass=root
  </pre>
* under the <b>blog directory</b>, run
  <pre>
  mvn spring-boot:run
  </pre>
* API will be available at http://localhost:8080 
####Launch to web client
* install Angular CLI
  <pre>npm install -g @angular/cli</pre>
* under <b>web directory</b> of the project, run
  <pre>
  npm install
  ng serve
  </pre>
* application will be available at http://localhost:4200
