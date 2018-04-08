node {
	
	stage 'test'
	
	echo "test"
	// honorRefspec=false
	
	checkout scm
	
	echo "master"
	
	def url = sh(returnStdout: true, script: 'git config remote.origin.url').trim()
	
	echo "repo url: ${url}"
}