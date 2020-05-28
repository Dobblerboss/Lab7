import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class UpdateIdCommand extends UpdateIdCommandUnex implements ExecuteCommand {
    public UpdateIdCommand(UpdateIdCommandUnex commandUnex){
        this.id = commandUnex.id;
        this.newFlat = commandUnex.newFlat;
        this.user = commandUnex.user;
    }
    @Override
    public String execute() {
        AtomicInteger key = new AtomicInteger();
        AtomicBoolean yes = new AtomicBoolean(false);
        MainServer.flats.entrySet().stream()
                .filter(x -> id==x.getValue().getId())
                .findFirst()
                .ifPresent(x -> { key.set(x.getKey());  yes.set(true); });
        if(yes.get()){
            int keyI = key.get();
            if(!MainServer.relation.get(keyI).equals(user)){
                return "Этот элемент не принадлежит вам";
            }
            MainServer.flats.put(keyI, newFlat);
            return "Элемент заменён";
        } else {
            return "Нет элемента с таким id";
        }


    }
}
