package ghlzblog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "tb_doc")
public class Doc {

    @Id
    @GeneratedValue
    private Long docid;
    private String title;
    private String content;
    private String createuid;
    private long flag;
    private long rankgid;
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
	public String getCreateuid() {
		return createuid;
	}
	public void setCreateuid(String createuid) {
		this.createuid = createuid;
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

