// Declare IMAGE_NAME at a higher scope
def IMAGE_NAME = ""

def test() {
    echo "Building the application ${params.VERSION}"
    sh 'mvn test'
}

def incrementVersion() {
    echo 'Incrementing app version...'
    sh '''
        mvn build-helper:parse-version versions:set \
        -DnewVersion=${parsedVersion.majorVersion}.${parsedVersion.minorVersion}.${parsedVersion.newIncrementalVersion} \
        versions:commit
    '''
    def version = readFile('pom.xml') =~ '<version>(.+)</version>'
    def matcher = version[0][1]
    IMAGE_NAME = "${matcher}-${BUILD_NUMBER}"
}

def build_image() {
    echo "Building the jar file ..."
    sh 'mvn clean package'
}

def build_con() {
    echo "Building the container"
    sh '''
        #!/bin/bash
        docker build -t omarsala78/my-rep:${IMAGE_NAME} .
    '''
}

def deploy() {
    withCredentials([usernamePassword(credentialsId: 'docker_hub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        echo "Logging in to Docker Hub"
        sh 'echo $PASSWORD | docker login -u $USERNAME --password-stdin'
        echo "Deploying the container to Docker Hub"
        sh '''
            #!/bin/bash
            docker push omarsala78/my-rep:${IMAGE_NAME}
        '''
    }
}

return this
