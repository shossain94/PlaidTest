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
            stage('Review node and npm installations') {
  			steps {
   				 nodejs(nodeJSInstallationName: 'node13') {
    			  sh 'npm -v'  //substitute with your code
     			  sh 'node -v'
   					 }
  					}
				   }
            stage('Install Dependencies'){
                steps{
                    sh 'npm install'
                    sh 'npm install wdio-allure-reporter --save-dev'
                    sh 'npm install -g allure-commandline --save-dev'
                    sh 'docker pull elgalu/selenium'
                    sh 'docker pull dosel/zalenium'
                }
            }
            }
        }
            stage ('Start Zalenium'){
                steps{
                    sh 'docker run --rm -ti --name zalenium -d -p 4444:4444 -e PULL_SELENIUM_IMAGE=true -v /var/run/docker.sock:/var/run/docker.sock -v /tmp/videos:/home/seluser/videos --privileged dosel/zalenium start'
                }
            }
            stage ('Run Tests'){
                steps{
                    sh 'mvn clean verify'
                }
            }
            stage ('Generate Allure Reports'){
                steps{
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
                }
            }
            stage ('Stop Zalenium'){
                steps{
                    sh 'docker stop zalenium'
                }
            }
    }
}