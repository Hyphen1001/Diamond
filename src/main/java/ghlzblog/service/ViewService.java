package ghlzblog.service;

import java.util.List;

import ghlzblog.po.View;

public interface ViewService {


	List<View> findrecentviewbyuid(Long uid,int num);

	void AddView(View view);

	View findbyUidAndDocid(Long uid, Long docid);

	void detele(View tmpview);

	void deletebydocid(Long docid);
}
