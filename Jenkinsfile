pipeline {
    agent any
    stages {
        stage('Build') {
            when {
                expression {
                    env.BRANCH_NAME == 'main'
                }
            }
            steps {
                echo "building the application"
            }
        }
        stage('Test') {
            steps {
                echo "testing the application"
            }
        }
        stage('Deploy') {
            steps {
                echo "deploying the application"
            }
        }
    }
}
