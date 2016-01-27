package com.client.model;

/**
 * User logged in
 * @author gurdi
 *
 */
public class User {
	//---------------------------------------- VARIABLES ------------------------------------------------
	private Project project 			= null;
	private String login				= "";
	private String pwd					= "";
	private boolean hasRightAdmin		= false;
	private boolean	hasRightEditUsers	= false;
	
	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * Constructor
	 */
	public User(Project project){
		this(project, "");
	}
	/**
	 * Constructor
	 * @param login
	 */
	public User(Project project, String login){
		this.login = login;
		this.project = project;
	}
	//---------------------------------------- GETTER SETTER --------------------------------------------------
	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * @return the hasRightAdmin
	 */
	public boolean hasRightAdmin() {
		return hasRightAdmin;
	}
	/**
	 * @param hasRightAdmin the hasRightAdmin to set
	 */
	public void setHasRightAdmin(boolean hasRightAdmin) {
		this.hasRightAdmin = hasRightAdmin;
	}
	/**
	 * @return the hasRightEditUsers
	 */
	public boolean hasRightEditUsers() {
		return hasRightEditUsers;
	}
	/**
	 * @param hasRightEditUsers the hasRightEditUsers to set
	 */
	public void setHasRightEditUsers(boolean hasRightEditUsers) {
		this.hasRightEditUsers = hasRightEditUsers;
	}
	
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}
	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * Fill request with his fields
	 */
	public void fillRequest(StringBuilder data){
		String prefix="";
		if(data.length() != 0){
			prefix = "&";
		}
		data.append(prefix+ServerUtils.KEY_PROJECT+"="+project.getName());
		data.append("&"+ServerUtils.KEY_ID+"="+login);
		data.append("&"+ServerUtils.KEY_PWD+"="+pwd);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	
	
	
  
}
