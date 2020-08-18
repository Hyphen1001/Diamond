package ghlzblog.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_docrank")
public class Docrank {
    @Id
    @GeneratedValue
    private Long rid;
    private Long gid;
    private Long docid;
    private Long drank;
	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	public Long getDocid() {
		return docid;
	}
	public void setDocid(Long docid) {
		this.docid = docid;
	}
	public Long getDrank() {
		return drank;
	}
	public void setDrank(Long drank) {
		this.drank = drank;
	}
    
}
