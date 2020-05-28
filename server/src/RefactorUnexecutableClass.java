import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

public class RefactorUnexecutableClass<T extends Command> implements Runnable {
    ByteBuffer intBuf;
    ByteBuffer buffer;
    SocketAddress remoteAdd;
    T thisCommandF;
    RefactorUnexecutableClass(SocketAddress remoteAdd, T thisCommandF, ByteBuffer intBuf, ByteBuffer buffer){
        this.remoteAdd = remoteAdd;
        this.thisCommandF = thisCommandF;
        this.buffer = buffer;
        this.intBuf = intBuf;
    }

    public String executeCommand(T command) throws NoSuchAlgorithmException {
        String spilt = command.getClass().toString().split(" ")[1];
        switch (spilt){
            case "PasswordSendUnex":
                PasswordSendCommand passwordSendCommand = new PasswordSendCommand((PasswordSendUnex) command);
                MainServer.LOGGER.log(Level.INFO, "Конец авторизации юзера " + passwordSendCommand.user);
                return passwordSendCommand.execute();
            case "LoginSendCommandUnex":
                LoginSendCommand loginSendCommand = new LoginSendCommand((LoginSendCommandUnex) command);
                MainServer.LOGGER.log(Level.INFO, "Авторизация юзера " + loginSendCommand.user);
                return loginSendCommand.execute();
            case "ClearCommandUnex"://
                ClearCommand clearCommand = new ClearCommand((ClearCommandUnex) command);
                MainServer.LOGGER.log(Level.INFO, "Выполнение команды clear");
                return clearCommand.execute();
            case "ShowCommandUnex"://
                ShowCommand showCommand = new ShowCommand();
                MainServer.LOGGER.log(Level.INFO, "Выполнение команды show");
                return showCommand.execute();
            case "HelpCommandUnex"://
                HelpCommand helpCommand = new HelpCommand();
                MainServer.LOGGER.log(Level.INFO, "Выполнение команды help");
                return helpCommand.execute();
            case "InfoCommandUnex"://
                InfoCommand infoCommand = new InfoCommand();
                MainServer.LOGGER.log(Level.INFO, "Выполнение команды info");
                return infoCommand.execute();
            case "PrintAscendingCommandUnex"://
                PrintAscendingCommand printAscendingCommand = new PrintAscendingCommand();
                MainServer.LOGGER.log(Level.INFO, "Выполнение команды print_ascending");
                return printAscendingCommand.execute();
            case "MaxTransportCommandUnex"://
                MaxTransportCommand maxTransportCommand = new MaxTransportCommand();
                MainServer.LOGGER.log(Level.INFO, "Выполнение команды max_by_transport");
                return maxTransportCommand.execute();
            case "SumOfNumberOfRoomsCommandUnex"://
                SumOfNumberOfRoomsCommand sumOfNumberOfRoomsCommand = new SumOfNumberOfRoomsCommand();
                MainServer.LOGGER.log(Level.INFO, "Выполнение команды sum_of_number_of_rooms");
                return sumOfNumberOfRoomsCommand.execute();
            case "RemoveKeyCommandUnex"://
                RemoveKeyCommand removeKeyCommand = new RemoveKeyCommand((RemoveKeyCommandUnex) command);
                MainServer.LOGGER.log(Level.INFO, "Выполнение команды remove_key");
                return removeKeyCommand.execute();
            case "InsertElementCommandUnex"://
                InsertElementCommand insertElementCommand = new InsertElementCommand((InsertElementCommandUnex)command);
                MainServer.LOGGER.log(Level.INFO, "Выполнение команды insert");
                return insertElementCommand.execute();
            case "UpdateIdCommandUnex"://
                UpdateIdCommand updateIdCommand = new UpdateIdCommand((UpdateIdCommandUnex)command);
                MainServer.LOGGER.log(Level.INFO, "Выполнение команды update_id");
                return updateIdCommand.execute();
            case "RemoveLowerCommandUnex"://
                RemoveLowerCommand removeLowerCommand = new RemoveLowerCommand((RemoveLowerCommandUnex)command);
                MainServer.LOGGER.log(Level.INFO, "Выполнение команды remove_lower");
                return removeLowerCommand.execute();
            case "ReplaceIfLowerCommandUnex":
                ReplaceIfLowerCommand replaceIfLowerCommand = new ReplaceIfLowerCommand((ReplaceIfLowerCommandUnex)command);
                MainServer.LOGGER.log(Level.INFO, "Выполнение команды replace_if_lower");
                return replaceIfLowerCommand.execute();
            case "ReplaceIfGreaterCommandUnex":
                ReplaceIfGreaterCommand replaceIfGreaterCommand = new ReplaceIfGreaterCommand((ReplaceIfGreaterCommandUnex)command);
                MainServer.LOGGER.log(Level.INFO, "Выполнение команды replace_if_greater");
                return replaceIfGreaterCommand.execute();
            default:
                MainServer.LOGGER.log(Level.WARNING, "Всё плохо");
                return "Error";
        }
    }

    @Override
    public void run() {
        String msg = null;
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            msg = executeCommand(thisCommandF);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        ExecutorService thread = Executors.newSingleThreadExecutor();
        SendMessage sendMessage = new SendMessage(remoteAdd, msg, intBuf, buffer);
        thread.submit(sendMessage);
    }
}
