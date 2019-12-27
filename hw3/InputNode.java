
public class InputNode extends Node
{
	Integer value;
	
	//Set a value of neural net input
	public InputNode(Integer value)
	{
		super();
		this.value = value;
	}
	
	//Return value
	public Integer getOutput()
	{
		return value;
	}
}
