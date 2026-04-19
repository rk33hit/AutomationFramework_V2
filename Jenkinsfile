pipeline {
    agent any

    tools {
        maven 'Maven3'
        jdk 'Java17'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Stage 1: Pulling latest code from GitHub...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Stage 2: Compiling the project...'
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Stage 3: Running Selenium TestNG tests...'
                bat 'mvn test'
            }
        }

        stage('Report') {
            steps {
                echo 'Stage 4: Publishing test results...'
                junit testResults: 'target/surefire-reports/*.xml',
                      allowEmptyResults: true
                archiveArtifacts artifacts: 'test-output/**/*',
                                 allowEmptyArchive: true
            }
        }

        stage('Archive') {
            steps {
                echo 'Stage 5: Archiving build artifacts...'
                archiveArtifacts artifacts: 'target/*.jar',
                                 allowEmptyArchive: true
            }
        }
    }

    post {
        success {
            echo '=============================================)'
            echo 'ALL TESTS PASSED - Build Successful!'
            echo '============================================='
        }
        unstable {
            echo '============================================='
            echo 'SOME TESTS FAILED - Build Unstable!'
            echo '============================================='
        }
        failure {
            echo '============================================='
            echo 'BUILD