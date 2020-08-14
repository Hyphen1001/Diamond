package ghlzblog.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_grouprecord")
public class Grouprecord {
 	@Id
    @GeneratedValue
    private long grouprecordid;
 	private long gid;
 	private long uid;
	public long getGid() {
		return gid;
	}
	public void setGid(long gid) {
		this.gid = gid;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
 	
}
