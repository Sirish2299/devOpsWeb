pipeline{
     agent any
     tools{
          maven 'local_maven'
     }

     stages{
          stage ('Build') {
               steps{
                    sh 'mvn clean package'
               }
               post{
                    success{
                         echo "Archiving the Artifacts"
                         archiveArtifacts Artifacts: '**/target/*.war'
                    }
               }
          }
          stage ('Deploy to tomcat server') {
               steps{
                    deploy adapters: [tomcat8(credentialsId: 'TomcatCreds', path: '', url: 'http://localhost:9010/')], contextPath: null, war: '**/*.war'
               }

          }
     }
}