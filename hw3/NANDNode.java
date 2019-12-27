import java.util.ArrayList;

//Nand node
public class NANDNode extends Node
{
	ArrayList<Node> inputs;
	ArrayList<Integer> weights;
	
	//Set up a nade gate from inputs
	public NANDNode(Node a, Node b)
	{
		super();

		Node one = new InputNode(1);
		inputs = new ArrayList<Node>();
		inputs.add(a);
		inputs.add(b);
		inputs.add(one);
		weights = new ArrayList<Integer>();
		weights.add(-1);
		weights.add(-1);
		weights.add(2);
	}
	
	//get nand output
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
