node {
	stage ('Build and test the addon'){
	    checkout scm
	    try{
		    sh "mvn clean install"
		} catch (err) {
             currentBuild.result = "FAILED"
        }
	}

	stage ('Deploy to artifactory'){
        // we want to pick up the version from the pom
        def pom = readMavenPom file: 'pom.xml'

        when {
            expression { pom.version ==~ /(?i)(SNAPSHOT)/ }
        }
        steps {
    	    sh "mvn deploy"
        }
    }
}