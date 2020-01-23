package files.service;

import java.util.HashMap;
import java.util.Map;

public class StoriesExportAdapter {
	Export export;


	public StoriesExportAdapter(){
		export = new Export();
	}

	public void addStoryRelation(String from, String to, String in, String out){
		export.addRelation(from,to,"IN: "+in+" OUT: "+out);
	}

	public void addStoryParticipant(String name){
		export.addParticipant(name);
	}



	public Export getExport(){
		return export;
	}
}
