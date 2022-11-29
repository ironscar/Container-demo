# Getting Started

### Container demo
- Clean docker env with ```docker rm container-demo && docker rmi ironscar/spring-boot-docker```
- Run ```mvn clean package && docker build -t ironscar/spring-boot-docker --build-arg VERSION=0.0.1-SNAPSHOT .``` to build image
- Run container by ```docker run -d -p 8080:8081 --name container-demo ironscar/spring-boot-docker```
- To stop container ```docker stop container-demo```
- Access ```http://localhost:8080/container/demo/msg``` to see response
- ```docker push``` for the first online image takes up to an hour and doesn't show the progress updates when on jenkins so have patience, subsequent pushes are much faster due to cached layers
- The personal access token that jenkins uses to push to github may need to be recreated due to its expiration so check on that if it stops working

### Jenkinsfile details

- It will first checkout the branch, get the pom version and calculate the build version
- it will do a clean install so as to build and run tests
- It will update the pom file and push it to the repo
- Then, it builds the new docker image with a tag same as the pom version
- After that, it pushes the new image to registry
- In the deployment step, it clones the ansible repo into the current directory and runs a playbook
  - `cd` command doesn't work in `sh` so we may have to give relative paths to the inventory and playbook files
- In cleanup step, it deletes the ansible repo cloned and also removes the docker image

- **todo** 
  - need to check if ansible repo already exists at a top-level location and just git pull, else git clone (if failures happen, it may clone twice and give errors if already exists etc)
  - need to check how to do different environment deployments like direct deployment in stage and exact version deployment in prod (latter is done)
  - Need to check how to remove older docker container and image when making version updates on app servers (former is auto-handled but not the latter)
  

### Target plan
- create a Spring boot service
  - containerize it (done)
  - write some actual test for project for next step (done)
- create jenkins pipelines managed as code for it to run CI - https://technology.riotgames.com/news/thinking-inside-container (for productionizing jenkins)
  - configure maven/docker (done)
  - build & test (done)
  - image build & push to registry (done)
  - update pom minor version on each build to main and push to git using jenkins user (done)
  - jenkins container volumes to save data and logging (done)
  - jenkins master slave scalable config as containers across different servers
- check how to integrate config management tool like Ansible into job and deploy image as container to server
  - install ansible on vagrant (done)
	- configure ansible to deploy docker images to VMs as containers (done)
	- add ansible to jenkins container image (done)
	- integrate ansible into jenkins build pipeline with ssh key automation (done)
  - check how to externalize and change configurations on deployment
  - check how to change passwords at deployment without checking into git
  - try out ansible roles
- setup jmeter for them to see how load testing works
- setup sonarqube to check code quality
- check how to gather logs from it using Prometheus etc and graph it
- set up another project to act like microservices & repeat above
- work on them together and see how things work in a cloud native environment
- bring kubernetes into it to see what happens
- setup a service mesh like Linkerd/Istio for the microservices
- check how to setup gitops
- openstack/cloudstack to create your own cloud - https://cloud.google.com/blog/topics/developers-practitioners/can-you-make-openstack-more-interesting-cloud-natives-heres-how (for intro to openstack)
- terraform on openstack/cloudstack to do automated version-controlled provisioning
- set up all the above on top of openstack/cloudstack environment

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
