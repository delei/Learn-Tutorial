package cn.delei.jdk.java.lang;

public class StringD {
    public static void main(String[] args) {

        String a = "hello";
        String b = "hello";

        String newA = new String("hello");
        String newB = new String("hello");

        System.out.println("****** Testing Object == ******");
        System.out.println("a==b ? :" + (a == b));
        System.out.println("newA==newB ? :" + (newA == newB));
        System.out.println("a==newA ? :" + (a == newA));

        System.out.println("***** Testing String Object intern method******");
        System.out.println("a.intern()==b.intern() ? : " + (a.intern() == b.intern()));
        System.out.println("newA.intern()==newB.intern() ? :" + (newA.intern() == newB.intern()));
        System.out.println("a.intern()==newA.intern() ? :" + (a.intern() == newA.intern()));
        System.out.println("a=a.intern() ? :" + (a == a.intern()));
        System.out.println("newA==newA.intern() ? : " + (newA == newA.intern()));

        System.out.println("****** Testing String Object equals method******");
        System.out.println("equals() method :" + a.equals(newA));

        String c = "hel";
        String d = "lo";
        final String finalc = "hel";
        final String finalgetc = getc();

        System.out.println("****** Testing Object splice ******");
        System.out.println("a==\"hel\"+\"lo\" ? :" + (a == "hel" + "lo"));
        System.out.println("a==c+d ? : " + (a == c + d));
        System.out.println("a==c+\"lo\" ? : " + (a == c + "lo"));
        System.out.println("a==finalc+\"lo\" ? :" + (a == finalc + "lo"));
        System.out.println("a==finalgetc+\"lo\" ? :" + (a == finalgetc + "lo"));
    }

    private static String getc() {
        return "hel";
    }

}
