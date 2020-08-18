package ghlzblog.service;

import java.util.List;

import ghlzblog.po.Collection;
import ghlzblog.po.Doc;
import ghlzblog.po.View;

public interface DocService {
	List<Doc> findrecentviewdoc(List<View> viewlist);
	
	List<Doc> findcollectdoc(List<Collection> collectlist);

	void AddDoc(Doc doc);

	Doc checkDoc(long docid);

	List<Doc> findmydoc(Long uid);

	Doc findbydocid(Long docid);

	List<Doc> findrecycledoc(Long uid);
	
	List<Doc> findgid(Long gid);
	void deleteall(Long uid); 
	 int deletedoc(Long uid, Long docid); 
}
