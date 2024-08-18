// #!/usr/bin/env groovy
// library("Jenkins_shared_library") //uncomment if you want to use the shared library
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
                    // gv.test()
                    // test()    //uncomment if you use the shared library
                        echo "Building the application ${params.VERSION}"
                        sh 'mvn test'
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
                    // gv.incrementVersion()
                    // building_image() //uncomment if you use the shared library
                    echo 'Incrementing app version...'
                    sh 'bash -c "mvn build-helper:parse-version versions:set -DnewVersion=${parsedVersion.majorVersion}.${parsedVersion.nextMinorVersion}.${parsedVersion.incrementalVersion}"'
                    def version = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def matcher = version[0][1]
                    env.IMAGE_NAME = "$matcher-$BUILD_NUMBER"
                            
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
                    // gv.build_image()
                    // building_image() //uncomment if you use the shared library
                        echo "building the jar file ..."
                        sh 'mvn clean package'

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
                    // gv.build_con()
                    // build_jar("omarsala78/my-rep:jvm-5") //uncomment if you use the shared library
                        echo "Building the container"
                        sh "docker build -t omarsala78/my-rep:$IMAGE_NAME ."
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
                    // gv.deploy()
                    // deploy_app()  //uncomment if you use the shared library
                        withCredentials([usernamePassword(credentialsId: 'docker_hub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        echo "Logging in to Docker Hub"
                        sh 'echo $PASSWORD | docker login -u $USERNAME --password-stdin'
                        echo "Deploying the container to Docker Hub"
                        sh "docker push omarsala78/my-rep:$IMAGE_NAME"
    }
                }
            }
        }
    }
}
