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

        stage('ECR Login') {
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

                        aws ecr get-login-password --region ap-south-1 | \
                        docker login --username AWS --password-stdin \
                        724680459203.dkr.ecr.ap-south-1.amazonaws.com
                    '''
                }
            }
        }

        stage('Docker Push') {
            steps {
                sh '''
                    docker tag restaurant-service:jenkins \
                    724680459203.dkr.ecr.ap-south-1.amazonaws.com/restaurant-service:jenkins

                    docker push \
                    724680459203.dkr.ecr.ap-south-1.amazonaws.com/restaurant-service:jenkins
                '''
            }
        }

        stage('Deploy to EC2') {
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

                        COMMAND_ID=$(aws ssm send-command \
                            --instance-ids i-0d0960ced8523acb5 \
                            --document-name "AWS-RunShellScript" \
                            --parameters 'commands=[
                                "set -e",
                                "aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 724680459203.dkr.ecr.ap-south-1.amazonaws.com",
                                "docker pull 724680459203.dkr.ecr.ap-south-1.amazonaws.com/restaurant-service:jenkins",
                                "docker rm -f restaurant-service || true",
                                "docker run -d --name restaurant-service --network restaurant-network -p 9097:9097 --env-file /home/ec2-user/restaurant-service.env 724680459203.dkr.ecr.ap-south-1.amazonaws.com/restaurant-service:jenkins"
                            ]' \
                            --query 'Command.CommandId' \
                            --output text)

                        echo "SSM Command ID: $COMMAND_ID"

                        sleep 5

                        aws ssm get-command-invocation \
                            --command-id "$COMMAND_ID" \
                            --instance-id i-0d0960ced8523acb5
                    '''
                }
            }
        }

    }
}