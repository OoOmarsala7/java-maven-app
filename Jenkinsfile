pipeline {
    agent any
    environment {
        NEW_VERSION = "1.0.0"
    }
    tools {
        maven 'maven'
    }
    stages {
        stage('Build') {
            when {
                expression {
                    env.BRANCH_NAME == 'main'
                }
            }
            steps {
                echo "Building the application ${NEW_VERSION}"
                sh 'mvn test'
            }
        }
        stage('Test') {
            steps {
                echo "Testing the application"
                sh 'mvn package'
            }
        }
        stage('Building container') {
            steps {
                echo "Building the container"
                sh 'docker build -t omarsala78/my-rep:jvm .'
            }
        }
        stage('Logging and deploying to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker_hub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        echo "Logging in to Docker Hub"
                        sh 'echo $PASSWORD | docker login -u $USERNAME --password-stdin'
                    }
                }
                echo "Deploying the container to Docker Hub"
                sh 'docker push omarsala78/my-rep:jvm'
            }
        }
    }
}
