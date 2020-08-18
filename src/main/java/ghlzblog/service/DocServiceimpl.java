package ghlzblog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ghlzblog.dao.DocRepository;
import ghlzblog.po.Collection;
import ghlzblog.po.Doc;
import ghlzblog.po.View;

@Service
public class DocServiceimpl implements DocService {

	@Autowired
    private DocRepository docRepository;

	@Override
	 public List<Doc> findrecentviewdoc(List<View> viewlist) {
	  List<Doc> doclist=new ArrayList<Doc>();
	  for(int i=0;i<viewlist.size();i++) {
	   if(docRepository.findByDocid(viewlist.get(i).getDocid())!=null)
	   doclist.add(docRepository.findByDocid(viewlist.get(i).getDocid()));
	  }
	  return doclist;
	 }

	@Override
	 public List<Doc> findcollectdoc(List<Collection> collectlist) {
	  List<Doc> doclist=new ArrayList<Doc>();
	  for(int i=0;i<collectlist.size();i++) {
	   if(docRepository.findByDocid(collectlist.get(i).getDocid())!=null)
	   doclist.add(docRepository.findByDocid(collectlist.get(i).getDocid()));
	  }
	  return doclist;
	 }
	@Override
	public void AddDoc(Doc doc) {
		docRepository.saveAndFlush(doc);
		return;
	}

	public Doc checkDoc(long docid) {
		Doc doc=docRepository.findByDocid(docid);
		return doc;
	}
	@Override
	 public Doc findbydocid(Long docid) {
	  
	  return docRepository.findByDocid(docid);
	 }
	@Override
	 public List<Doc> findrecycledoc(Long uid) {
	  List<Doc> doclist=docRepository.findByCreateuid(uid);
	  List<Doc> doclistl11=new ArrayList<Doc>();
	  for(int i=0;i<doclist.size();i++) {
	   if(doclist.get(i).getFlag()==0) {
	    doclistl11.add(doclist.get(i));
	   }
	  }
	  return doclistl11;
	 }
	 
	 @Override
	 public List<Doc> findmydoc(Long uid){
	  List<Doc> doclist=docRepository.findByCreateuid(uid);
	  List<Doc> doclistl11=new ArrayList<Doc>();
	  for(int i=0;i<doclist.size();i++) {
	   if(doclist.get(i).getFlag()==1) {
	    doclistl11.add(doclist.get(i));
	   }
	  }
	  return doclistl11;
	 }
	 
		@Override
		public List<Doc> findgid(Long gid) {
			return docRepository.findByRankgid(gid);
		}
		@Override
		public int  deletedoc(Long uid,Long docid) {
			  List<Doc> doclist=docRepository.findByCreateuid(uid);
			  for(int i=0;i<doclist.size();i++) {
			   if(doclist.get(i).getFlag()==0&&doclist.get(i).getDocid()==docid) {
			    docRepository.delete(doclist.get(i));
			    return 1;
			   }
			  }
			  return 0;
			  }
		@Override
		  public void deleteall(Long uid) {
		   List<Doc> doclist=docRepository.findByCreateuid(uid);
		   for(int i=0;i<doclist.size();i++) {
		    if(doclist.get(i).getFlag()==0) {
		     docRepository.delete(doclist.get(i));
		    }
		   }
		   
		  }
}
