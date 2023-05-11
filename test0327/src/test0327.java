import java.util.Scanner;

public class test0327 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine();
        System.out.println(countLetters(in).toString());
    }

    public static String countLetters(String in) {
        int[] letterNumber = new int[26]; // 0~25对应26个字母
        for (int i = 0; i < in.length(); i++) {
            char ch = in.charAt(i); // 遍历in字符串，将每位换位char字母
            if (Character.isLetter(ch)) { // 只处理字母的char
                letterNumber[ch - 'A']++; // 统计出现次数，eg：X-A，
            }
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < letterNumber.length; i++) {
            if (letterNumber[i] > 0) {
                result.append((char) ('A' + i)); // 根据ASCII转换成字母
                result.append(letterNumber[i]); // 次数
            }
        }
        return result.toString();
    }
}
