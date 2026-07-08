pipeline {

    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    environment {
        SONARQUBE_SERVER = 'SonarQube'
        NEXUS_VERSION = 'nexus3'
        NEXUS_PROTOCOL = 'http'
        NEXUS_URL = '13.201.133.54:8081'
        NEXUS_REPOSITORY = 'maven-snapshots'
        GROUP_ID = 'com.company'
        ARTIFACT_ID = 'employee-management'
        VERSION = '1.0.0-SNAPSHOT'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-ssh',
                    url: 'git@github.com:akarshjain001/employee-management-cicd.git'
            }
        }

        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('SonarQube Analysis') {

            steps {

                withSonarQubeEnv("${SONARQUBE_SERVER}") {

                    sh '''
                    mvn sonar:sonar \
                    -Dsonar.projectKey=employee-management \
                    -Dsonar.projectName=Employee-Management
                    '''

                }

            }

        }

        stage("Quality Gate") {

            steps {

                timeout(time: 5, unit: 'MINUTES') {

                    waitForQualityGate abortPipeline: true

                }

            }

        }

        stage('Upload Artifact to Nexus') {

            steps {

                nexusArtifactUploader(

                    nexusVersion: NEXUS_VERSION,
                    protocol: NEXUS_PROTOCOL,
                    nexusUrl: NEXUS_URL,
                    repository: NEXUS_REPOSITORY,
                    credentialsId: 'nexus-creds',

                    groupId: GROUP_ID,
                    version: VERSION,

                    artifacts: [

                        [
                            artifactId: ARTIFACT_ID,
                            classifier: '',
                            file: 'target/Employee-Management-1.0.0-SNAPSHOT.jar',
                            type: 'jar'
                        ]

                    ]

                )

            }

        }

    }

    /*

    post {

        success {

            slackSend(
                color: 'good',
                message: "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}\nArtifact uploaded to Nexus."
            )

        }

        failure {

            slackSend(
                color: 'danger',
                message: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
            )

        }

    }
    */

}
