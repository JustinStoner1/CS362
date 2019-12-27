import java.util.ArrayList;

public class Nim
{
	BINARYTODECIMALNode firstBit;
	BINARYTODECIMALNode secondBit;
	BINARYTODECIMALNode thirdBit;
	ArrayList<XORNode> aXORz;
	ArrayList<XORNode> bXORz;
	ArrayList<XORNode> cXORz;
	
	//Build the neural net from scratch
	public Nim(ArrayList<Integer> pile1, ArrayList<Integer> pile2, ArrayList<Integer> pile3)//Integer pile1, Integer pile2, Integer pile3
	{
		ArrayList<InputNode> as = new ArrayList<InputNode>();
		for (int i = 0; pile1.size() > i; i++)
		{
			as.add(new InputNode(pile1.get(i)));
		}
		ArrayList<InputNode> bs = new ArrayList<InputNode>();
		for (int i = 0; pile1.size() > i; i++)
		{
			bs.add(new InputNode(pile2.get(i)));
		}
		ArrayList<InputNode> cs = new ArrayList<InputNode>();
		for (int i = 0; pile1.size() > i; i++)
		{
			cs.add(new InputNode(pile3.get(i)));
		}
		
		//get z
		ArrayList<XORNode> abXors = new ArrayList<XORNode>();
		for (int i = 0; pile1.size() > i;i++)
		{
			abXors.add(new XORNode(as.get(i),bs.get(i)));
		}
		
		ArrayList<XORNode> zs = new ArrayList<XORNode>();
		for (int i = 0; pile3.size() > i;i++)
		{
			zs.add(new XORNode(abXors.get(i),cs.get(i)));
		}
		
		//get first 3 bits
		ArrayList<ANDNode> aANDz = new ArrayList<ANDNode>();
		for (int i = 0; pile1.size() > i;i++)
		{
			aANDz.add(new ANDNode(zs.get(i),as.get(i)));
		}
		
		ArrayList<ANDNode> bANDz = new ArrayList<ANDNode>();
		for (int i = 0; pile1.size() > i;i++)
		{
			bANDz.add(new ANDNode(zs.get(i),bs.get(i)));
		}
		
		ArrayList<ANDNode> cANDz = new ArrayList<ANDNode>();
		for (int i = 0; pile1.size() > i;i++)
		{
			cANDz.add(new ANDNode(zs.get(i),cs.get(i)));
		}
		
		//Selector bits
		firstBit = new BINARYTODECIMALNode(aANDz);
		secondBit = new BINARYTODECIMALNode(bANDz);
		thirdBit = new BINARYTODECIMALNode(cANDz);
		
		//get next 18 bits
		aXORz = new ArrayList<XORNode>();
		
		for (int i = 0; pile1.size() > i;i++)
		{
			aXORz.add(new XORNode(zs.get(i),as.get(i)));
		}
		
		bXORz = new ArrayList<XORNode>();
		
		for (int i = 0; pile2.size() > i;i++)
		{
			bXORz.add(new XORNode(zs.get(i),bs.get(i)));
		}
		
		cXORz = new ArrayList<XORNode>();
		
		for (int i = 0; pile3.size() > i;i++)
		{
			cXORz.add(new XORNode(zs.get(i),cs.get(i)));
		}
		
		
	}
	
	//Get the result of the neural net based on the pile sizes
	public ArrayList<Integer> getOutput()
	{
		ArrayList<Integer> output = new ArrayList<Integer>();
		
		output.add(firstBit.getOutput());
		output.add(secondBit.getOutput());
		output.add(thirdBit.getOutput());
		
		ArrayList<Integer> pile1Move = new ArrayList<Integer>();
		for (int i = 0; aXORz.size() > i;i++)
		{
			pile1Move.add(aXORz.get(i).getOutput());
		}
		///Collections.reverse(pile1Move);
		output.addAll(pile1Move);
		
		ArrayList<Integer> pile2Move = new ArrayList<Integer>();
		for (int i = 0; bXORz.size() > i;i++)
		{
			pile2Move.add(bXORz.get(i).getOutput());
		}
		///Collections.reverse(pile2Move);
		output.addAll(pile2Move);
		
		ArrayList<Integer> pile3Move = new ArrayList<Integer>();
		for (int i = 0; cXORz.size() > i;i++)
		{
			pile3Move.add(cXORz.get(i).getOutput());
		}
		///Collections.reverse(pile3Move);
		output.addAll(pile3Move);
		
		return output;
	}
}
