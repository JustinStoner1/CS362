import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collections;

public class Main
{
	//starts game
	public static void main(String[] args)
	{
		try
		{
			playNim(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		}
		catch (NumberFormatException a)
		{
			System.out.println("Bad arguments");
		}

	}
	
	//Handles the game loop and gets moves from the neural net
	public static void playNim(int pileSize1, int pileSize2, int pileSize3)
	{
		Scanner userIn = new Scanner(System.in);
		//playing game
		while (true)
		{
			Integer chosenPile;
			Integer hMove;
			//get human move
			while (true)
			{
				try
				{
					System.out.println("Current pile sizes: "+pileSize1+" "+pileSize2+" "+pileSize3);
					System.out.println("Input pile number 1-3");
					chosenPile = Integer.parseInt(userIn.next());

					System.out.println("Input a legal move");
					hMove = Integer.parseInt(userIn.next());
					if (hMove <= 0)
					{
						System.out.println("Illegal move");
						break;
					}
					
					if (chosenPile == 1)
					{
						if (hMove <= pileSize1)
						{
							  				pileSize1 -= hMove;
							break;
						}
						else
						{
							System.out.println("Illegal move");
						}
					}
					else if (chosenPile == 2)
					{
						if (hMove <= pileSize2)
						{
							pileSize2 -= hMove;
							break;
						}
						else
						{
							System.out.println("Illegal move");
						}
					}
					else if (chosenPile == 3)
					{
						if (hMove <= pileSize3)
						{
							pileSize3 -= hMove;
							break;
						}
						else
						{
							System.out.println("Illegal move");
						}
					}
					else
					{
						System.out.println("Non-legal pilesize");
					}
					
				}
				catch (NumberFormatException a)
				{
					System.out.print("Bad input");
				}
			}
			//Check for a win
			if (pileSize1 <= 0 && pileSize2 <= 0 && pileSize3 <= 0)
			{
				System.out.print("Human wins");
				return;
			}
			
			Integer[] pile1Array = new Integer[6];
			Integer[] pile2Array = new Integer[6];
			Integer[] pile3Array = new Integer[6];
			
			String pile1SizeString = String.format("%6s", Integer.toBinaryString(pileSize1)).replace(' ', '0');
			for (int i = 0; 6 > i; i++)
			{
				pile1Array[i] = (Integer)((int)Integer.parseInt(""+pile1SizeString.charAt(i)));
			}
			
			String pile2SizeString = String.format("%6s", Integer.toBinaryString(pileSize2)).replace(' ', '0');
			for (int i = 0; 6 > i; i++)
			{
				pile2Array[i] = (Integer)((int)Integer.parseInt(""+pile2SizeString.charAt(i)));
			}
			
			String pile3SizeString = String.format("%6s", Integer.toBinaryString(pileSize3)).replace(' ', '0');
			for (int i = 0; 6 > i; i++)
			{
				pile3Array[i] = (Integer)((int)Integer.parseInt(""+pile3SizeString.charAt(i)));
			}
			
			ArrayList<Integer> pile1 = new ArrayList<Integer>(Arrays.asList(pile1Array));
			ArrayList<Integer> pile2 = new ArrayList<Integer>(Arrays.asList(pile2Array));
			ArrayList<Integer> pile3 = new ArrayList<Integer>(Arrays.asList(pile3Array));
			
			//Get neural net move
			Nim nim = new Nim(pile1, pile2, pile3);
			ArrayList<Integer> out = nim.getOutput();

			List<Integer> firstThreeBits = out.subList(0, 3);

			//If the game is losing
			if (firstThreeBits.get(0) == 0 && firstThreeBits.get(1) == 0 && firstThreeBits.get(2) == 0)
			{
				if (pileSize1 > 0)
				{
					pileSize1 -= 1;
					System.out.println("Pile 1 reduced to: "+pileSize1);
				}
				else if (pileSize2 > 0)
				{
					pileSize2 -= 1;
					System.out.println("Pile 2 reduced to: "+pileSize1);
				}
				else if (pileSize3 > 0)
				{
					pileSize3 -= 1;
					System.out.println("Pile 3 reduced to: "+pileSize1);
				}
				if (pileSize1 <= 0 && pileSize2 <= 0 && pileSize3 <= 0)
				{
					System.out.print("Neural net wins");
					return;
				}
				continue;
			}
			
			List<Integer> move = new ArrayList<Integer>();
			
			int pileIndex = 0;
			//Pile 1
			if (firstThreeBits.get(0) > firstThreeBits.get(1) && firstThreeBits.get(0) > firstThreeBits.get(2))
			{
				pileIndex = 1;
				move = out.subList(4, 9);
			}
			//Pile 2
			if (firstThreeBits.get(1) > firstThreeBits.get(0) && firstThreeBits.get(1) > firstThreeBits.get(2))
			{
				pileIndex = 2;
				move = out.subList(9, 15);
			}
			//Pile 3
			if (firstThreeBits.get(2) > firstThreeBits.get(1) && firstThreeBits.get(2) > firstThreeBits.get(0))
			{
				pileIndex = 3;
				move = out.subList(15, 21);
			}

			ArrayList<InputNode> moveNodes = new ArrayList<InputNode>();
			for (int i = 0; move.size() > i; i++)
			{
				moveNodes.add(new InputNode(move.get(i)));
			}

			int newPileSize = (new BINARYTODECIMALNode(moveNodes)).getOutput();

			if (pileIndex == 1)
			{
				pileSize1 = newPileSize;
				System.out.println("Pile 1 reduced to: "+pileSize1);
			}
			else if (pileIndex == 2)
			{
				pileSize2 = newPileSize;
				System.out.println("Pile 2 reduced to: "+pileSize2);
			}
			else
			{
				pileSize3 = newPileSize;
				System.out.println("Pile 3 reduced to: "+pileSize3);
			}

			if (pileSize1 <= 0 && pileSize2 <= 0 && pileSize3 <= 0)
			{
				System.out.print("Neural net wins");
				return;
			}
		}
	}

	//Clamp input between 0 and 1
	public static Integer binaryClamp(Integer x)
	{
		if (x < 0)
		{
			return 0;
		}
		else if (x > 1)
		{
			return 1;
		}
		else
		{
			return x;
		}
	}

}
