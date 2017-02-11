package org.lt.cms.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色对象，用来对应可以访问的功能，系统中为了简单只定义了管理员，发布人员和审核人员。
 * @author li949
 *
 */
@Entity
@Table(name="t_role")
public class Role {
	private int id;
	private String name;
	private RoleType roleSn;
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Enumerated(EnumType.ORDINAL)
	public RoleType getRoleSn() {
		return roleSn;
	}
	public void setRoleSn(RoleType roleSn) {
		this.roleSn = roleSn;
	}
	
}
