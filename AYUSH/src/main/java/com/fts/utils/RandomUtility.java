package com.fts.utils;

import java.util.Random;

public final class RandomUtility
{
    private RandomUtility()
    {

    }

    private static final Random RANDOM = new Random();

    /**
     * Creates a random string whose length is the number of characters specified. Characters will be chosen from the set of all characters.
     * 
     * @param count the length of random string to create
     * @return the random string
     */
    public static String random(int count)
    {
        return random(count, false, false);
    }

    /**
     * Creates a random string whose length is the number of characters specified. Characters will be chosen from the set of characters whose ASCII value is between <code>32</code>
     * and <code>126</code> (inclusive).
     * 
     * @param count the length of random string to create
     * @return the random string
     */
    public static String randomAscii(int count)
    {
        return random(count, 32, 127, false, false);
    }

    /**
     * Creates a random string whose length is the number of characters specified. Characters will be chosen from the set of alphabetic characters.
     * 
     * @param count the length of random string to create
     * @return the random string
     */
    public static String randomAlphabetic(int count)
    {
        return random(count, true, false);
    }

    /**
     * Creates a random string whose length is the number of characters specified. Characters will be chosen from the set of alpha-numeric characters.
     * 
     * @param count the length of random string to create
     * @return the random string
     */
    public static String randomAlphanumeric(int count)
    {
        return random(count, true, true);
    }

    /**
     * Creates a random string whose length is the number of characters specified. Characters will be chosen from the set of numeric characters.
     * 
     * @param count the length of random string to create
     * @return the random string
     */
    public static String randomNumeric(int count)
    {
        return random(count, false, true);
    }

    /**
     * Creates a random string whose length is the number of characters specified. Characters will be chosen from the set of alpha-numeric characters as indicated by the arguments.
     * 
     * @param count the length of random string to create
     * @param letters if <code>true</code>, generated string will include alphabetic characters
     * @param numbers if <code>true</code>, generated string will include numeric characters
     * @return the random string
     */
    public static String random(int count, boolean letters, boolean numbers)
    {
        return random(count, 0, 0, letters, numbers);
    }

    /**
     * Creates a random string whose length is the number of characters specified. Characters will be chosen from the set of alpha-numeric characters as indicated by the arguments.
     * 
     * @param count the length of random string to create
     * @param start the position in set of chars to start at
     * @param end the position in set of chars to end before
     * @param letters if <code>true</code>, generated string will include alphabetic characters
     * @param numbers if <code>true</code>, generated string will include numeric characters
     * @return the random string
     */
    public static String random(int count, int start, int end, boolean letters, boolean numbers)
    {
        return random(count, start, end, letters, numbers, null, RANDOM);
    }

    /**
     * Creates a random string based on a variety of options, using default source of randomness. This method has exactly the same semantics as
     * {@link #random(int,int,int,boolean,boolean,char[],Random)}, but instead of using an externally supplied source of randomness, it uses the internal static {@link Random}
     * instance.
     * 
     * @param count the length of random string to create
     * @param start the position in set of chars to start at
     * @param end the position in set of chars to end before
     * @param letters only allow letters?
     * @param numbers only allow numbers?
     * @param chars the set of chars to choose randoms from. If <code>null</code>, then it will use the set of all chars.
     * @return the random string
     * @throws ArrayIndexOutOfBoundsException if there are not <code>(end - start) + 1</code> characters in the set array.
     */
    public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars)
    {
        return random(count, start, end, letters, numbers, chars, RANDOM);
    }

    /**
     * Creates a random string based on a variety of options, using supplied source of randomness. If start and end are both <code>0</code>, start and end are set to
     * <code>' '</code> and <code>'z'</code>, the ASCII printable characters, will be used, unless letters and numbers are both <code>false</code>, in which case, start and end are
     * set to <code>0</code> and <code>Integer.MAX_VALUE</code>. If set is not <code>null</code>, characters between start and end are chosen. This method accepts a user-supplied
     * {@link Random} instance to use as a source of randomness. By seeding a single {@link Random} instance with a fixed seed and using it for each call, the same random sequence
     * of strings can be generated repeatedly and predictably.
     * 
     * @param count the length of random string to create
     * @param start the position in set of chars to start at
     * @param end the position in set of chars to end before
     * @param letters only allow letters?
     * @param numbers only allow numbers?
     * @param chars the set of chars to choose randoms from. If <code>null</code>, then it will use the set of all chars.
     * @param random a source of randomness.
     * @return the random string
     * @throws ArrayIndexOutOfBoundsException if there are not <code>(end - start) + 1</code> characters in the set array.
     * @throws IllegalArgumentException if <code>count</code> &lt; 0.
     * @since 2.0
     */
    public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars, Random random)
    {
        if (count == 0)
        {
            return "";
        }
        else if (count < 0)
        {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        if ((start == 0) && (end == 0))
        {
            end = 'z' + 1;
            start = ' ';
            if (!letters && !numbers)
            {
                start = 0;
                end = Integer.MAX_VALUE;
            }
        }

        StringBuffer buffer = new StringBuffer();
        int gap = end - start;

        while (count-- != 0)
        {
            char ch;
            if (chars == null)
            {
                ch = (char) (random.nextInt(gap) + start);
            }
            else
            {
                ch = chars[random.nextInt(gap) + start];
            }
            if ((letters && numbers && Character.isLetterOrDigit(ch)) || (letters && Character.isLetter(ch)) || (numbers && Character.isDigit(ch)) || (!letters && !numbers))
            {
                buffer.append(ch);
            }
            else
            {
                count++;
            }
        }
        return buffer.toString();
    }

    /**
     * Creates a random string whose length is the number of characters specified. Characters will be chosen from the set of characters specified.
     * 
     * @param count the length of random string to create
     * @param chars the String containing the set of characters to use, may be null
     * @return the random string
     * @throws IllegalArgumentException if <code>count</code> &lt; 0.
     */
    public static String random(int count, String chars)
    {
        if (chars == null)
        {
            return random(count, 0, 0, false, false, null, RANDOM);
        }
        return random(count, chars.toCharArray());
    }

    /**
     * Creates a random string whose length is the number of characters specified. Characters will be chosen from the set of characters specified.
     * 
     * @param count the length of random string to create
     * @param chars the character array containing the set of characters to use, may be null
     * @return the random string
     * @throws IllegalArgumentException if <code>count</code> &lt; 0.
     */
    public static String random(int count, char[] chars)
    {
        if (chars == null)
        {
            return random(count, 0, 0, false, false, null, RANDOM);
        }
        return random(count, 0, chars.length, false, false, chars, RANDOM);
    }
}
