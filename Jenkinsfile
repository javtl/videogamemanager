pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "videogame-manager-app"
    }
    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Build & Test') {
            steps { sh 'mvn clean verify' }
        }
        stage('Docker Build') {
            steps {
                // Esto crea la imagen de Docker usando el Dockerfile de la raíz
                sh "docker build -t ${DOCKER_IMAGE}:latest ."
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') { // Nombre configurado en Jenkins
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
}