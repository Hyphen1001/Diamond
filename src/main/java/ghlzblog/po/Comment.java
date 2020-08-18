package ghlzblog.po;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "tb_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long commentid;
    private String content;
    private long uid;
    private long docid;

	public Date getTime() {
		return Time;
	}

	public void setTime(Date time) {
		Time = time;
	}

	@Temporal(TemporalType.TIMESTAMP)
    private Date Time;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Long getCommentid() {
		return commentid;
	}
	public void setCommentid(Long commentid) {
		this.commentid = commentid;
	}


  
}
