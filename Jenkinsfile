pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t restaurant-service:jenkins .'
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker rm -f restaurant-service || true'
                sh 'docker run -d --name restaurant-service -p 9097:9097 restaurant-service:jenkins'
            }
        }

    }
}