def test() {
    echo "Building the application ${params.VERSION}"
    sh 'mvn test'
}

def pack() {
    echo "Packaging the application"
    sh '''#!/bin/bash
        mvn build-helper:parse-version versions:set \
        -DnewVersion=${parsedVersion.majorVersion}.${parsedVersion.nextMinorVersion}.${parsedVersion.incrementalVersion} \
        versions:commit
    '''
    def version = readFile('pom.xml') =~ '<version>(.+)</version>'
    def matcher = version[0][1]
    IMAGE_NAME = "$matcher-$BUILD_NUMBER"
    
    sh 'mvn clean package'
}

def build_con() {
    echo "Building the container"
    sh "docker build -t omarsala78/my-rep:jvm-${IMAGE_NAME} ."
}

def deploy() {
    withCredentials([usernamePassword(credentialsId: 'docker_hub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        echo "Logging in to Docker Hub"
        sh 'echo $PASSWORD | docker login -u $USERNAME --password-stdin'
        echo "Deploying the container to Docker Hub"
        sh "docker push omarsala78/my-rep:jvm-${IMAGE_NAME}"
    }
}

return this
