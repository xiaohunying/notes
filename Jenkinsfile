node {
	
	stage 'test'
	
	echo "test"
	// honorRefspec=false
	
	checkout scm
	
	echo "master"
	
	def repoUrl = sh(returnStdout: true, script: 'git config remote.origin.url').trim()
	
	echo "repo url: ${repoUrl}"
	
	sh "git.exe fetch --no-tags --progress ${repoUrl} +refs/heads/*:refs/remotes/origin/*"
}