import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import za.ac.wits.snake.DevelopmentAgent;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

//0-up;   1-down;   2-left;   3-right;  4-left;   5-straight;  6-right
public class MyAgent extends DevelopmentAgent{
    double n=0;
    public static void main(String args[]) {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }

    //[row, col]
    public String getDirection(String head, String next){
        int[] headi=BFS.pair(head), nexti=BFS.pair(next);
        if(headi[0]==nexti[0] && headi[1]>nexti[1]) return "right";
        else if (headi[0]==nexti[0] && headi[1]<nexti[1]) return "left";
        else if (headi[1]==nexti[1] && headi[0]>nexti[0]) return "down";
        return "up";
    }

    public boolean isValid(int row, int col, int[][] arr){
        return row<50 && col<50 && row>=0 && col>=0 && (arr[row][col] == 0 || arr[row][col] == 6);
    }

    public boolean isWeakValid(int row, int col, int[][] arr){
        return row<50 && col<50 && row>=0 && col>=0 && (arr[row][col] == 0 || arr[row][col]==9 || arr[row][col]==90);
    }

    public boolean isWeakerValid(int row, int col, int[][] arr){
        return row<50 && col<50 && row>=0 && col>=0 && (arr[row][col]==9 || arr[row][col]==90);
    }


    //Never used, remove this entire function
    public void avoidApple(String head, String next, String apple, int[][] arr){
        int[] headi=BFS.pair(head), applei=forApple(apple);
        if(getDirection(head,next).equals("up") && headi[0]-1==applei[0] && headi[1]==applei[1]){
            if(isWeakValid(headi[0], headi[1]-1, arr)){ System.out.println(4);}
            else System.out.println(6);
        } else if (getDirection(head,next).equals("down") && headi[0]+1==applei[0] && headi[1]==applei[1]) {
            if(isWeakValid(headi[0], headi[1]-1, arr)){ System.out.println(6);}
            else System.out.println(4);
        } else if (getDirection(head,next).equals("right") && headi[0]==applei[0] && headi[1]+1==applei[1]) {
            if(isWeakValid(headi[0]+1, headi[1], arr)){ System.out.println(6);}
            else System.out.println(4);
        } else if (getDirection(head,next).equals("left") && headi[0]==applei[0] && headi[1]-1==applei[1]) {
            if(isWeakValid(headi[0]+1, headi[1], arr)){ System.out.println(4);}
            else System.out.println(6);
        }
    }


    //The parameter 'next' is never used
    public boolean oneMove(String head, String next, String apple){
        int[] headi=BFS.pair(head), applei=forApple(apple);
        if(headi[0]-1==applei[0] && headi[1]==applei[1]) return true;
        else if (headi[0]+1==applei[0] && headi[1]==applei[1]) return true;
        else if (headi[0]==applei[0] && headi[1]+1==applei[1]) return true;
        else return headi[0] == applei[0] && headi[1] - 1 == applei[1];
    }

