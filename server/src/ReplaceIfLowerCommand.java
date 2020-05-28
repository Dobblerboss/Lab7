import java.util.Map;

public class ReplaceIfLowerCommand extends ReplaceIfLowerCommandUnex implements ExecuteCommand {
    public ReplaceIfLowerCommand(ReplaceIfLowerCommandUnex commandUnex){
        this.user = commandUnex.user;
        this.newFlat = commandUnex.newFlat;
        this.key = commandUnex.key;
    }
    @Override
    public String execute() {
        Map.Entry<Integer,Flat> MainFlat = MainServer.flats.entrySet().stream()
                .filter(x -> MainServer.relation.get(x.getKey()).equals(user))
                .filter(x -> x.getKey().equals(key))
                .findFirst()
                .get();
        if (newFlat.compareTo(MainFlat.getValue())<0){
            MainServer.flats.put(MainFlat.getKey(),newFlat);
            return "Заменено";
        }else {
            return "Введённое значение оказался больше";
        }
    }
}
