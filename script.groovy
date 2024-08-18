def test() {
    echo "Building the application ${params.VERSION}"
    sh 'mvn test'
}

def incrementVersion() {
    echo 'incrementing app version...'
    sh "mvn build-helper:parse-version versions:set -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.nextMinorVersion}.\\\${parsedVersion.incrementalVersion}"
    def version = readFile('pom.xml') =~ '<version>(.+)</version>'
    def matcher = version[0][1]
    env.IMAGE_NAME = "$matcher-$BUILD_NUMBER"
    
}

def build_image() {
    echo "building the jar file ..."
        sh 'mvn clean package'

}

def build_con() {
    echo "Building the container"
    sh "docker build -t omarsala78/my-rep:slhf ."
}

def deploy() {
    withCredentials([usernamePassword(credentialsId: 'docker_hub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        echo "Logging in to Docker Hub"
        sh 'echo $PASSWORD | docker login -u $USERNAME --password-stdin'
        echo "Deploying the container to Docker Hub"
        sh "docker push omarsala78/my-rep:slhf"
    }
}

return this