    public void makeValidMove(String head, String next, int[][] arr){
        int[] headi=BFS.pair(head);
        int r=headi[0], c=headi[1];
        if(getDirection(head,next).equals("up")){
            String a="up";
            if(isWeakerValid(r-1,c,arr) && isWeakerValid(r,c-1,arr) && !isValid(r,c+1,arr)){
                if(arr[r-1][c]==9) System.out.println(5);
                else if (arr[r][c-1]==9) System.out.println(4);
                return;
            }
            if(isWeakerValid(r-1,c,arr) && isWeakerValid(r,c+1,arr) && !isValid(r,c-1,arr)){
                if(arr[r-1][c]==9) System.out.println(5);
                else if (arr[r][c+1]==9) System.out.println(6);
                return;
            }
            if(isValid(r,c-1,arr) && isValid(r,c+1,arr)){
                if((!isTunnel(r,c-1,"left", arr) && !isTunnel(r,c+1,"right",arr)) || (isTunnel(r,c-1,"left",arr) && isTunnel(r,c+1,"right",arr))){
                int count=1;
                while(isValid(r,c-count,arr) && isValid(r,c+count,arr)){
                    count++;
                }
                if(!isValid(r,c-count,arr)) System.out.println(6);
                else System.out.println(4);
                } else if (isTunnel(r,c-1,"left",arr)) {
                    System.out.println(6);
                } else System.out.println(4);
            }
            else if(isValid(r, c-1, arr) && !isTunnel(r,c-1,"left",arr)){ System.out.println(4);} //check for both and see which is best, iterate
            else if (isValid(r,c+1,arr) && !isTunnel(r,c+1,"right",arr)) {System.out.println(6);}
            else if (isValid(r-1,c,arr) && !isTunnel(r-1,c,a,arr)) {System.out.println(5);}
            else if(isWeakValid(r, c-1, arr)){ System.out.println(4);}
            else if (isWeakValid(r,c+1,arr)) {System.out.println(6);}
            else System.out.println(5);
        } else if (getDirection(head,next).equals("down")) {
            String a="down";
            if(isWeakerValid(r+1,c,arr) && isWeakerValid(r,c-1,arr) && !isValid(r,c+1,arr)){
                if(arr[r+1][c]==9) System.out.println(5);
                else if (arr[r][c-1]==9) System.out.println(6);
                return;
            }
            if(isWeakerValid(r+1,c,arr) && isWeakerValid(r,c+1,arr) && !isValid(r,c-1,arr)){
                if(arr[r+1][c]==9) System.out.println(5);
                else if (arr[r][c+1]==9) System.out.println(4);
                return;
            }
            if(isValid(r,c-1,arr) && isValid(r,c+1,arr)){
                if((!isTunnel(r,c-1,"left", arr) && !isTunnel(r,c+1,"right",arr)) || (isTunnel(r,c-1,"left",arr) && isTunnel(r,c+1,"right",arr))){
                int count=1;
                while(isValid(r,c-count,arr) && isValid(r,c+count,arr)){
                    count++;
                }
                if(!isValid(r,c-count,arr)) System.out.println(4);
                else System.out.println(6);
                } else if (isTunnel(r,c-1,"left",arr)) {
                    System.out.println(4);
                } else System.out.println(6);

            }
            else if(isValid(r,c-1,arr) && !isTunnel(r,c-1,"left",arr)){System.out.println(6);}
            else if (isValid(r,c+1,arr) && !isTunnel(r,c+1,"right",arr)) {System.out.println(4);}
            else if (isValid(r+1,c,arr) && !isTunnel(r+1,c,a,arr)) {System.out.println(5);}
            else if(isWeakValid(r, c-1, arr)){ System.out.println(6);}
            else if (isWeakValid(r,c+1,arr)) {System.out.println(4);}
            else System.out.println(5);
        } else if (getDirection(head,next).equals("right")) {
            String a="right";
            if(isWeakerValid(r,c+1,arr) && isWeakerValid(r+1,c,arr) && !isValid(r-1,c,arr)){
                if(arr[r][c+1]==9) System.out.println(5);
                else if (arr[r+1][c]==9) System.out.println(6);
                return;
            }
            if(isWeakerValid(r,c+1,arr) && isWeakerValid(r-1,c,arr) && !isValid(r+1,c,arr)){
                if(arr[r][c+1]==9) System.out.println(5);
                else if (arr[r-1][c]==9) System.out.println(4);
                return;
            }
            if(isValid(r,c+1, arr) && !isTunnel(r,c+1,a,arr))  {System.out.println(5);}
            else if(isValid(r-1,c,arr) && isValid(r+1,c,arr)){
                if((!isTunnel(r-1,c,"up", arr) && !isTunnel(r+1,c,"down",arr)) || (isTunnel(r-1,c,"up",arr) && isTunnel(r+1,c,"down",arr))){
                int count=1;
                while(isValid(r-count,c,arr) && isValid(r+count,c,arr)){
                    count++;
                }
                if(!isValid(r-count,c,arr)) System.out.println(6);
                else System.out.println(4);
                } else if (isTunnel(r-1,c,"up",arr)) {
                    System.out.println(6);
                } else System.out.println(4);
            }
            else if (isValid(r+1,c,arr) && !isTunnel(r+1,c,"down",arr)) {System.out.println(6);}
            else if (isValid(r-1,c,arr) && !isTunnel(r-1,c,"up",arr))System.out.println(4);
            else if (isWeakValid(r,c+1,arr)) {System.out.println(5);}
            else if (isWeakValid(r+1,c,arr)) {System.out.println(6);}
            else System.out.println(4);
        } else {
            String a="left";
            if(isWeakerValid(r,c-1,arr) && isWeakerValid(r+1,c,arr) && !isValid(r-1,c,arr)){
                if(arr[r][c-1]==9) System.out.println(5);
                else if (arr[r+1][c]==9) System.out.println(4);
                return;
            }
            if(isWeakerValid(r,c-1,arr) && isWeakerValid(r-1,c,arr) && !isValid(r+1,c,arr)){
                if(arr[r][c-1]==9) System.out.println(5);
                else if (arr[r-1][c]==9) System.out.println(6);
                return;
            }
            if(isValid(r,c-1,arr) && !isTunnel(r,c-1,a,arr)){System.out.println(5);}
            else if(isValid(r-1,c,arr) && isValid(r+1,c,arr)){
                if((!isTunnel(r-1,c,"up", arr) && !isTunnel(r+1,c,"down",arr)) || (isTunnel(r-1,c,"up",arr) && isTunnel(r+1,c,"down",arr))){
                int count=1;
                while(isValid(r-count,c,arr) && isValid(r+count,c,arr)){
                    count++;
                }
                if(!isValid(r-count,c,arr)) System.out.println(4);
                else System.out.println(6);
                } else if (isTunnel(r-1,c,"up",arr)) {
                    System.out.println(4);
                } else System.out.println(6);
            }
            else if (isValid(r+1,c,arr) && !isTunnel(r+1,c,"down",arr)) {System.out.println(4);}
            else if (isValid(r-1,c,arr) && !isTunnel(r-1,c,"up",arr)){System.out.println(6);}
            else if (isWeakValid(r,c-1,arr)) {System.out.println(5);}
            else if (isWeakValid(r+1,c,arr)) { System.out.println(4);}
            else System.out.println(6);
        }
    }

