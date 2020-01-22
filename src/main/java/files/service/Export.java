package files.service;

import Graph.Relationship;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Export {
	private StringBuilder exportSting;
	private String filename;

	Export(){
		exportSting = new StringBuilder();
	}

	Export(String filename){
		this();
		this.filename=filename;
	}

	private String escapeColons(String str){
		return str.replace(":",";");
	}

	public void addRelation(String participantFrom, String participantTo, String arrowDescription){
		participantFrom = escapeColons(participantFrom);
		participantTo = escapeColons(participantTo);
		arrowDescription = escapeColons(arrowDescription);
		String str = participantFrom+"->"+participantTo+": "+arrowDescription+'\n';
		System.out.print(str);
		exportSting.append(str);
	}

	public void addRelation(String participantFrom, String participantTo){
		addRelation(participantFrom,participantTo,"");
	}

	public void addParticipant(String participantName){
		participantName = escapeColons(participantName);
		String str = "participant "+participantName+'\n';		exportSting.append(str);
	}

	public void setFilename(String filename){
		this.filename = filename;
	}

	public void save(){
		try {
			BufferedWriter writer =  new BufferedWriter(new FileWriter(filename));
			writer.write(exportSting.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
