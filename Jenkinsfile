	pipeline{
    agent any
    tools{
        maven "maven"
    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Angel-Nieva/milkstgo']])               
                sh "mvn clean install"                
            }
        }
        stage("Test"){
            steps{                
                sh "mvn test"                
            }
        }
        stage("SonarQube Analysis"){
            steps{
                sh "mvn clean verify sonar:sonar -Dsonar.projectKey=pep1 -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_86e6b21c7e42458406b1c2e52492aa717192eb36"
            }
        }
        stage("Build Docker Image"){
            steps{
                sh "docker build -t angelnieva/milkstgo ."
            }
        }
        stage("Push Docker Image"){
            steps{
                withCredentials([string(credentialsId: 'dckrhubpassword', variable: 'dckpass')]) {
				    sh "docker login -u angelnieva -p ${dckpass}"
				}
				sh "docker push angelnieva/milkstgo"                
            }
        }
    }
    post{
        always{
            sh "docker logout"
        }
    }
}
