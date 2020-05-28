import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class InsertElementCommand extends InsertElementCommandUnex implements ExecuteCommand {
    public InsertElementCommand(InsertElementCommandUnex commandUnex){
        this.user = commandUnex.user;
        this.key = commandUnex.key;
        this.newFlat = commandUnex.newFlat;
    }
    @Override
    public String execute() {
            int size = MainServer.flats.size();
            try {
                if(!MainServer.relation.get(key).equals(user)){
                    return "Элемент с таким ключом уже существует и он не принадлежит вам";
                }
            } catch (NullPointerException e){
                // дальше
            }
            newFlat.setCreationDate();
            AtomicReference<Long> maxId = new AtomicReference(1);
            MainServer.flats.values()
                .stream()
                .max(Comparator.comparing(Flat::getId))
                .ifPresent(x -> maxId.set(x.getId()));
            long id = maxId.get()+1;

            newFlat.setId(id);
            MainServer.flats.put(key, newFlat);
            String msg = "Элемент под номером "+key.toString();
            if (MainServer.flats.size()==size){
                return msg+" заменён и добавлен";
            } else{
                MainServer.relation.put(key, user);
                return msg+" добавлен";
            }
    }
}
