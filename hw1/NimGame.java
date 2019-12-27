import java.util.ArrayList;
import java.util.List;

public class NimGame
{
    private int marbles;
    private int currentMarbles;
    private int[] moves;
    private boolean[] nimChart;
    private List<boolean[]> periodInfo;

    /**
     * Given the set of moves and current state of the game this function will generate a perfect move
     * Trys move until a true is hit in the Nim chart
     * If a move cannot be found it plays the smallest move it can to give the other player more chances to make a mistake
     * @return The perfect legal move
     */
    public int getPerfectMove()
    {
        if (NimUtilities.isWin(currentMarbles,moves) == false)
        {
            return moves[0];
        }

        for (int m : moves)
        {
            if (currentMarbles - m >= 0)
            {
                if (!NimUtilities.isWin(currentMarbles - m,moves))
                {
                    return m;
                }
            }

        }
        return 0;
    }

    /**
     * Returns the set of moves assigned to this game of Nim
     * @return The set of moves
     */
    public int[] getMoves()
    {
        return moves.clone();
    }

    /**
     * Returns the current number of marbles in the pile
     * @return The current number of marbles in the pile
     */
    public int getCurrentMarbles()
    {
        return currentMarbles;
    }

    /**
     * Takes a specified number of marbles from the pile
     * @param amount The amount of marbles to take
     * @return true if the game has been won otherwise false
     */
    public boolean takeMarbles(int amount)
    {
        currentMarbles -= amount;
        if (currentMarbles <= 0)
        {
            currentMarbles = marbles;
            return false;
        }
        return true;
    }

    /**
     * Creates a game of Nim based on the provided set of moves and marbles
     * @param marbles The default number of marbles
     * @param moves The set of Moves
     */
    NimGame(int marbles, int[] moves)
    {
        this.marbles = marbles;
        currentMarbles = this.marbles;
        this.moves = moves.clone();
        int movesLength = this.moves.length;
        nimChart = NimUtilities.produceNimArray(this.marbles*movesLength^2,this.moves);
        //period = findPeriod(nimChart, this.moves[movesLength - 1]);
        periodInfo = NimUtilities.findPeriod(moves[moves.length-1]*4,moves);
    }

    /**
     * Returns infomation about the game of Nim
     * @return A string that includes the Nim chart, period, and period offset
     */
    public String toString()
    {
        String game = "";
        int entry = 0;
        String outputGuide = "N  ";
        String outputChart = "W :";
        for (int n = 0; n <= marbles; n++)
        {
            boolean i = nimChart[n];
            outputGuide += " " + entry;
            if (i)
            {
                outputChart += ("" + entry).replaceAll(".", " ") + "T";
            }
            else
            {
                outputChart += ("" + entry).replaceAll(".", " ") + "F";
            }
            entry++;
        }
        game += outputGuide + "\n" + outputChart;

        game += "\n" + "Period: " + periodInfo.get(0).length + " Sequence:";
        for (boolean i : periodInfo.get(0))
        {
            if (i)
            {
                game += " T";
            }
            else
            {
                game += " F";
            }
        }

        int periodOffset = periodInfo.get(1).length;
        if (periodOffset != 0)
        {
            game += "\nOffset: " + periodOffset + " repeats for N >= " + periodOffset+ " ";
            game += "Initial values:";
            for (boolean i : periodInfo.get(1))
            {
                if (i)
                {
                    game += " T";
                }
                else
                {
                    game += " F";
                }
            }

        }
        else
        {
            game += "\nNo period offset";
        }
        game += "\n";

        return game;
    }
}
