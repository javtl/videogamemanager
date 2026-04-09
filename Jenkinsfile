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
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv('SonarQube') {
                        script {
                            def branchSuffix = env.BRANCH_NAME == 'master' ? '' : "-${env.BRANCH_NAME}"
                            sh "mvn sonar:sonar -Dsonar.token=${SONAR_TOKEN} -Dsonar.projectName=vgm${branchSuffix} -Dsonar.projectKey=vgm${branchSuffix}"
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