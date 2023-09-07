pipeline {
    agent any

    environment {
        registryCredential = credentials('DockerHub_IdPwd')
        dockerImage = ''
    }

    stages {
        stage('Git scm update') {
            steps {
                git credentialsId: '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',
                url: 'https://github.com/hgleeee/weather-diary.git',
                branch: 'main'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("zdsay5863/weather-diary")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'DockerHub_IdPwd') {
                        dockerImage.push("1.0")
                    }
                }
            }
        }

        stage('Pull Image') {
            steps {
                sh 'docker pull zdsay5863/weather-diary:1.0'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                kubectl create deployment dpy-weather-diary --image=zdsay5863/weather-diary
                kubectl expose deployment dpy-weather-diary --type=LoadBalancer --port=8080 --target-port=80 --name=weather-diary-svc
                '''
            }
        }
    }
}
