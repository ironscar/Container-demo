pipeline {
    agent any
    // need to use the names used in global config in jenkins
    tools {
        maven 'Maven-3.8.4'
        jdk 'JDK'
    }
    stages {
        // dont really need this first step other than to check
        stage ('init-check') {
            steps {
                echo '$M2_HOME'
                echo '$JAVA_HOME'
            }
        }
        stage('build-test') {
            steps {
                sh 'mvn clean install'
                echo 'build and tests done'
            }
        }
        stage("package") {
            steps {
                echo 'build new docker image'
            }
        }
    }
}
