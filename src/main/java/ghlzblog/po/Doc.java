package ghlzblog.po;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "tb_doc")
public class Doc {

    @Id
    @GeneratedValue
    private Long docid;
    private String title;
    private String content;
    private long createuid;
    public void setCreateuid(long createuid) {
		this.createuid = createuid;
	}
	private long flag;
    private long rankgid;
    private long drank;
	@Temporal(TemporalType.TIMESTAMP)
	private Date Time;
	private long ischange;

	
	public long getIschange() {
		return ischange;
	}

	public void setchange() {
		this.ischange = 1L;
	}
	public void setnotchange() {
		this.ischange = 0L;
	}

	public Date getTime() {
		return Time;
	}

	public void setTime(Date time) {
		Time = time;
	}

	public Long getChangcnt() {
		return changcnt;
	}

	public void setChangcnt(Long changcnt) {
		this.changcnt = changcnt;
	}

	private Long changcnt;
	public Long getDocid() {
		return docid;
	}
	public void setDocid(Long docid) {
		this.docid = docid;
	}
	public long getDrank() {
		return drank;
	}
	public void setDrank(long drank) {
		this.drank = drank;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getCreateuid() {
		return createuid;
	}

	public long getRankgid() {
		return rankgid;
	}
	public void setRankgid(long rankgid) {
		this.rankgid = rankgid;
	}
	public long getFlag() {
		return flag;
	}
	public void setFlag(long flag) {
		this.flag = flag;
	}
   

   
  







}

