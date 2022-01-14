# Getting Started

### Container demo
- Clean docker env with ```docker rm container-demo && docker rmi ironscar/spring-boot-docker```
- Run ```mvn clean package && docker build -t ironscar/spring-boot-docker --build-arg VERSION=0.0.1 .``` to build image
- Run container by ```docker run -d -p 8080:8080 --name container-demo ironscar/spring-boot-docker```
- To stop container ```docker stop container-demo```
- Access ```http://localhost:8080/container/demo/msg``` to see response

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.2/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.6.2/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.2/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
