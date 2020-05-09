package BoardFiles;

import java.util.Random;

public class Map {
    public static final int EMPTY = 0;
    public static final int HEAD = 1;
    public static final int TAIL = 2;
    public static final int CONDUCTOR = 3;

    public final int height;
    public final int width;

    private int fields[][];
    private int tmpFields[][];

    public Map(int width, int height ){
        fields = new int[height][width];
        tmpFields = new int[height][width];

        this.height = height;
        this.width = width;
    }

    public int getField( int x, int y ){
        try{
            return fields[y][x];
        } catch( ArrayIndexOutOfBoundsException e ){
            return EMPTY;
        }
    }

    public void setState( int x, int y, int state ){
        try{
            fields[y][x] = state;
        } catch( ArrayIndexOutOfBoundsException e ){}
    }

    public void swapHeadTail( int x, int y ){
        try{
            if( fields[y][x] == HEAD )
                fields[y][x] = TAIL;
            else
                fields[y][x] = HEAD;
        } catch( ArrayIndexOutOfBoundsException e ){}
    }

    public void nextRound(){
        for( int i = 0; i < height; i++ )
            for( int j = 0; j < width; j++ ){
                calculateStatus(i,j);
            }
            
        int tmp[][] = fields;
        fields = tmpFields;
        tmpFields = tmp;
    }

    public int countAdjacentHeads(int x, int y)
    {
        int counter = 0;

        int moore = 1;
            for (int i = -1; i <= moore; i++)
                for (int j = -1; j <= moore; j++)
                    try {
                    if (fields[y + i][x + j] == HEAD)
                        counter++;

            if (fields[y][x] ==  HEAD)
                counter--;
        } catch (ArrayIndexOutOfBoundsException e)
        {

        }
        return counter;
    }

    public void calculateStatus(int x, int y)
    {
        if (fields[y][x] == EMPTY)
            tmpFields[y][x] = EMPTY;
        else if (fields[y][x] == HEAD)
            tmpFields[y][x] = TAIL;
        else if (fields[y][x] == TAIL)
            tmpFields[y][x] = CONDUCTOR;
        else if (countAdjacentHeads(x,y) == 1 || countAdjacentHeads(x,y) == 2)
            tmpFields[y][x] = HEAD;
        else
            tmpFields[y][x] = CONDUCTOR;
    }
}