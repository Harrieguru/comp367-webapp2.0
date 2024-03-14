pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build Maven Project') {
            steps {
                // Use the configured Maven installation
                withMaven(maven: 'MAVEN3') {
                    sh 'mvn clean package jacoco:report'
                }
            }
        }
        stage('Code Coverage') {
            steps {
                // This stage is used to capture and archive the Jacoco coverage reports
                jacoco(classPattern: 'com.example.*', execPattern: '**/target/jacoco.exec')
                archiveArtifacts artifacts: 'target/site/jacoco/*'
            }
        }
        stage('Docker Build') {
            steps {
                script {
                    docker.build('comp367-webapp')
                }
            }
        }
        stage('Docker Login') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerCredentials') {
                        // Docker login is handled automatically using the provided credentials
                    }
                }
            }
        }
        stage('Docker Push') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerCredentials') {
                        docker.image('comp367-webapp').push('latest')
                    }
                }
            }
        }
    }
}
