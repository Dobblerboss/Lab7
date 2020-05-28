import sun.applet.Main;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ClearCommand extends ClearCommandUnex implements ExecuteCommand {
    ClearCommand(ClearCommandUnex e){
        this.user = e.user;
    }
    public String execute(){

        LinkedHashMap<Integer,Flat> newMap = new LinkedHashMap<>();
        MainServer.flats
                    .entrySet()
                    .stream()
                    .filter(x -> !MainServer.relation.get(x.getKey()).equals(user))
                    .forEach(x -> newMap.put(x.getKey(),x.getValue()));
        MainServer.flats
                .entrySet()
                .stream()
                .filter(x -> MainServer.relation.get(x.getKey()).equals(user))
                .forEach(x -> MainServer.relation.remove(x.getKey()));
        MainServer.flats = newMap;

        return "Коллекция очистилась от ваших элементов";
    }

}
