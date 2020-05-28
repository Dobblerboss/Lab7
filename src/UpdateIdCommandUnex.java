public class UpdateIdCommandUnex extends Command {
    protected long id;
    protected Flat newFlat;
    @Override
    public boolean checkCom(String[] s) {
        try {
            id = Long.parseLong(s[0]);
        }catch(NumberFormatException e){
            System.out.println("Не пройдена вадидация. Введите число (id) на второе место");
            return false;
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Не пройдена вадидация. Нужно указать один параметр (id) или два параметра(id, json представление элемента Flat).");
            return false;
        }
        if(s.length==2){
            try {
                JsonDecoder jsonDecoder = new JsonDecoder(s[1]);
                newFlat = jsonDecoder.getDecodeValue();
                newFlat.setId(id);
                return true;
            } catch (NullPointerException e){
                System.out.println("Не пройдена вадидация. Какое-то значение пустое");
                return false;
            }
        } else if(s.length == 1){
            CreateFlat createFlat = new CreateFlat();
            newFlat = createFlat.getNewFlat();
            newFlat.setId(id);
            return true;
        }
        System.out.println("Не пройдена вадидация. Нужно указать один параметр (id) или два параметра(id, json представление элемента Flat).");
        return false;
    }
}
