pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'Java'
        // git 'Git'
    }

    environment {
        SPRING_DATASOURCE_URL      = 'jdbc:mysql://localhost:3306/PETCLINIC_DB?useSSL=false&serverTimezone=UTC'
        SPRING_DATASOURCE_USERNAME = 'admin'
        SPRING_DATASOURCE_PASSWORD = 'A$dmin123'
        SERVER_PORT                = '8081'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Getting code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Testing...'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Creating JAR...'
                sh 'mvn package -DskipTests'
            }
        }
    }
}