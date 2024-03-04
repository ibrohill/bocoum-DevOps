pipeline {
    agent any

    environment {
        mvn = tool 'M3'
    }

    tools {
        jdk 'JDK_11'
        maven 'M3'
        sonarqube 'sonar-server'
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
                script {
                    def scannerHome = tool 'sonarqube'
                    withSonarQubeEnv('sonar-server') {
                        sh "${scannerHome}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=bocoum -Dsonar.projectName=bocoum -Dsonar.login=sqp_ad4c87a9ba68a382667f94b76012e375118e2967"
                    }
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
