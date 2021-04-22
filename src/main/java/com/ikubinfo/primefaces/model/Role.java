package com.ikubinfo.primefaces.model;

public class Role {
	private long roleId;
	private String name;
	
	
	public Role(long roleId, String name) {
		super();
		this.roleId = roleId;
		this.name = name;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", name=" + name + "]";
	}
	

}
