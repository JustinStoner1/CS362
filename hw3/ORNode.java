import java.util.ArrayList;

public class ORNode extends Node
{
	ArrayList<Node> inputs;
	ArrayList<Integer> weights;
		

	//Make an or node
	public ORNode(Node a, Node b)
	{
		super();

		inputs = new ArrayList<Node>();
		inputs.add(a);
		inputs.add(b);
		weights = new ArrayList<Integer>();
		weights.add(1);
		weights.add(1);
	}
		
	//Get or output
	public Integer getOutput()
	{
		Integer out = 0;
		for (int n = 0; inputs.size() > n; n++)
		{
			out += inputs.get(n).getOutput() * weights.get(n);
		}
		return Main.binaryClamp(out);
	}
}
