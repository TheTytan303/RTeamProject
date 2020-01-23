package files.service;

import java.util.HashMap;
import java.util.Map;

public class StoriesExportAdapter {
	Export export;
	Map<String,String> exportNamesMap;

	public StoriesExportAdapter(){
		export = new Export();
		exportNamesMap = new HashMap<>();
	}

	public void addStory1Relation(String fromFile, String toFile){
		export.addRelation(exportNamesMap.get(fromFile),exportNamesMap.get(toFile));
	}

	public void addStory1Participant(String name,String count){
		String newName = name+"\\n"+count;
		exportNamesMap.put(name, newName);
		export.addParticipant(newName);
	}

	public void addStory2Relation(String functionCalling, String functionCalled, String count){
		//export.addRelation(exportNamesMap.get(fromFile),exportNamesMap.get(toFile));
	}


	public Export getExport(){
		return export;
	}
}
