import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class InputManager
{
    /**
     * Passes user input onto the NimGame instance
     *
     * @param game The game of Nim to play
     */
    private static void playNim(NimGame game)
    {
        System.out.println("There are " + game.getCurrentMarbles() + " marbles in the pile");
        System.out.println("You must choose to remove " + Arrays.toString(game.getMoves()) + " marbles at a time");

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        int humanMove, gameMove;
        int[] moves = game.getMoves();
        while (true)
        {
            gameMove = game.getPerfectMove();
            System.out.println("The game takes " + gameMove + " marbles");
            if (!game.takeMarbles(gameMove))
            {
                System.out.println("The game wins!");
                return;
            }

            System.out.println(game.getCurrentMarbles() + " Marbles left");

            moveVerificationLoop:
            while (true)
            {
                try
                {
                    humanMove = Integer.valueOf(userInput.readLine());

                    for (int move : moves)
                    {
                        if (humanMove == move)
                        {
                            break moveVerificationLoop;
                        }
                    }
                    System.out.println("Move not legal, try again");
                } catch (IOException e)
                {
                    System.out.println("Enter only single integers");
                }
            }

            System.out.println("You took " + humanMove + " marbles");

            if (!game.takeMarbles(humanMove))
            {
                System.out.println("The human wins!");
                return;
            }
        }
    }

    private static int[] getMovesFromInput(String input)
    {
        String[] moveStrings = input.split(" ", 0);
        int[] moves = new int[moveStrings.length];
        for (int i = 0; i < moveStrings.length; i++)
        {
            moves[i] = Integer.parseInt(moveStrings[i]);
        }
        return moves;
    }

    /**
     * Process user input
     *
     * @param args The command line arguments
     */
    public static void main(String[] args)
    {
        //try
        //{
            //Settings marbles moves x1 x2...
            int argsLength = args.length;
            if (argsLength <= 0) printUsage();
            String setting = args[0];

            int[] moves = getMovesFromInput(args[1]);

            if (setting.equals("period"))
            {
                //Print and exit
                List<boolean[]> periodInfo = NimUtilities.findPeriod(moves[moves.length-1]*4,moves);
                String periodString = "Period: " + periodInfo.get(0).length + " Pattern:";
                for (boolean i : periodInfo.get(0))
                {
                    if (i)
                    {
                        periodString += " T";
                    }
                    else
                    {
                        periodString += " F";
                    }
                }

                int periodOffset = periodInfo.get(1).length;
                if (periodOffset != 0)
                {
                    periodString += "\nOffset: " + periodOffset + " repeats for N >= " + periodOffset + " ";
                    periodString += "Initial values:";
                    for (boolean i : periodInfo.get(1))
                    {
                        if (i)
                        {
                            periodString += " T";
                        }
                        else
                        {
                            periodString += " F";
                        }
                    }
                }
                else
                {
                    periodString += "\nNo period offset";
                }
                System.out.println(periodString);
                System.exit(0);
            }
            else
            {
                int marbles = Integer.parseInt(args[2]);
                switch (setting)
                {
                    case "isWin":
                        boolean isWon = NimUtilities.isWin(marbles, moves);
                        System.out.println(isWon);
                        if (isWon)
                        {
                            System.out.println("The first player will win");
                        }
                        else
                        {
                            System.out.println("The first player will lose");
                        }
                        break;
                    case "period":

                        break;
                    case "play":
                        NimGame nimGame = new NimGame(marbles, moves);
                        playNim(nimGame);
                        break;
                    default:
                        printUsage();
                        break;
                }
            }

        /*} catch (Exception e)
        {
            System.out.println(e);
            printUsage();
        }
        */
    }

    /**
     * In the event the input cannot be process, the usage is printed and the program terminated
     */
    private static void printUsage()
    {
        System.out.println("Usage:");
        //System.out.println("<setting> <marbles> \"moves\"");
        System.out.println("<setting> \"moves\" <marbles>");
        System.out.println("Settings: isWin, period, play");
        System.exit(1);
    }
}
