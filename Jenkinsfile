pipeline {
    agent any
    
    environment {
        MAVEN_HOME = tool 'MAVEN3'
        NOHUP_PATH = '' // Disable execution of nohup
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build Maven Project') {
            steps {
                // Use the configured Maven installation and JDK
                withMaven(maven: 'MAVEN3', jdk: 'JDK') {
                    sh 'mvn clean package jacoco:report'
                }
            }
        }
        
        stage('Code Coverage') {
            steps {
                // Capture and archive the Jacoco coverage reports
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
