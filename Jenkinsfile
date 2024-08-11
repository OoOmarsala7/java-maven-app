pipeline {
    agent any 
    stages {
        stage('Build') { 
            steps {
                // when{
                //     expression {
                //         BRANSH_NAME == 'main' && CODE_CHANGES == true
                //     }
                // }
                echo "building the application" 
            }
        }
        stage('Test') { 
            steps {
                echo "testing the application "
            }
        }
        stage('Deploy') { 
            steps {
                echo "deploying the application"
            }
        }
    }
}