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
                   script {
                       def cleanBranchName = env.BRANCH_NAME.replaceAll("/", "-")
                       def projectIdentifier = "vgm-${cleanBranchName}"

                       withSonarQubeEnv('SonarQube') {
                           sh "mvn sonar:sonar -Dsonar.token=${SONAR_TOKEN} -Dsonar.projectName=${projectIdentifier} -Dsonar.projectKey=${projectIdentifier}"
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