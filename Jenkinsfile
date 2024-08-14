def gv
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
                script {
                    gv.test()
                }
              
            }
        }
        stage('Packagin') { 
            steps {
                script {
                    gv.pack()
                }
                
            }
        }
        stage('Building container') {
            steps {
                script {
                    gv.build_con()
                }           
            }
        }
        stage('Logging and deploying to Docker Hub') {
            steps {
                script {
                 gv.deploy()
                }
               
            }
        }
    }
}
