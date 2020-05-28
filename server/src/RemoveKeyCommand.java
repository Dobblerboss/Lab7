import java.util.LinkedHashMap;

public class RemoveKeyCommand extends RemoveKeyCommandUnex implements ExecuteCommand {
    public RemoveKeyCommand(RemoveKeyCommandUnex e){
        this.key = e.key;
        this.user = e.user;
    }
    @Override
    public String execute() {
        int lengthPrev = MainServer.flats.size();
        LinkedHashMap<Integer,Flat> newFlat = new LinkedHashMap<>();
        try {
            if(!MainServer.relation.get(key).equals(user)){
                return "Элемент с таким ключом уже существует и он не принадлежит вам";
            }
        } catch (NullPointerException e){
            // дальше
        }

        MainServer.flats.entrySet()
                .stream()
                .filter(x -> x.getKey()!=key)
                .forEach((k)->newFlat.put(k.getKey(),k.getValue()));

        if(newFlat.size()==lengthPrev){
            return "Не найдено элемента с таким ключом";
        }else {
            MainServer.relation.remove(key);
            MainServer.flats = newFlat;

            return "Элемент под номером "+key+" удалён";
        }

    }
}