    public int[] forApple(String apple){
        String[] apples=apple.split(" ");
        return new int[]{Integer.parseInt(apples[1]), Integer.parseInt(apples[0])};
    }

    public  void experiment(String head, String next, int[][] arr){  //avoid head on collisions, doesn't solve turn collisions
        int[] headi=BFS.pair(head);
        int r=headi[0], c=headi[1];
        if(getDirection(head, next).equals("up")){
            if(isValid(r-1,c,arr ))arr[r-1][c]=90;
            if(isValid(r,c-1,arr)) arr[r][c-1]=9;
            if(isValid(r,c+1,arr)) arr[r][c+1]=9;
        }
        else if (getDirection(head,next).equals("down")) {
            if(isValid(r+1,c,arr))arr[r+1][c]=90;
            if(isValid(r,c-1,arr)) arr[r][c-1]=9;
            if(isValid(r,c+1,arr)) arr[r][c+1]=9;
        }
        else if (getDirection(head,next).equals("right")) {
            if(isValid(r,c+1,arr)) arr[r][c+1]=90;
            if(isValid(r+1,c,arr)) arr[r+1][c]=9;
            if(isValid(r-1,c,arr)) arr[r-1][c]=9;
        }
        else if (getDirection(head,next).equals("left")) {
            if(isValid(r,c-1,arr)) arr[r][c-1]=90;
            if(isValid(r-1,c,arr)) arr[r-1][c]=9;
            if(isValid(r+1,c,arr)) arr[r+1][c]=9;
        }
    }

