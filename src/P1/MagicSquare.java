package P1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class MagicSquare {

    boolean isLegalMagicSquare(String fileName) {
        String filename = "src/P1/txt/" + fileName;
        String encoding = "UTF-8";
        String content = null;
        File file = new File(filename);
        long fileLength = file.length();
        byte[] fileContent = new byte[(int) fileLength];
        int cols;
        int rows;
        try{
            FileInputStream in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        try{
            content = new String(fileContent, encoding);
        }catch (UnsupportedEncodingException e){
            System.err.println("不支持"+encoding);
            e.printStackTrace();
        }
        assert content != null;
        String[] line = content.split("\n");
        rows = line.length;
        cols = rows;
        int[][] nums = new int[rows][rows];
        int[] sumR = new int[rows];
        int[] sumC = new int[cols];
        int[] sumD = new int[2];
        for(int i = 0; i < rows; i++){
            if(line[i].split("\\.").length != 1){
                System.out.println("不能有小数");
                return  false;
            }
            if(line[i].split("-").length > 1){
                System.out.println("不能有负数");
                return  false;
            }
            String []data = line[i].split("\t");
            if(data.length != rows){
                if(i == 0)
                    System.out.println("行和列不相等");
                else
                    System.out.println("不是矩阵");
                return false;
            }
            for(int j = 0; j < rows; j++){
                try{
                    int num = Integer.parseInt(data[j]);
                    nums[i][j] = num;
                }catch (NumberFormatException e){
                    System.out.println("数据有非法符号");
                    return false;
                }
                sumR[i] += nums[i][j];
                sumC[j] += nums[i][j];
                if(i == j){
                    sumD[0] += nums[i][j];
                }
                if(i+j+1 == cols){
                    sumD[1] += nums[i][j];
                }
            }
        }
        if(sumD[0] != sumD[1]){
            return false;
        }
        int sum = sumD[0];
        for(int i = 0; i < rows; i++){
            if(sumR[i] != sum){
                return false;
            }
        }
        for(int j = 0; j < cols; j++){
            if(sumC[j] != sum){
                return false;
            }
        }
        return true;
    }
    public static void generateMagicSquare(int n) {
        if(n < 0 || n%2 == 0) {
            System.out.println("n不合法");
            return;
        }
        int[][] magic = new int[n][n];
        int row = 0, col = n/2, i, j, square = n*n;
        for(i = 1; i <= square; i++){
            magic[row][col] = i;
            if(i % n == 0)
                row++;
            else{
                if(row == 0)
                    row = n - 1;
                else
                    row--;
                if(col == (n - 1))
                    col = 0;
                else
                    col++;
            }
        }
        StringBuilder content = new StringBuilder();
        for(i = 0; i < n; i++){
            for(j = 0; j < n; j++) {
                content.append(magic[i][j]);
                content.append("\t");
            }
            content.append("\n");
        }
        try {
            Files.write(Paths.get("src/P1/txt/6.txt"), content.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        MagicSquare magic = new MagicSquare();
        for(int i = 1; i < 6; i++){
            if(magic.isLegalMagicSquare(i +".txt")){
                System.out.println(i+": True");
            }
            else{
                System.out.println(i+": False");
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入一个正整数: ");
        int num = scanner.nextInt();
        generateMagicSquare(num);
        if(magic.isLegalMagicSquare("6.txt")){
            System.out.println("6: True");
        }
        else{
            System.out.println("6: False");
        }
    }
}