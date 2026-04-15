pipeline {
    agent any
    tools {
            maven 'maven-3.9'
        }
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
                script {
                    sh "docker build -t ${DOCKER_IMAGE}:latest ."
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    // Esto envuelve el comando de Maven
                    withSonarQubeEnv('SonarQube') {
                        sh "mvn sonar:sonar"
                    }

                    // Este paso detiene el pipeline hasta que Sonar responda (vía Webhook)
                    timeout(time: 5, unit: 'MINUTES') {
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            error "Pipeline abortado por baja cobertura: ${qg.status}"
                        }
                    }
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
    }
}