    public void closeEdgeTunnel(int[][] arr, String head, String next){
        //deal when snake is near wall, close that tunnel
        int[] headi=BFS.pair(head);
        if(getDirection(head,next).equals("up") || getDirection(head,next).equals("down")){
            if(headi[1]==1) arr[headi[0]][headi[1]-1]=9;
            if(headi[1]==48) arr[headi[0]][headi[1]+1]=9;
        } else if (getDirection(head,next).equals("left") || getDirection(head,next).equals("right")) {
            if(headi[0]==1) arr[headi[0]-1][headi[1]]=9;
            if(headi[0]==48) arr[headi[0]+1][headi[1]]=9;
        }
    }

    public int[] triplet(int row, int col, String orientation){
        if(orientation.equals("hor")){
            if(col-1>=0 && col+1<=49) return new int[]{row, col-1, row,col,row,col+1};
            if(col-1>=0) return new int[]{row,col-1,row,col,-1,-1};
            return new int[]{-1,-1,row,col,row,col+1};
        } else {
            if(row-1>=0 && row+1<=49) return new int[]{row+1, col, row,col,row-1,col};
            if(row-1>=0) return new int[]{-1,-1,row,col,row-1,col};
            return new int[]{row+1,col,row,col,-1,-1};
        }
    }

    public boolean isTunnel(int row, int col, String direction,int[][] arr) {
        int[] triplet;
        int counter=0;
        switch (direction) {
            case "up" : {
                triplet = triplet(row, col, "hor");
                while (isValid(triplet[2], triplet[3], arr)) {
                    if(counter++>4) return true;
                    if (!isValid(triplet[0], triplet[1], arr) && !isValid(triplet[4], triplet[5], arr)) {
                        triplet = triplet(--row, col, "hor");
                        continue;
                    }
                    return false;
                }
            }
            case "down" : {
                triplet = triplet(row, col, "hor");
                while (isValid(triplet[2], triplet[3], arr)) {
                    if(counter++>4) return true;
                    if (!isValid(triplet[0], triplet[1], arr) && !isValid(triplet[4], triplet[5], arr)) {
                        triplet = triplet(++row, col, "hor");
                        continue;
                    }
                    return false;
                }
            }
            case "left" : {
                triplet = triplet(row, col, "ver");
                while (isValid(triplet[2], triplet[3], arr)) {
                    if(counter++>4) return true;
                    if (!isValid(triplet[0], triplet[1], arr) && !isValid(triplet[4], triplet[5], arr)) {
                        triplet = triplet(row, --col, "ver");
                        continue;
                    }
                    return false;
                }
            }
            default : {
                triplet = triplet(row, col, "ver");
                while (isValid(triplet[2], triplet[3], arr)) {
                    if(counter++>4) return true;
                    if (!isValid(triplet[0], triplet[1], arr) && !isValid(triplet[4], triplet[5], arr)) {
                        triplet = triplet(row, ++col, "ver");
                        continue;
                    }
                    return false;
                }
            }
        }
        return true;
    }


