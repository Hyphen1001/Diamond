package ghlzblog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "tb_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long commentid;
    private String content;
    private String uid;
    private String docid;
    @Temporal(TemporalType.TIMESTAMP)
    private Date Time;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}

  
}
