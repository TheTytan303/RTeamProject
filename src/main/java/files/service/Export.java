package files.service;

import Graph.Relationship;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Export {
	private StringBuilder exportSting;
	private String filename;

	public void Export(){
		exportSting = new StringBuilder();
	}

	public void Export(String filename){
		Export();
		this.filename=filename;
	}


	public void addRelation(String from, String to, String arrowDescription){
		from = from.replace(":",";");
		to = to.replace(":",";");
		arrowDescription = arrowDescription.replace(":",";");
		String str = from+"->"+to+": "+arrowDescription+'\n';
		exportSting.append(str);
	}

	public void setFilename(String filename){
		this.filename = filename;
	}

	public void save(){
		try {
			BufferedWriter writer =  new BufferedWriter(new FileWriter(filename));
			writer.write(exportSting.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