        @Override
        public void run () {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                String initString = br.readLine();
                String[] temp = initString.split(" ");
                int nSnakes = Integer.parseInt(temp[0]);
                String prev = "";
                int prevSize=-1, currentSize=-1;
                String prevTail="", currentTail="";

                while (true) {
                    String line = br.readLine();

                    if (line.contains("Game Over")) {
                        break;
                    }

                    if (!prev.equals(line)) {
                        prev = line;
                        n = 5;
                    }
                    n -= 0.1;
                    BFS.myArr = new int[50][50];

                    String[] appleSPos = line.split(" ");
                    int[] applePos = {Integer.parseInt(appleSPos[0]), Integer.parseInt(appleSPos[1])};
                    BFS.targetRow = applePos[1];
                    BFS.targetCol = applePos[0];

                    if (BFS.targetRow < 0 || BFS.targetRow > 49 || BFS.targetCol < 0 || BFS.targetCol > 49)
                        System.out.println("log " + line);

                    //ArrayList<String> allPoints = new ArrayList<>();

                    // read in obstacles and do something with them!
                    int nObstacles = 3;
                    for (int obstacle = 0; obstacle < nObstacles; obstacle++) {
                        String obs = br.readLine();
                        String[] points = BFS.points(obs);
                        //allPoints.add(points[0]);
                        //allPoints.add(points[points.length - 1]);
                        for (String i : points) {
                            int[] pair = BFS.pair(i);
                            BFS.myArr[pair[0]][pair[1]] = 7;
                        }
                    }

                    int mySnakeNum = Integer.parseInt(br.readLine());
                    int[] headi = {-1, -1};
                    int[] taili={-1,-1};
                    //int length = 0;
                    boolean myOneMove = false; //otherOneMove = false;
                    String[] mesnake = {};
                    for (int i = 0; i < nSnakes; i++) {
                        String snakeLine = br.readLine();
                        String[] split = snakeLine.split(" ");
                        if (i == mySnakeNum) {
                            //hey! That's me :)
                            String head = split[3], tail=split[split.length-1];
                            currentTail=tail;
                            System.out.println("log " + head);
                            //length = Integer.parseInt(split[1]);
                            headi = BFS.pair(head);
                            taili=BFS.pair(tail);
                            currentSize=Integer.parseInt(split[1]);
                            myOneMove = oneMove(head, split[4], line);
                            mesnake = split;
                        }
                        if (split[0].equals("alive")) {
                            String[] points = new String[split.length - 3];
                            System.arraycopy(split, 3, points, 0, split.length - 3);
                            BFS.drawSnake(BFS.myArr, points, i + 1);
                            //Snake tail stays in the same position after chowing, so assume tail can kill you
                        }
                        if (i != mySnakeNum && split[0].equals("alive")) {
                            //boolean a = oneMove(split[3], split[4], line);
                            //if (a) otherOneMove = a;
                            experiment(split[3], split[4], BFS.myArr);
                            closeEdgeTunnel(BFS.myArr, split[3], split[4]);
                        }
                        //do stuff with other snakes
                    }

                    if(!isValid(BFS.targetRow-1,BFS.targetCol-1,BFS.myArr) && !isValid(BFS.targetRow-1,BFS.targetCol,BFS.myArr) && !isValid(BFS.targetRow-1,BFS.targetCol+1,BFS.myArr)
                        && !isValid(BFS.targetRow+1,BFS.targetCol-1,BFS.myArr) && !isValid(BFS.targetRow+1,BFS.targetCol,BFS.myArr) && !isValid(BFS.targetRow+1,BFS.targetCol+1,BFS.myArr)) {
                        if(isValid(BFS.targetRow, BFS.targetCol-1,BFS.myArr)) BFS.myArr[BFS.targetRow][BFS.targetCol-1]=9;
                        if(isValid(BFS.targetRow, BFS.targetCol+1,BFS.myArr)) BFS.myArr[BFS.targetRow][BFS.targetCol+1]=9;
                    }  else if (!isValid(BFS.targetRow-1,BFS.targetCol-1,BFS.myArr) && !isValid(BFS.targetRow,BFS.targetCol-1,BFS.myArr) && !isValid(BFS.targetRow+1,BFS.targetCol-1,BFS.myArr)
                            && !isValid(BFS.targetRow-1,BFS.targetCol+1,BFS.myArr) && !isValid(BFS.targetRow,BFS.targetCol+1,BFS.myArr) && !isValid(BFS.targetRow+1,BFS.targetCol+1,BFS.myArr)){
                        if(isValid(BFS.targetRow-1, BFS.targetCol,BFS.myArr)) BFS.myArr[BFS.targetRow-1][BFS.targetCol]=9;
                        if(isValid(BFS.targetRow+1, BFS.targetCol,BFS.myArr)) BFS.myArr[BFS.targetRow+1][BFS.targetCol]=9;
                    }

                    if(headi[1]-BFS.targetCol==1 && headi[0]==BFS.targetRow && !isValid(BFS.targetRow+1,BFS.targetCol,BFS.myArr) && !isValid(BFS.targetRow, BFS.targetCol-1,BFS.myArr) && isTunnel(BFS.targetRow-1,BFS.targetCol,"up",BFS.myArr)){
                        if(BFS.myArr[BFS.targetRow][BFS.targetCol]!=90) BFS.myArr[BFS.targetRow][BFS.targetCol]=9;}
                    if(headi[1]-BFS.targetCol==1 && headi[0]==BFS.targetRow && !isValid(BFS.targetRow-1,BFS.targetCol,BFS.myArr) && !isValid(BFS.targetRow, BFS.targetCol-1,BFS.myArr) && isTunnel(BFS.targetRow+1,BFS.targetCol,"down",BFS.myArr)){
                        if(BFS.myArr[BFS.targetRow][BFS.targetCol]!=90) BFS.myArr[BFS.targetRow][BFS.targetCol]=9;}
                    if(BFS.targetCol-headi[1]==1 && headi[0]==BFS.targetRow && !isValid(BFS.targetRow+1,BFS.targetCol,BFS.myArr) && !isValid(BFS.targetRow, BFS.targetCol+1,BFS.myArr) && isTunnel(BFS.targetRow-1,BFS.targetCol,"up",BFS.myArr)){
                        if(BFS.myArr[BFS.targetRow][BFS.targetCol]!=90) BFS.myArr[BFS.targetRow][BFS.targetCol]=9;}
                    if(BFS.targetCol-headi[1]==1 && headi[0]==BFS.targetRow && !isValid(BFS.targetRow-1,BFS.targetCol,BFS.myArr) && !isValid(BFS.targetRow, BFS.targetCol+1,BFS.myArr) && isTunnel(BFS.targetRow+1,BFS.targetCol,"down",BFS.myArr)){
                        if(BFS.myArr[BFS.targetRow][BFS.targetCol]!=90) BFS.myArr[BFS.targetRow][BFS.targetCol]=9;}

                    if(headi[0]-BFS.targetRow==1 && headi[1]==BFS.targetCol && !isValid(BFS.targetRow-1,BFS.targetCol,BFS.myArr) && !isValid(BFS.targetRow, BFS.targetCol-1,BFS.myArr) && isTunnel(BFS.targetRow,BFS.targetCol+1,"right",BFS.myArr)){
                        if(BFS.myArr[BFS.targetRow][BFS.targetCol]!=90) BFS.myArr[BFS.targetRow][BFS.targetCol]=9;}
                    if(headi[0]-BFS.targetRow==1 && headi[1]==BFS.targetCol && !isValid(BFS.targetRow-1,BFS.targetCol,BFS.myArr) && !isValid(BFS.targetRow, BFS.targetCol+1,BFS.myArr) && isTunnel(BFS.targetRow,BFS.targetCol-1,"left",BFS.myArr)){
                        if(BFS.myArr[BFS.targetRow][BFS.targetCol]!=90) BFS.myArr[BFS.targetRow][BFS.targetCol]=9;}
                    if(BFS.targetRow-headi[0]==1 && headi[1]==BFS.targetCol && !isValid(BFS.targetRow+1,BFS.targetCol,BFS.myArr) && !isValid(BFS.targetRow, BFS.targetCol-1,BFS.myArr) && isTunnel(BFS.targetRow,BFS.targetCol+1,"right",BFS.myArr)){
                        if(BFS.myArr[BFS.targetRow][BFS.targetCol]!=90) BFS.myArr[BFS.targetRow][BFS.targetCol]=9;}
                    if(headi[0]-BFS.targetRow==1 && headi[1]==BFS.targetCol && !isValid(BFS.targetRow+1,BFS.targetCol,BFS.myArr) && !isValid(BFS.targetRow, BFS.targetCol+1,BFS.myArr) && isTunnel(BFS.targetRow,BFS.targetCol-1,"left",BFS.myArr)){
                        if(BFS.myArr[BFS.targetRow][BFS.targetCol]!=90) BFS.myArr[BFS.targetRow][BFS.targetCol]=9;}

                    if(n<-7) BFS.myArr[BFS.targetRow][BFS.targetCol]=9;

                    if(!currentTail.equals(prevTail)){
                        BFS.myArr[taili[0]][taili[1]]=0;
                        prevTail=currentTail;
                    }

//                    int counter=-1;
//                    if(prevSize==currentSize){
//                        BFS.myArr[taili[0]][taili[1]]=0;
//                        counter--;}
//                    else{
//                        counter=5;
//                        prevSize=currentSize;
//                    }

                    BFS.findShortestPath(BFS.myArr, headi[0], headi[1]);
                    if(BFS.isNull){
                        BFS.targetRow=taili[0];
                        BFS.targetCol=taili[1];
                      BFS.findShortestPath(BFS.myArr,headi[0],headi[1]);
                    }

                    System.err.println(BFS.isNull);

                    ArrayList<ArrayList<Integer>> per = new ArrayList<>();
                    per.add(new ArrayList<>());
                    per.add(new ArrayList<>());
                    per.add(new ArrayList<>());
                    for (int i = 0; i < 3; i++)
                        for (int j = 0; j < 3; j++) {
                            per.get(i).add(-1);
                        }

                    if (headi[0] - 1 >= 0 && headi[1] - 1 >= 0) per.get(0).set(0, BFS.myArr[headi[0] - 1][headi[1] - 1]);
                    if (headi[0] - 1 >= 0) per.get(0).set(1, BFS.myArr[headi[0] - 1][headi[1]]);
                    if (headi[0] - 1 >= 0 && headi[1] + 1 <= 49) per.get(0).set(2, BFS.myArr[headi[0] - 1][headi[1] + 1]);
                    if (headi[1] - 1 >= 0) per.get(1).set(0, BFS.myArr[headi[0]][headi[1] - 1]);
                    per.get(1).set(1, BFS.myArr[headi[0]][headi[1]]);
                    if (headi[1] + 1 <= 49) per.get(1).set(2, BFS.myArr[headi[0]][headi[1] + 1]);
                    if (headi[0] + 1 <= 49 && headi[1] - 1 >= 0) per.get(2).set(0, BFS.myArr[headi[0] + 1][headi[1] - 1]);
                    if (headi[0] + 1 <= 49) per.get(2).set(1, BFS.myArr[headi[0] + 1][headi[1]]);
                    if (headi[0] + 1 <= 49 && headi[1] + 1 <= 49) per.get(2).set(2, BFS.myArr[headi[0] + 1][headi[1] + 1]);

//                    System.err.println(per.get(0));
//                    System.err.println(per.get(1));
//                    System.err.println(per.get(2));
//
                    for (int i = 0; i < 50; i++) {
                        // Iterate through columns
                        for (int j = 0; j < 50; j++) {
                            if(BFS.myArr[i][j]==90) System.err.print(5 + " ");
                            else System.err.print(BFS.myArr[i][j]+" ");
                        }
                        System.err.println(); // Move to the next line after each row
                    }
                    System.err.println();
                    System.err.println();

                    if (BFS.isNull/*&& length >= 150*/) {

                        //if(myOneMove && n<-3.8) avoidApple(mesnake[3],mesnake[4],line,BFS.myArr);
                        makeValidMove(mesnake[3], mesnake[4], BFS.myArr);
                    //} else if (myOneMove && otherOneMove) {
                        //avoidApple(mesnake[3], mesnake[4], line, BFS.myArr);
                    } else if (headi[0] != 49 && BFS.myArr[headi[0] + 1][headi[1]] == 6) {
                        System.out.println(1);
                        System.err.println(1);
                    } else if (headi[1] != 49 && BFS.myArr[headi[0]][headi[1] + 1] == 6) {
                        System.out.println(3);
                        System.err.println(3);
                    } else if (headi[0] != 0 && BFS.myArr[headi[0] - 1][headi[1]] == 6) {
                        System.out.println(0);
                        System.err.println(0);
                    } else if (headi[1] != 0 && BFS.myArr[headi[0]][headi[1] - 1] == 6) {
                        System.out.println(2);
                        System.err.println(2);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
