def test() {
      echo "Building the application ${params.VERSION}"
      sh 'mvn test'
}

def pack() {
    echo "packging the application"
    sh 'mvn package'
}

def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def build_con() {
    echo "Building the container"
    sh "docker build -t omarsala78/my-rep:jvm-${params.VERSION} ."
}

def deploy() {
       withCredentials([usernamePassword(credentialsId: 'docker_hub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        echo "Logging in to Docker Hub"
                        sh 'echo $PASSWORD | docker login -u $USERNAME --password-stdin'
                         echo "Deploying the container to Docker Hub"
                        sh "docker build -t omarsala78/my-rep:jvm-${params.VERSION} ."
                    }
}


return this