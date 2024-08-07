import java.util.LinkedList;
import java.util.Queue;

public class BFS {

    static int targetRow=-1, targetCol=-1;
    static boolean isNull=false;
    static int[][]  myArr=new int[50][50];

    public static int[] pair(String line){
        String[] pair=line.split(",");
        return new int[]{Integer.parseInt(pair[1]), Integer.parseInt(pair[0])};
    }

    public static String[] points(String line){
        return line.split(" ");
    }

    public static void printBoard(int[][] arr){
        for (int[] ints : arr) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    public static void drawSnake(int[][] arr, String[] points, int id){
        String prev=null;
        for(String i: points){
            arr[pair(i)[0]][pair(i)[1]]=id;
            if(prev!=null){
                if(pair(i)[0]==pair(prev)[0]){
                    for(int q=Math.min(pair(i)[1],pair(prev)[1]); q<Math.max(pair(i)[1],pair(prev)[1]);q++){
                        arr[pair(i)[0]][q]=id;
                    }
                }
                if(pair(i)[1]==pair(prev)[1]){
                    for(int q=Math.min(pair(i)[0],pair(prev)[0]); q<Math.max(pair(i)[0],pair(prev)[0]);q++){
                        arr[q][pair(i)[1]]=id;
                    }
                }
            }
            prev=i;
        }
    }

    public static void findShortestPath(int[][] arr, int startRow, int startCol) {
        isNull=false;
        int rows = 50;
        int cols = 50;
        Queue<Pos> vis = new LinkedList<>();
        Pos[][] prev = new Pos[arr.length][arr[0].length];
        boolean[][] visited = new boolean[rows][cols];

        Pos target = new Pos(targetRow, targetCol);
        //arr[targetRow][targetCol]=0;

        visited[startRow][startCol] = true;
        vis.offer(new Pos(startRow, startCol));

        // Define the 4 possible movements: up, down, left, right
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        int currentRow = startRow, currentCol= startCol;

        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        while (!vis.isEmpty()) {
            Pos pos = vis.poll();
            if (pos.x==targetRow && pos.y == targetCol) {
                target = new Pos(pos.x, pos.y);
                break;
            }

            for (int i = 0; i < 4; i++) {
                currentRow = pos.x + dx[i];
                currentCol = pos.y + dy[i];

                if (!isValid(arr, currentRow, currentCol) || visited[currentRow][currentCol]) continue;

                vis.offer(new Pos(currentRow, currentCol));
                visited[currentRow][currentCol] = true;
                prev[currentRow][currentCol] = new Pos(pos.x, pos.y);
            }
        }

        // If the target was reached, backtrack to mark the path with '*'
        if (visited[targetRow][targetCol]) {
            Pos pos = target;
            while (pos != null && !(pos.x == startRow && pos.y == startCol) && pos.x!=-1 && pos.y!=-1) {
                arr[pos.x][pos.y] = 6;
                pos = prev[pos.x][pos.y];
            }
        } else {
            isNull=true;
        }
    }

    public static boolean isValid(int[][] arr, int r, int c) {
        return r >= 0 && r < arr.length && c >= 0 && c < arr[0].length && arr[r][c] == 0;
    }

    static class Pos {
        int x, y;
        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
