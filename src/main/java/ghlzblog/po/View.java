package ghlzblog.po;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_view")
public class View {
	@Id
    @GeneratedValue
    private long viewrecordid;
 	private long docid;
 	private long uid;
	private Date time;

	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public long getViewrecordid() {
		return viewrecordid;
	}
	public void setViewrecordid(long viewrecordid) {
		this.viewrecordid = viewrecordid;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public long getDocid() {
		return docid;
	}
	public void setDocid(long docid) {
		this.docid = docid;
	}
}
