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
                bat 'mvn clean package jacoco:report'
            }
        }
        
        stage('Code Coverage') {
            steps {
                jacoco(
                    classPattern: '**/target/classes',
                    execPattern: '**/target/jacoco/*.exec',
                    sourcePattern: '**/src/main/java'
                )
                archiveArtifacts artifacts: 'target/site/jacoco/*'
            }
        }
        
        stage('Docker Build') {
            steps {
                script {
                    bat 'docker build -t comp367-webapp .'
                }
            }
        }
        
        stage('Docker Login') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerCredentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        bat 'docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%'
                    }
                }
            }
        }
        
        stage('Docker Push') {
            steps {
                script {
                    bat 'docker push comp367-webapp:latest'
                }
            }
        }
    }
}
