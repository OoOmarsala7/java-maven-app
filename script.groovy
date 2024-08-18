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
    sh "docker build -t omarsala78/my-rep:$IMAGE_NAME ."
}

def deploy() {
    withCredentials([usernamePassword(credentialsId: 'docker_hub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        echo "Logging in to Docker Hub"
        sh 'echo $PASSWORD | docker login -u $USERNAME --password-stdin'
        echo "Deploying the container to Docker Hub"
        sh "docker push omarsala78/my-rep:$IMAGE_NAME"
    }
}

def pushing_to_github() {    
    echo "Pushing to GitHub"
    withCredentials([string(credentialsId: 'jenkins_token', variable: 'TOKEN')]) {
        // Configure Git user details
        sh 'git config --global user.email "jenkins@example.com"'
        sh 'git config --global user.name "jenkins"'

        // Stash any local changes to prevent conflicts
        sh 'git stash'
        
        // Fetch the latest changes from the remote repository
        sh 'git fetch origin'
        
        // Check out the main branch
        sh 'git checkout main'
        
        // Merge remote changes into the local branch
        sh 'git merge origin/main'
        
        // Apply stashed changes
        sh 'git stash pop'
        
        // Stage and commit any changes
        sh 'git add .'
        sh "git commit -m 'ci: jenkins automated commit'"
        
        // Set the remote URL with the token and push changes
        sh "git remote set-url origin https://${TOKEN}@github.com/OoOmarsala7/java-maven-app.git"
        sh 'git push origin main'
    }
}






    
return this
