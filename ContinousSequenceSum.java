import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Programm for finding continuous sequence with the highest sum
 * @author Paskal Drelichowski
 * @version 1.0
 */

public class ContinousSequenceSum 
{
    @Test
    public void testSum()
    {
        assertEquals(15, summeArray(new int[] {1,2,3,4,5}));
        assertEquals(62, summeArray(new int[] {2,4,8,16,32}));
        assertEquals(-15, summeArray(new int[] {-1,-2,-3,-4,-5}));
        assertEquals(-62, summeArray(new int[] {-2,-4,-8,-16-32}));
        assertEquals(0, summeArray(new int[] {-2,-4,-8,12,2}));
    }

    @Test
    public void testFindSeq()
    {
        assertArrayEquals(new int[] {1,2,3,4,5}, findSequenzLargestSum(new int[] {1,2,3,4,5}));
        assertArrayEquals(new int[] {2,4,8,16,32}, findSequenzLargestSum(new int[] {2,4,8,16,32}));
        assertArrayEquals(new int[] {-1,-2}, findSequenzLargestSum(new int[] {-1,-2,-3,-4,-5}));
        assertArrayEquals(new int[] {-2,-4}, findSequenzLargestSum(new int[] {-2,-4,-8,-16-32}));
        assertArrayEquals(new int[] {12,2}, findSequenzLargestSum(new int[] {-2,-4,-8,12,2}));
        
        int[] arr1 = {2,-8,3,-2,4,-10};
        int[] arr2 = {5,-7,6,3,-1,-8,6,-1,-2,7};

        assertArrayEquals(new int[] {3,-2,4}, findSequenzLargestSum(arr1));
        assertArrayEquals(new int[] {6,-1,-2,7}, findSequenzLargestSum(arr2));
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    
    @Test
    public void testArray()
    {
        assertArrayEquals(new int[] {1,2,3,4}, continuousSequenceArray(new int[] {1,2,3,4,5}, new int[] {0,3}));
        assertArrayEquals(new int[] {16,32}, continuousSequenceArray(new int[] {2,4,8,16,32},new int[] {3,4}));

        exceptionRule.expect(IndexOutOfBoundsException.class);
        exceptionRule.expectMessage("Index 7 out of bounds for length 5");
        continuousSequenceArray(new int[] {-1,-2,-3,-4,-5},new int[] {4,7});
    }

    /**
    * Finding the sequence with the highest sum
    * @param {int[]} Array of values to proceed Algorithm
    * @return int[] Array of the continous sequence with the highest sum
    **/
    public static int[] findSequenzLargestSum(int array[])
    {
        int summe=0;
        //It's not allowed to be 0 cause -12 could be for example the highest number
        Integer highest=null;
        //Storage for the start- and endindex of the highest sum of a continuous sequence
        int[] indexes = new int[2];
        for(int i=0; i<array.length;i++)
        {
            summe=array[i];
            for(int j=i+1;j<array.length;j++)
            {
                summe+=array[j];
                if(highest==null)
                {
                    highest=summe; 
                }
                 if(summe>=highest)
                {
                        highest=summe;
                        indexes[0]=i;
                        indexes[1]=j;
                }
            }
        }
        return continuousSequenceArray(array, indexes);
    }

    /**
    * Function for getting the sum of all values to any given integer array
    * @param {int[]} Array of values
    * @return int the sum of all values in an array
    **/
    public static int summeArray(int[] array)
    {
        int sum=0;
        for(int i=0; i<array.length; i++)
        {
            sum+=array[i];
        }
        return sum;
    }

    /**
    * Create a subarray of a bigger array
    * @param {int[]} Array of values
    * @param {int[]} Array with indexes of the continuous sequence with the highest sum
    * @throws IndexOutOfBoundsException
    * @return int[] the sum of all values in an array
    **/
    public static int[] continuousSequenceArray(int[] array, int[] indexes) throws IndexOutOfBoundsException
    {
        int[] result = new int [indexes[1]-indexes[0]+1];
        //If the Subarray has an index out of the length of the given array
        if(array.length<=indexes[0])
        {
            throw new IndexOutOfBoundsException("Index "+ indexes[0] +" out of bounds for length "+array.length);
        }
        //If the Subarray has an index out of the length of the given array
        else if(array.length<=indexes[1])
        {
            throw new IndexOutOfBoundsException("Index "+ indexes[1] +" out of bounds for length "+array.length);
        }
        else
        {
            int j=0;
            for(int i=indexes[0]; i<=indexes[1]; i++)
            {
                result[j]=array[i];
                j++;
            }
        }
        return result;
    }

    /**
    * Create a smaller array
    * @param {int[]} Array of values
    * @return String formated output for the result
    **/
    public static String arrayOutputIndexes(int[] array)
    {
        String output="(ie., {";
        for(int i=0; i<array.length; i++)
        {
            if(i<array.length-1)
            {
                output+=array[i]+", ";
            }
            else
            {
                output+=array[i]+"})"; 
            }
        }
        return output;
    }
}