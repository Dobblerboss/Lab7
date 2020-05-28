public class CheckingConnection extends Thread {
    private long timer_s;
    private long timer_f;
    private long timer_d;
    public CheckingConnection(){
        timer_s = System.currentTimeMillis();
        timer_d = 5000;
        timer_f = timer_s + timer_d;
    }
    @Override
    public void run() {
        while (true){
            if(timer_f<=System.currentTimeMillis()){
                System.out.println("Ошибка подключения. Проверьте подключение к интернету или включите сервер. Выхожу из программы");
                System.exit(0);
            }
        }
    }
}
