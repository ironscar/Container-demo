pipeline {
    environment {
        registry = 'ironscar/spring-boot-docker'
        registryCredential = 'docker-hub'
        githubPeronalToken = credentials('github-personal-token')
        dockerImage = ''
        pomVersion = ''
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
                sh 'git checkout $BRANCH_NAME && git pull $BRANCH_NAME'
                sh 'mvn clean install'
                script {
                    def pom = readMavenPom()
                    pomVersion = pom.getVersion()
                    echo "${pomVersion}"
                    def versionList = pomVersion.tokenize(".")
                    def majorVersion = versionList[0]
                    def middleVersion = versionList[1]
                    pomVersion = majorVersion + "." + middleVersion + "." + BUILD_NUMBER + "-SNAPSHOT"
                    pom.version = pomVersion
                    echo "${pomVersion}"
                    writeMavenPom model: pom
                }
                sh 'git commit -am "update: version update by jenkins"'
                sh 'git push https://${githubPeronalToken_PSW}@github.com/ironscar/Container-demo.git $BRANCH_NAME'
            }
        }
        stage("package") {
            steps {
                script {
                    dockerImage = docker.build(registry + ':' + BUILD_NUMBER, '--build-arg VERSION=' + pomVersion + ' .')
                }
            }
        }
        stage("publish") {
            steps {
                // script {
                //     docker.withRegistry('', registryCredential) {
                //         dockerImage.push()
                //     }
                // }
                sh 'echo "publish"'
            }
        }
        stage("clean up") {
            steps {
                sh 'docker rmi $registry:$BUILD_NUMBER'
            }
        }
    }
}
