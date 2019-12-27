import java.util.ArrayList;

public class XORNode extends Node
{
	ArrayList<Node> inputs;
	ArrayList<Integer> weights;
	
	//Make an xor node from other types of nodes
	public XORNode(Node a, Node b)
	{
		super();

		Node one = new InputNode(1);
		Node nand = new NANDNode(a,b);
		Node or = new ORNode(a,b);
		
		inputs = new ArrayList<Node>();
		inputs.add(nand);
		inputs.add(or);
		inputs.add(one);
		weights = new ArrayList<Integer>();
		weights.add(1);
		weights.add(1);
		weights.add(-1);
	}
	
	//Get xor of inputs
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
