// #!/usr/bin/env groovy
// library("Jenkins_shared_library") //uncomment if you want to use the shared libraryy
def gv
pipeline {
    agent any
    environment {
        NEW_VERSION = "1.0.0"
    }
    parameters {
        // choice(name: "VERSION", choices: ['1.0.0', '2.0.0', '3.0.0'])
        booleanParam(name: "ExecuteTest", defaultValue: true)
    }
    tools {
        maven 'maven'
    }
    stages {
        stage('init') {                      //this is only if you want to load and execute from the groovy filee
            steps {
                script {
                    gv = load 'script.groovy'
                }
            }
        }
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
        stage('incrementVersion') { 
            when {
                expression {
                    BRANCH_NAME == 'main'  // Targeting the master branch
                }
            }
            steps {
                script {
                    gv.incrementVersion()

                            
                }
            }
        }
         stage('Packaging') { 
            when {
                expression {
                    BRANCH_NAME == 'main'  // Targeting the master branch
                }
            }
            steps {
                script {
                    gv.build_image()

                }
            }
        }
        stage('Building container') {
            when {
                expression {
                    BRANCH_NAME == 'main'  // Targeting the master branch
                }
            }
            steps {
                script {
                    gv.build_con()

                }
            }
        }
        stage('Logging and deploying to Docker Hub') {
            when {
                expression {
                    BRANCH_NAME == 'main'  
                }
            }
            // input {
            //     message "Which stage are you at?"
            //     ok "Done"
            //     parameters {
            //         choice(name: 'stage', choices: ['dev', 'test', 'prod'])
            //     }
            // }
            steps {
                script {
                    gv.deploy()

                }
            }
        
        }
        stage('git_hub') {
            steps{
                script {
                    gv.pushing_to_github()
                }
            }
        }
    }
}
