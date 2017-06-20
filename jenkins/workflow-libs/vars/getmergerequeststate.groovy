@Grab('org.gitlab4j:gitlab4j-api:4.2.0')
import org.gitlab4j.api.*

String call(String projectName, String mergeid) {
	println "Get state of merge request[$projectName][$mergeid]"
	
	GitLabApi gitLabApi = GitLabApi.login(GitLabApi.ApiVersion.V4, 'http://gitlab', 'root', 'jBCNConf2017');
	
	ProjectApi projectApi  = gitLabApi.getProjectApi()
    
  def projectId = null
  
  for(def project : projectApi.getProjects()) {
      if(projectName.equals(project.getName())) {
          projectId = project.getId()
          break
      }
  }
	
	MergeRequestApi mergeRequestApi = gitLabApi.getMergeRequestApi()
  
  /*
  def merge_id = null
  
  for(def mergeRequest : mergeRequestApi.getMergeRequests(projectId)){
    println "merge request[$mergeid][${mergeRequest.iid}][${mergeRequest.id}]"
    
    if(mergeid == "${mergeRequest.iid}") {
       merge_id = mergeRequest.getId()
       break
    }       		
	}
  
  println "merge request id[${merge_id}]"  
  */    

  String state = null
  
  try {
    //state = mergeRequestApi.getMergeRequest(projectId, mergeid.toInteger()).getState()
    MergeRequestApi2 mergeRequestApi2 = new MergeRequestApi2(gitLabApi)
    state =  mergeRequestApi2.getState(projectId, mergeid.toInteger())
  } catch(Exception e) {
    //Ignore.
    println "Error to get the state of merge request[$projectId][$mergeid]: $e"
  }
  
  println "$state"
  
	return state
}