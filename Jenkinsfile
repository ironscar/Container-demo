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
                /*
                 * need to checkout and pull here as otherwise it keeps complaining
                 * that pull is up to date but push is behind remote etc
                 */
                sh 'git checkout $BRANCH_NAME && git pull origin $BRANCH_NAME'

                // the script is to update version using Pipeline Utility Steps plugin
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

                /*
                 * run install after version update as otherwise dockerfile version will be wrong
                 * target is going to have old version but it will try to read new version
                 * but only push if install is successful
                 */
                sh 'mvn clean install'
                sh 'git commit -am "update: version update by jenkins"'
                sh 'git push https://${githubPeronalToken_PSW}@github.com/ironscar/Container-demo.git $BRANCH_NAME'
            }
        }
        stage("package") {
            steps {
                script {
                    // add the computed pom version here so that dockerfile picks correct files to copy
                    dockerImage = docker.build(registry + ':' + BUILD_NUMBER, '--build-arg VERSION=' + pomVersion + ' .')
                }
            }
        }
        stage("publish") {
            steps {
                // commented this due to it taking very long time
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
