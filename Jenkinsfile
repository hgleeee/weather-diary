pipeline {
    agent any

    environment {
        dockerImage = ''
        registryCredential = 'DockerHub_IdPwd'
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
                sh 'docker build -t zdsay5863/weather-diary:1.0 .'
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push("1.0")
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                kubectl create deployment dpy-weather-diary --image=zdsay5863/weather-diary:1.0
                kubectl expose deployment dpy-weather-diary --type=LoadBalancer --port=8080 --target-port=80 --name=weather-diary-svc
                '''
            }
        }
    }
}
