import java.util.ArrayList;
import java.lang.Math; 

//For converting binary digits to decimal
public class BINARYTODECIMALNode extends Node
{
	ArrayList<Node> inputs;
	ArrayList<Integer> weights;
	
	//Converts array of binary inputs to a single decimal output
	public BINARYTODECIMALNode(ArrayList inputs)
	{
		super();
		
		this.inputs = inputs;
		weights = new ArrayList<Integer>();
		
		for (int i = inputs.size()-1; 0 <= i; i--)
		{
			weights.add((Integer)((int)Math.pow(2, i)));
		}
	}
	
	//calcuate decimal valie
	public Integer getOutput()
	{
		Integer out = 0;
		for (int n = 0; inputs.size() > n; n++)
		{
			out += inputs.get(n).getOutput() * weights.get(n);
		}
		return out;
	}
}
