pipeline {
    agent any

    environment {
        imageName = 'weather-diary-img:1.0'
        credentialsId = 'dckr_pat_Yn4kRQr3YTBVhHKhPDSbMqRoEYc'
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
                sh 'docker build -t ${env.imageName} .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withDockerRegistry(credentialsId: '${env.credentialsId}', url: '') {
                        sh 'docker push ${env.imageName}'
                }
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                kubectl create deployment weather-diary --image=${env.imageName}
                kubectl expose deployment weather-diary --type=LoadBalancer --port=8080 --target-port=80 --name=weather-diary-svc
                '''
            }
        }
    }
}
