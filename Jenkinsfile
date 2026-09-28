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

        stage('AWS Login Test') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'aws-ecr-credentials',
                        usernameVariable: 'AWS_ACCESS_KEY_ID',
                        passwordVariable: 'AWS_SECRET_ACCESS_KEY'
                    )
                ]) {
                    sh '''
                        export AWS_DEFAULT_REGION=ap-south-1
                        aws sts get-caller-identity
                    '''
                }
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