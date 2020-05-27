package BoardFiles;

public class Map{
    public static final int EMPTY = 0;
    public static final int HEAD = 1;
    public static final int TAIL = 2;
    public static final int CONDUCTOR = 3;

    public final int height;
    public final int width;

    private int fields[][];
    private int tmpFields[][];

    private boolean isRun;
    private boolean isFinished;

    private String automatonName;

    public Map( int width, int height ) {
        fields = new int[height][width];
        tmpFields = new int[height][width];

        this.height = height;
        this.width = width;
        isRun = false;
        automatonName = "Wireworld";
    }

    public int getField(int x, int y) {
        try {
            return fields[y][x];
        } catch (ArrayIndexOutOfBoundsException e) {
            return EMPTY;
        }
    }

    public void setState(int x, int y, int state) {
        try {
            fields[y][x] = state;
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void changeIsRun(){
        isRun = !isRun;
    }

    public boolean isRun(){
        return isRun;
    }

    public void setIsFinished( boolean isFinished ){
        this.isFinished = isFinished;
    }

    public boolean isFinished(){
        return isFinished;
    }

    public void setAutomatonName( String name ){
        automatonName = name;
    }

    public void swapHeadTail(int x, int y) {
        try {
            if (fields[y][x] == HEAD)
                fields[y][x] = TAIL;
            else
                fields[y][x] = HEAD;
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    public void nextRound() {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                if( automatonName.equals("Wireworld") )
                    calculateWireworldStatus(j, i);
                else if( automatonName.equals("Game Of Life") )
                    calculateGameOfLifeStatus(j, i);
            }

        int tmp[][] = fields;
        fields = tmpFields;
        tmpFields = tmp;
    }

    private int countNeighbor(int x, int y, int state) {

        int counter = 0;
        int moore = 1;

        for (int i = -1; i <= moore; i++)
            for (int j = -1; j <= moore; j++)
                try {
                    if (fields[y + i][x + j] == state)
                        counter++;
                } catch (ArrayIndexOutOfBoundsException e) {
                }

        if (fields[y][x] == state)
            counter--;

        return counter;
    }

    private void calculateWireworldStatus(int x, int y) {
        if (fields[y][x] == EMPTY)
            tmpFields[y][x] = EMPTY;
        else if (fields[y][x] == HEAD)
            tmpFields[y][x] = TAIL;
        else if (fields[y][x] == TAIL)
            tmpFields[y][x] = CONDUCTOR;
        else if (countNeighbor(x, y, HEAD) == 1 || countNeighbor(x, y, HEAD) == 2)
            tmpFields[y][x] = HEAD;
        else
            tmpFields[y][x] = CONDUCTOR;
    }

    private void calculateGameOfLifeStatus( int x, int y ){
        int neigh = countNeighbor( x, y, CONDUCTOR );
        if( neigh == 3 )
            tmpFields[y][x] = CONDUCTOR;
        else if( neigh == 2 && fields[y][x] == CONDUCTOR )
            tmpFields[y][x] = CONDUCTOR;
        else
            tmpFields[y][x] = EMPTY;
    }
}