package ghlzblog.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ghlzblog.dao.ViewRepository;
import ghlzblog.po.View;

@Service
public class ViewServiceimpl implements ViewService {

	@Autowired
	private ViewRepository viewRepository;


	@Override
	public List<View> findrecentviewbyuid(Long uid, int num) {
		List<View> viewlist=viewRepository.findByUid(uid);
		Collections.sort(viewlist, new Comparator<View>() {
			public int compare(View v1, View v2) {
				return (int) (v2.getViewrecordid()-v1.getViewrecordid());
			}
		});
		if(viewlist.size()>num)
		{
			List<View> v=new ArrayList<View>();
			for(int i=0;i<num;i++)
				v.add(viewlist.get(i));
			return v;
		}
		return viewlist;
	}
	@Override
	public void AddView(View view) {
		viewRepository.saveAndFlush(view);
		return;
	}
	@Override
	public View findbyUidAndDocid(Long uid,Long docid) {
		return viewRepository.findByUidAndDocid(uid,docid);
	}
	@Override
	public void detele(View tmpview) {
		viewRepository.delete(tmpview);
		return;
	}

	@Override
	public void deletebydocid(Long docid) {
		List<View> v=viewRepository.findByDocid(docid);
		for(int i=0;i<v.size();i++) {
			viewRepository.delete(v.get(i));
		}

	}
}

