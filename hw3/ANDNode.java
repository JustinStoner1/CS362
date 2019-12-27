import java.util.ArrayList;

//And neruon
public class ANDNode extends Node
{
	ArrayList<Node> inputs;
	ArrayList<Integer> weights;
	
	//Build an and node with the given inputs
	public ANDNode(Node a, Node b)
	{
		super();

		Node one = new InputNode(1);
		inputs = new ArrayList<Node>();
		inputs.add(a);
		inputs.add(b);
		inputs.add(one);
		weights = new ArrayList<Integer>();
		weights.add(1);
		weights.add(1);
		weights.add(-1);
		Node and = new MidNode(inputs,weights);
	}
	
	//calculate boolean logic
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
