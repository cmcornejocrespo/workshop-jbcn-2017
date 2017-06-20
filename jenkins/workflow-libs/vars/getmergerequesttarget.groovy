@Grab('org.gitlab4j:gitlab4j-api:4.2.0')
import org.gitlab4j.api.*

String call(String projectName, String mergeid) {
	println "Get target branch of merge request[$projectName][$mergeid]"
	
	GitLabApi gitLabApi = GitLabApi.login('http://gitlab', 'root', 'jBCNConf2017');
	
	ProjectApi projectApi  = gitLabApi.getProjectApi()
    
    def projectId = null
    
    for(def project : projectApi.getProjects()) {
        if(projectName.equals(project.getName())) {
            projectId = project.getId()
            break
        }
    }
	
	MergeRequestApi mergeRequestApi = gitLabApi.getMergeRequestApi()
  
  String target = null
  
  try {
    target = mergeRequestApi.getMergeRequest(projectId, mergeid.toInteger()).getTargetBranch()
  } catch(Exception e) {
    //Ignore.
    println "Error to get target branch of merge request[$projectId][$mergeid]: $e"
  }

	return target
}