import java.util.LinkedHashMap;

public class RemoveLowerCommand extends RemoveLowerCommandUnex implements ExecuteCommand {
    public RemoveLowerCommand(RemoveLowerCommandUnex commandUnex){
        this.user = commandUnex.user;
        this.newFlat = commandUnex.newFlat;
    }
    @Override
    public String execute() {
        LinkedHashMap<Integer,Flat> newMap = new LinkedHashMap<>();
        LinkedHashMap<Integer,Flat> userSet = new LinkedHashMap<>();
        LinkedHashMap<Integer,Flat> anotherSet = new LinkedHashMap<>();
        MainServer.flats.entrySet()
                .stream()
                .filter(x -> MainServer.relation.get(x.getKey()).equals(user))
                .forEach(x -> userSet.put(x.getKey(),x.getValue()));
        MainServer.flats.entrySet()
                .stream()
                .filter(x -> !MainServer.relation.get(x.getKey()).equals(user))
                 .forEach(x -> anotherSet.put(x.getKey(),x.getValue()));
        userSet.entrySet().stream()
                .filter(x -> {
                    int comp = newFlat.compareTo(x.getValue());
                    if(comp>0){
                        return false;
                    } else{
                        return true;
                    }
                })
                .forEach(x->newMap.put(x.getKey(),x.getValue()));
        anotherSet.entrySet().stream()
                .forEach(x -> newMap.put(x.getKey(),x.getValue()));
        int size = MainServer.flats.size();

        MainServer.flats.entrySet()
                .stream()
                .filter(x -> newMap.get(x.getKey()).equals(null))
                .forEach(x -> MainServer.relation.remove(x.getKey()));

        MainServer.flats = newMap;
        if(MainServer.flats.size() == size){
            return "Коллекция не изменилась";
        }else {
            return "Удалено "+(size- MainServer.flats.size())+" элементов";
        }

    }
}
