package projet.view;

public class Views {
    public static View current = new ConsoleView();

    public static void setCurrent(View v) {
        current = v;
    }

    public static void print(String s) {
        if (current != null) current.print(s);
    }

    public static void println(String s) {
        if (current != null) current.println(s);
    }

    public static void println() {
        if (current != null) current.println();
    }
}
