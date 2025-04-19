package fita;

public class User {
    private String userCode;
    private String fullName;
    private String address;
    private String className;
    private String password;
    private String role;

    public User(String userCode, String fullname, String address, String className, String password, String role) {
        this.userCode = userCode;
        this.fullName = fullname;
        this.address = address;
        this.className = className;
        this.password = password;
        this.role = role;
    }
    public String getUserCode() 
    {
    	return userCode; 
    }
    public void setCode(String userCode) 
    {
    	this.userCode = userCode; 
    }
    public String getFullName() 
    {
    	return fullName; 
    }
    public void setFullName(String fullname) 
    {
    	this.fullName = fullname; 
    }

    public String getAddress() 
    {
    	return address; 
    }
    public void setAddress(String address) 
    {
    	this.address = address; 
    }
    public String getClassName() 
    {
    	return className; 
    }
    public void setClassName(String className) 
    {
    	this.className = className; 
    }
    public String getPassword() 
    {
    	return password; 
    }
    public void setPassword(String password) 
    {
    	this.password = password; 
    }

    public String getRole() 
    {
    	return role; 
    }
    public void setRole(String role) 
    {
    	this.role = role; 
    }
}
