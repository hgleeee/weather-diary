pipeline {
    agent any

    environment {
        imageName = 'weather-diary'
        registryCredential =
        dockerImage = ''
    }

    stages {
        stage('Git scm update') {
            steps {
                echo 'Git Scm Update'
                git url: 'https://github.com/hgleeee/weather-diary.git',
                    branch: 'main',
                    credentialsId: '생성한 github access token credential id'
            }
        }

        stage('Build') {
            steps {
                echo 'Build'
                dir ('.') {
                    sh 'gradlew clean build'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Build Docker Image'
                script {
                    dockerImage = docker.build imageName
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                echo 'Push Docker Image'
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push("1.0")
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploy'
                sh '''
                kubectl create deployment weather-diary --image=($env.imageName)
                kubectl expose deployment weather-diary --type=LoadBalancer --port=8080 --target-port=80 --name=weather-diary-svc
                '''
            }
        }
    }
}