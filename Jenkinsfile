node {
	
	stage 'test'
	
	echo "test"
	// honorRefspec=false
	
	checkout scm
	
	echo "master"
	
	sh 'git fetch +refs/heads/*:refs/remotes/origin/*'
}