import java.util.Arrays;
import java.util.List;

public class NimUtilities
{
    /**
     * Finds the period defined by the set of moves
     * Starting at the beginning of the sequence, the functions trys larger values until it finds a pattern
     * If no pattern can be found it moves to the next value of the sequence and repeats this process until a pattern is found
     * It assumes the period is correct until it is broken
     * Any offset is return, the default is an empty array
     * If no period can be found, it reruns empty arrays
     * @param moves
     * @return (Pattern, offset)
     */
    public static List<boolean[]> findPeriod(int marbles, int[] moves)
    {
        boolean[] nimChart = produceNimArray(marbles,moves);
        int limit = nimChart.length;

        //make a block, assume there are enough marbles
        boolean[] baseBlock;
        boolean[] currentBlock;
        boolean isBroke = false;
        //s is the starting position
        for (int s = 0; s < limit; s++)
        {
            //j is the block size
            for (int j = 2; j < limit/2 - s; j++)
            {
                baseBlock = Arrays.copyOfRange(nimChart, s, j+s);
                //try the block, k is the offset
                for (int k = j+s; k < limit - (limit % j); k += j)
                {
                    currentBlock = Arrays.copyOfRange(nimChart, k, k + j);
                    if (!Arrays.equals(currentBlock, baseBlock))
                    {
                        //Pattern has been disrupted
                        isBroke = true;
                        break;
                    }
                }
                if (!isBroke)
                {
                    return Arrays.asList(baseBlock,Arrays.copyOfRange(nimChart,0,s));
                }
                isBroke = false;
            }
        }
        //Failed to find period
        return findPeriod(marbles*4,moves);
    }

    /**
     * Uses a Nim chart to find if the first player can win
     * @param currentMarbles The number of marbles to start at
     * @param moves The set of legal moves
     * @return true if the player going first can win, otherwise false
     */
    public static boolean isWin(int currentMarbles, int[] moves)
    {
        boolean[] nimChart = produceNimArray(currentMarbles,moves);
        return nimChart[nimChart.length-1];
    }

    /**
     * Creates the sequence of cells that is used by the other functions
     * Each cell represents whether a player at that position can win assuming both players are playing perfectly
     * Any cell that can be reached by a legal move from another true cell is set to true
     * False cells are cells where no legal move leads to a true cell
     * @param marbles
     * @param moves
     * @return The Nim chart where true means that a player in that postion can win if they play perfectly
     */
    public static boolean[] produceNimArray(int marbles, int[] moves)
    {
        boolean[] nimChart = new boolean[marbles + 1];
        //nimChart[0] = false;

        for (int i = 0; i <= marbles; i++)
        {
            //if (nimChart[i] == null) nimChart[i] = false;
            if (nimChart[i] == true) continue;
            for (int m : moves)
            {
                if (i+m <= marbles)
                {
                    nimChart[i + m] = true;
                }
            }
        }
        return nimChart;
    }
}
