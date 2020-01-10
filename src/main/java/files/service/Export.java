package files.service;

import Graph.Relationship;

import java.util.ArrayList;

public class Export {
	private StringBuilder exportSting;

	public void parseExportString(ArrayList<String> vertexNames, ArrayList<Long> vertexNumbers, ArrayList<Relationship> relationships){
		for (int i = 0; i<vertexNames.size(); i++) {
			for (int j = 0; j<relationships.size(); j++) {
				for (int k =0; k<relationships.get(j).getDependencies().size(); k++) {
					String string = vertexNames.get(i)+" ("+vertexNumbers.get(i)+ ")-->" + relationships.get(j).getDependencies().get(k)+": "+relationships.get(j).getInCount();
					System.out.println(string);
				}
			}
		}
	}

}
