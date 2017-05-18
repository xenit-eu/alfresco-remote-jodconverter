node {
	stage ('Build and test the addon'){
	    	checkout scm
	    	try{
			// use the id of the globally configured maven instance
			def mvnTool = tool 'maven-3.3.9'

			// execute maven
			sh "${mvnTool}/bin/mvn clean install" 
		} catch (err) {
             		currentBuild.result = "FAILED"
        	}
	}
}
