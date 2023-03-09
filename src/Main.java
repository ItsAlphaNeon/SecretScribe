import controller.SecretScribe;

public class Main {
    public static void main(String[] args) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SecretScribe secretScribe = new SecretScribe();
        secretScribe.run();
    }
}