pipeline {

    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    environment {
        SONARQUBE_SERVER = 'SonarQube'
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
                sh 'mvn -B clean'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn -B compile'
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn -B test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE_SERVER}") {

                    sh '''
                        mvn -B sonar:sonar \
                        -Dsonar.projectKey=employee-management \
                        -Dsonar.projectName=Employee-Management
                    '''

                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy Artifact to Nexus') {
            steps {
                sh 'mvn -B deploy -DskipTests'
            }
        }

    }

    post {

        success {

            slackSend(
                color: 'good',
                message: """
✅ *BUILD SUCCESS*

*Job:* ${env.JOB_NAME}
*Build:* #${env.BUILD_NUMBER}

Artifact successfully deployed to Nexus.

Build URL:
${env.BUILD_URL}
"""
            )

        }

        failure {

            slackSend(
                color: 'danger',
                message: """
❌ *BUILD FAILED*

*Job:* ${env.JOB_NAME}
*Build:* #${env.BUILD_NUMBER}

Check Jenkins Console Output:

${env.BUILD_URL}
"""
            )

        }

    }

}
