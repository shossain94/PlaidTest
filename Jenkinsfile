pipeline {
    agent any
    stages {
        stage('Code and Dependencies'){
            parallel{
            stage('Checkout Code'){
                steps{
                    git 'https://github.com/shossain94/PlaidTest.git'
                }
            }
             stage ('Run Tests'){
                steps{
                    sh 'mvn clean verify'
                }
            }
            post {
            always {
                 archiveArtifacts artifacts: 'target/surefire-reports/html/*', fingerprint: true
                 testng "target/surefire-reports/xml/*.xml"
                 publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: './target/surefire-reports/html', reportFiles: 'extent.html', reportName: 'Extent Report', reportTitles: ''])
                 }