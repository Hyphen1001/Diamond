package ghlzblog.po;

import javax.persistence.*;

@Entity
@Table(name = "tb_group")
public class Group {
	
	@Id
    @GeneratedValue
    private Long gid;
	private long createid;
	private String groupname;
	private String meassage;

	public long getCreateid() {
		return createid;
	}
	public void setCreateid(long createid) {
		this.createid = createid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getMeassage() {
		return meassage;
	}
	public void setMeassage(String meassage) {
		this.meassage = meassage;
	}
	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	
}
