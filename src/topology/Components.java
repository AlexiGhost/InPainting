package topology;

import java.util.ArrayList;
import java.util.List;

public class Components {
	/**Array of all the connected components of the boundary of the mask*/
	private List<Component> components = new ArrayList<>();
	/**Return the number of connected components*/
	public int size(){
		return components.size();
	}
	/**Construct the list of all connected components of the given mask*/
	public Components(Boundary boundary){
		Tag tag = new Tag(boundary);
		do{
			components.add(new Component(tag, tag.seedPoint()));
		}while(tag.seedPoint() != null);
	}
	
	public List<Component> getComponents() {
		return components;
	}
}
