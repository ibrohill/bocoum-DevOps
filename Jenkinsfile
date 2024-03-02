pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'M3'
        PATH = "$MAVEN_HOME/bin:$PATH"

        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "172.18.0.4:8081"
        NEXUS_REPOSITORY = "repoJenkinsLy"
        NEXUS_CREDENTIAL_ID = "nexusCredential"
        ARTIFACT_VERSION = "${BUILD_NUMBER}"
        SONARQUBE_URL = 'http://localhost:9000'  // Mettez l'URL réelle de votre serveur SonarQube ici
        SONARQUBE_TOKEN = credentials('sonarqube-token-id') // Configurez cela dans les informations d'identification Jenkins
    }

    tools {
        jdk 'JDK_11'
        maven 'M3'
    }

    stages {
        stage('Git Check out') {
            steps {
                checkout scm
            }
        }

        stage('Maven build') {
            steps {
                sh "mvn clean package"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar-server') {
                    sh "${MAVEN_HOME}/bin/mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=bocoum \
                        -Dsonar.projectName='bocoum' \
                        -Dsonar.host.url='http://localhost:9000' \
                        -Dsonar.login='sqp_a20fbbc307aaf8fed3348f38cc5575d63f84cf54'"
                }
            }
        }

        stage('Publish to Nexus') {
            steps {
                script {
                    def pom = readMavenPom file: "pom.xml"
                    def filesByGlob = findFiles(glob: "target/*.${pom.packaging}")
                    def artifactPath = filesByGlob[0].path
                    def artifactExists = fileExists artifactPath

                    if (artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}"

                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: ARTIFACT_VERSION,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging]
                            ]
                        )
                    } else {
                        error "*** File: ${artifactPath}, could not be found"
                    }
                }
            }
        }
    }
}
