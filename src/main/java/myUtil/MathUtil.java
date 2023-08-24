package myUtil;

public class MathUtil {
    public static int gcd(int a, int b) {
        return b == 0? a : gcd(b, a % b);
    }
    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }
    public static Integer binaryLengthToDecimalLength(int n) {
        long ans = 1;
        while (n > 0) {
            ans<<=1;
            n--;
        }
        while (ans > 0) {
            ans=ans/10;
            n++;
        }
        return n;
    }
}
