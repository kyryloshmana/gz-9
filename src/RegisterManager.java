import java.util.HashMap;
import java.util.Map;

public class RegisterManager {
    private final HashMap<Men,Woman> registerManager;
    public RegisterManager(){
        registerManager = new HashMap<>();
    }

    public void createFamily(Men men, Woman woman){
        registerManager.put(men,woman);
    }

    public void removeFamily(Men men, Woman woman){
        registerManager.remove(men,woman);
    }

    public void getFamily(){
        for(Map.Entry<Men,Woman> entry:registerManager.entrySet()){
            Men men = entry.getKey();
            Woman woman = entry.getValue();
            System.out.println("Сім'я: " + men.getFirstName() +" "+ men.getLastName() + " та " + woman.getFirstName() +" "+ woman.getLastName());
        }
    }
}
