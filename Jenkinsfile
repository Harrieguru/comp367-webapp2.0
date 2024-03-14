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
                sh 'mvn clean package jacoco:report'
            }
        }
        stage('Code Coverage') {
            steps {
                jacoco(execPattern: '**/target/*.exec')
            }
        }
        stage('Docker Build') {
            steps {
                script {
                    docker.build("comp367-webapp")
                }
            }
        }
        stage('Docker Login') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', credentialsId: 'dockerCredentials') {
                        // Login to Docker Hub
                    }
                }
            }
        }
        stage('Docker Push') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', credentialsId: 'dockerCredentials') {
                        docker.image("comp367-webapp").push("latest")
                    }
                }
            }
        }
    }
}
