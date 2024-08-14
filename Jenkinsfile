pipeline {
    agent any
    environment {
        NEW_VERSION = "1.0.0"
        withCredentials([usernamePassword(credentialsId: 'docker_hub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
     }  
    }
    tools{
        maven
    }
    stages {
        stage('Build') {
            when {
                expression {
                    env.BRANCH_NAME == 'main'
                }
            }
            
            steps {
                echo "building the application ${NEW_VERSION}"
                mvn test
            }
        }
        stage('Test') {
            steps {
                echo "testing the application"
                sh mvn package
                
            }
        }
        stage('building container') {
            steps {
                echo "building the container"
                sh docker build -t omarsala78/my-rep:jvm
                
            }
        }
        stage('logging and deploying to docker hub')
            steps{
                echo "loggin in to docker hub"
                sh echo $PASSWORD | docker login -u $USERNAME --password-stdin

            }
    }
}
