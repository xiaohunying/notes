node {
	
	stage 'test'
	
	echo "test"
	// honorRefspec=false
	
	checkout scm
	
	echo "master"
	
	def scmUrl = scm.getUserRemoteConfigs()[0].getUrl()
	
	echo "repo url: ${scmUrl}"
}