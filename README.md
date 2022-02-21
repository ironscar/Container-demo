# Getting Started

### Container demo
- Clean docker env with ```docker rm container-demo && docker rmi ironscar/spring-boot-docker```
- Run ```mvn clean package && docker build -t ironscar/spring-boot-docker --build-arg VERSION=0.0.1-SNAPSHOT .``` to build image
- Run container by ```docker run -d -p 8080:8081 --name container-demo ironscar/spring-boot-docker```
- To stop container ```docker stop container-demo```
- Access ```http://localhost:8080/container/demo/msg``` to see response
- ```docker push``` sometimes takes up to an hour and doesn't show the progress updates when on jenkins so have patience

### Progress so far
- Able to create and publish docker image of spring boot project to dockerhub using jenkins job on a jenkins container

### Setting up jenkins
- create custom jenkins image with docker cli installation
- if not custom jenkins image with docker cli installation, docker commands directly speified fail
- run jenkins container as ``` docker run -d -p 8081:8080 -p 50000:50000 --name myjenkins2 --group-add $(stat -c '%g' /var/run/docker.sock) -v /var/run/docker.sock:/var/run/docker.sock ironscar/jenkins-docker-cli ```
- windows should include a "//var/run/docker.sock" for the host
- using group-add without a user adds the specified group to the declared user (by default no user implies root) of the container
- the $(stat...) thing doesn't work on windows so try to get the group id of the group that has ownership of docker.sock and add that as value to group-add flag (generally 0)
- Install plugins: Maven integration, Docker, Docker pipelines, Pipeline utility steps
- Set up credentials for github and docker registry using manage credentials
- for github, also generate a personal access token from Settings > Developer settings > Personal access tokens (expires in some set time) and then create a new credential in jenkins with password as token
- Setting this as a credential in jenkinsfile will give access to token as {credentialName}_PSW
- Maange Jenkins > Configure System > git plugin config lets you set the name of the commit author so that jenkins commits can be differentiated
- registryCredential specified in environment of Jenkinsfile should specify same id as docker registry credential
- Configure tools for jdk by specifying name as JDK (used in Jenkinsfile tools) & /opt/java/openjdk as JAVA_HOME
- Configure maven by naming Maven-3.8.4 and choose to auto-install specific version
- Configure docker by naming Docker-19.03.13 and specify installation root (in this case /usr/bin/docker)
- Create multibranch pipeline and add git as branch source and specify repository url
- Add credentials as created before
- Add filter by regular expression for branches (for starters, just main branch)
- Can configure docker below as well
- On save changes, it will index and run build

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