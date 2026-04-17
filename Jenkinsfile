pipeline {
    agent any

    tools {
        maven 'maven-3.9'
    }

    environment {
        DOCKER_IMAGE = "videogame-manager-app"
        // Definimos la ruta del informe para no repetirla
        JACOCO_REPORT_PATH = "target/site/jacoco/jacoco.xml"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // El clean es vital para no arrastrar informes viejos de 41%
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    // Enviamos el análisis forzando la ruta del XML de JaCoCo
                    withSonarQubeEnv('SonarQube') {
                        sh "mvn sonar:sonar -Dsonar.coverage.jacoco.xmlReportPaths=${JACOCO_REPORT_PATH}"
                    }
                }
            }
        }

        stage("Quality Gate Check") {
            steps {
                script {
                    // Esperamos a que SonarCloud/SonarQube termine el proceso
                    // Si la condición de >80% que tienes configurada no se cumple, qg.status será 'ERROR'
                    timeout(time: 5, unit: 'MINUTES') {
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            error "Pipeline abortado: El proyecto no cumple el Quality Gate (Cobertura < 80%). Status: ${qg.status}"
                        }
                    }
                }
            }
        }

        stage('Docker Build') {
            // Esta etapa solo se ejecutará si el Quality Gate de Sonar da OK
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE}:latest ."
                }
            }
        }
    }

    post {
        always {
            // Limpia el espacio de trabajo para la siguiente ejecución
            deleteDir()
        }
        failure {
            echo "El pipeline ha fallado. Revisa la cobertura en SonarQube."
        }
    }
}