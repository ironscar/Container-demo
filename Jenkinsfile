pipeline {
    environment {
        registry = 'ironscar/spring-boot-docker'
        registryCredential = 'docker-hub'
        dockerImage = ''
    }
    agent any
    // need to use the names used in global config in jenkins
    tools {
        maven 'Maven-3.8.4'
        jdk 'JDK'
        dockerTool 'Docker-19.03.13'
    }
    stages {
        // dont really need this first step other than to check
        stage ('init-check') {
            steps {
                sh '''
                    echo "M2_HOME = ${M2_HOME}"
                    echo "JAVA_HOME = ${JAVA_HOME}"
                    echo "$BUILD_NUMBER"
                    docker --version
                '''
            }
        }
        stage('build-test') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage("package") {
            steps {
                // needs docker daemon which currently cannot be connected to
                script {
                    dockerImage = docker.build(registry + ':' + BUILD_NUMBER, '--build-arg VERSION=0.0.1 .')
                }
            }
        }
        stage("publish") {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage("clean up") {
            steps {
                sh 'docker rmi $registry:$BUILD_NUMBER'
            }
        }
    }
}
