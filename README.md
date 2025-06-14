# Getting Started

### Container demo
- Clean docker env with ```docker rm container-demo && docker rmi ironscar/spring-boot-docker```
- Run ```mvn clean package && docker build -t ironscar/spring-boot-docker --build-arg VERSION=0.0.1-SNAPSHOT .``` to build image
- Run container by ```docker run -d -p 8080:8081 --name container-demo ironscar/spring-boot-docker```
- To stop container ```docker stop container-demo```
- Access ```http://localhost:8080/container/demo/msg``` to see response
- ```docker push``` for the first online image takes up to an hour and doesn't show the progress updates when on jenkins so have patience, subsequent pushes are much faster due to cached layers
- The personal access token that jenkins uses to push to github may need to be recreated due to its expiration so check on that if it stops working

---

### Jenkinsfile details

- It will first checkout the branch, get the pom version and calculate the build version
- it will do a clean install so as to build and run tests
- It will update the pom file and push it to the repo
- Then, it builds the new docker image with a tag same as the pom version
- After that, it pushes the new image to registry
- In the deployment step, it clones the ansible repo into the current directory and runs a playbook
  - `cd` command doesn't work in `sh` so we may have to give relative paths to the inventory and playbook files
  - The inventory file is dynamically chosen based on which branch was updated (`snapshot` branch uses `stage` inventory and `main` branch uses `prod` inventory)
- In cleanup step, it deletes the ansible repo cloned and also removes the docker image
- includes some try catch blocks so that it can cleanup on failures too
- best practice is not to use try catch blocks but with the `post` block [TODO]

---

### External config & password management

- create a config class that reads env property values and changes response
- read props and add default values using `${:}` in case env property doesn't exist
  - caveat: must restart vscode every time env variable is changed on system
- put all properties in the `app_stage` and `app_prod` yml files for ansible
- read all said properties in `docker_playbook` yml using `env`
  - can check `echo $app_sbd_prop1` inside running container prints its value
- include a property encryped by ansible-vault which is `app_sbd_pass`
  - it gets passed the same way here though
- jenkinsfile needs a way to get the vault password to use it
  - add credentials for ansible stage and prod vault passwords
  - writing a file with the password stored in jenkins
  - using this file as `ansible-playbook -i {inventory.yml} --vault-id {vaultId}@password-file {playbook.yml}`
  - deleting the password file

---

### Target plan

#### Completed

- create a Spring boot service
	- containerize it
	- write some actual test for project for next step
- create jenkins pipelines managed as code for it to run CI
	- configure maven/docker
	- build & test
	- image build & push to registry
	- update pom minor version on each build to main and push to git using jenkins user
	- jenkins container volumes, logging
	- jenkins master slave scalable config as containers across different servers
- check how to integrate Ansible into job and deploy image as container to server
	- install ansible on vagrant
	- configure ansible to deploy docker images to VMs as containers
	- add ansible to jenkins container image
	- integrate ansible into jenkins build pipeline
	- check how to externalize and change configurations on deployment
	- check how to change passwords at deployment without checking into git
- jenkins master slave scalable config as containers across different servers
- update spring boot project to latest version and use jdk 17 alpine image
    - has db container in separate VM but DB deployment is not automated yet

#### Todo

- Refer to https://roadmap.sh/r/cloud-development-roadmap for next learning items
- Build a Developer Cloud Platform based on this roadmap

#### Optional

- setup ansible semaphore UI into jenkins-ansible image if makes sense
- check how to rotate ssh keys without rebuilding docker image
- look into SMTP/SFTP/WebRTC
- look into VectorDBs/Timeseries DBs

---

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

---
