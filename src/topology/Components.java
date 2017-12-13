package topology;

import java.util.ArrayList;
import java.util.List;

public class Components {
	/**Array of all the connected components of the boundary of the mask*/
	public List<Component> components = new ArrayList<>();
	/**Return the number of connected components*/
	public int size(){
		return components.size();
	}
	/**Construct the list of all connected components of the given mask*/
	public Components(Boundary boundary){
		
	}
}
