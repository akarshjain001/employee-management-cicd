pipeline {

    agent any

    options {

        skipDefaultCheckout(true)

        disableConcurrentBuilds()

        buildDiscarder(logRotator(
            numToKeepStr: '20',
            artifactNumToKeepStr: '10'
        ))

        timestamps()

        timeout(time: 30, unit: 'MINUTES')

    }

    tools {

        jdk 'JDK21'
        maven 'Maven3'

    }

    environment {

        SONARQUBE_SERVER = 'SonarQube'

    }

    stages {

        stage('Clean Workspace') {

            steps {
                deleteDir()
            }

        }

        stage('Checkout Source Code') {

            steps {

                git branch: 'main',
                    credentialsId: 'github-ssh',
                    url: 'git@github.com:akarshjain001/employee-management-cicd.git'

            }

        }

        stage('Build & Verify') {

            steps {

                sh 'mvn -B clean verify'

            }

            post {

                always {

                    junit(
                        testResults: 'target/surefire-reports/*.xml',
                        allowEmptyResults: true
                    )

                }

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
:white_check_mark: *BUILD SUCCESS*

*Project:* ${env.JOB_NAME}
*Build:* #${env.BUILD_NUMBER}
*Status:* SUCCESS
*Duration:* ${currentBuild.durationString}

:package: Artifact successfully deployed to Nexus.

:link: Build URL:
${env.BUILD_URL}
"""
            )

        }

        failure {

            slackSend(
                color: 'danger',
                message: """
:x: *BUILD FAILED*

*Project:* ${env.JOB_NAME}
*Build:* #${env.BUILD_NUMBER}
*Status:* FAILED
*Duration:* ${currentBuild.durationString}

Please check the Jenkins Console Output.

:link: Build URL:
${env.BUILD_URL}
"""
            )

        }

        always {

            deleteDir()

        }

    }

}
