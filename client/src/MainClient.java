import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Клиентское приложение запущено");
        binding();
        Console console = new Console();
        try{
            while (true){
                console.writeCommand();
            }
        } catch (NoSuchElementException e){
            System.out.println("Выхожу из программы");
            System.exit(0);
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    private static void binding()  {
        System.out.println("Введите порт");
        try {
            Console.port = Integer.parseInt(new Scanner(System.in).nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Формат неправильный");
            binding();
        }
    }
}
