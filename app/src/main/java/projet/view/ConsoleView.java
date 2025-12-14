package projet.view;

public class ConsoleView implements View {
    @Override
    public void print(String s) {
        System.out.print(s);
    }

    @Override
    public void println(String s) {
        System.out.println(s);
    }

    @Override
    public void println() {
        System.out.println();
    }
}
