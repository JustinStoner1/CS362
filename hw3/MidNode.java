import java.util.ArrayList;

public class MidNode extends Node
{
	ArrayList<Integer> weights;
	ArrayList<Node> inputs;
	
	//Basic neuron
	public MidNode(ArrayList<Node> inputs, ArrayList<Integer> weights)
	{
		super();
		this.weights = weights;
		this.inputs = inputs;
	}
	
	//Get output from weights
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
