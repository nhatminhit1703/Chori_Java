package com.chori.entity;
// Generated Dec 8, 2016 6:21:17 PM by Hibernate Tools 5.1.0.Beta1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Role generated by hbm2java
 */
@Entity
@Table(name = "role")
public class Role implements java.io.Serializable {

	private String roleid;
	private User user;
	private String rolename;
	private String description;
	private Date createdate;
	private Set<User> users = new HashSet<User>(0);
	private Set<Function> functions = new HashSet<Function>(0);

	public Role() {
	}

	public Role(String roleid) {
		this.roleid = roleid;
	}

	public Role(String roleid, User user, String rolename, String description, Date createdate, Set<User> users,
			Set<Function> functions) {
		this.roleid = roleid;
		this.user = user;
		this.rolename = rolename;
		this.description = description;
		this.createdate = createdate;
		this.users = users;
		this.functions = functions;
	}

	@Id

	@Column(name = "ROLEID", unique = true, nullable = false, length = 20)
	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATOR")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "ROLENAME", length = 100)
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Column(name = "DESCRIPTION", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE", length = 19)
	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rolefunction", joinColumns = {
			@JoinColumn(name = "ROLEID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "FUNCTIONID", nullable = false, updatable = false) })
	public Set<Function> getFunctions() {
		return this.functions;
	}

	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}

}