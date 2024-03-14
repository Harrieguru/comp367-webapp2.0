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
                withMaven(maven: 'MAVEN3', jdk: 'JDK') {
                    sh 'mvn clean package jacoco:report'
                }
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
    
    post {
        success {
            jacoco(
                classPattern: '**/target/classes',
                execPattern: '**/target/jacoco/*.exec',
                sourcePattern: '**/src/main/java'
            )
        }
    }
}
