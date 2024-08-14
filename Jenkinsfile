pipeline {
    agent any
    environment {
        NEW_VERSION = "1.0.0"
    }
    parameters {
        choice(name: "VERSION", choices: ['1.0.0', '2.0.0', '3.0.0'])
        booleanParam(name: "ExecuteTest", defaultValue: true)
    }
    tools {
        maven 'maven'
    }
    stages {
        stage('Test') {
            when {
                expression {
                    params.ExecuteTest == true
                }
            }
            steps {
                echo "Building the application ${params.VERSION}"
                sh 'mvn test'
            }
        }
        stage('Packagin') {  // This stage name is duplicated; consider renaming for clarity
            steps {
                echo "Testing the application"
                sh 'mvn package'
            }
        }
        stage('Building container') {
            steps {
                echo "Building the container"
                sh "docker build -t omarsala78/my-rep:jvm:${params.VERSION} ."
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
                sh "docker push omarsala78/my-rep:jvm:${params.VERSION}"
            }
        }
    }
}
