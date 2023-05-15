import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static boolean check = false;

    static MenManager manManager = new MenManager();
    static WomanManager womanManager = new WomanManager();
    static RegisterManager registerManager = new RegisterManager();


    public static void main(String[] args) {
        Scanner data = new Scanner(System.in);

        String menu = """
                Меню
                1. Додати чоловіка
                2. Додати жінку
                3. Створити пару
                4. Розійтись
                5. Інформація
                6. Показати пари
                """;

        System.out.println(menu);
        System.out.println("Оберіть номер:");

        while (!check){
            String selectNumber = data.nextLine();
            switch (selectNumber){
                case "1" -> createMan();
                case "2" -> createWoman();
                case "3" -> registerPartnership();
                case "4" -> deregisterPartnership();
                case "5" -> showAll();
                case "6" -> registerManager.getFamily();
            }
            System.out.println(menu);
        }
    }
    static void showAll(){
       HashMap<Integer, Men> mens = manManager.getMens();
        System.out.println("Чоловіки не одружені ");
        for(Men men : mens.values()){
             System.out.println("ІД: " +men.getId() + " Особа: " + men.getFirstName() + " " + men.getLastName() + " Вік: " + men.getAge() + " Пенсіонер: " + men.isRetired(men.getAge()));
        }

        HashMap<Integer, Men> mensMerited = manManager.getMeritedMens();
        System.out.println("Чоловіки одружені ");
        for(Men men : mensMerited.values()){
            System.out.println("ІД: " +men.getId() + " Особа: " + men.getFirstName() + " " + men.getLastName() + " Вік: " + men.getAge() + " Пенсіонер: " + men.isRetired(men.getAge()));
        }

        HashMap<Integer, Woman> women = womanManager.getWomen();
        System.out.println("Жінки не одружені");
        for(Woman woman : women.values()){
        System.out.println("ІД: " +woman.getId() + " Особа: " + woman.getFirstName() + " " + woman.getLastName() + " Вік: " + woman.getAge() + " Пенсіонер: " + woman.isRetired(woman.getAge()));
        }

        HashMap<Integer, Woman> womenMerited = womanManager.getMeritedWomen();
        System.out.println("Жінки одружені");
        for(Woman woman : womenMerited.values()){
            System.out.println("ІД: " +woman.getId() + " Особа: " + woman.getFirstName() + " " + woman.getLastName() + " Вік: " + woman.getAge() + " Пенсіонер: " + woman.isRetired(woman.getAge()));
        }
}
    static String enteredFirstName(){
        Scanner firstName = new Scanner(System.in);
        System.out.println("Введіть ім'я");
        return firstName.nextLine();
    }
    static String enteredLastName(){
        Scanner lastName = new Scanner(System.in);
        System.out.println("Введіть прізвище");
        return lastName.nextLine();
    }
    static Integer enteredAgeName(){
        Scanner lastName = new Scanner(System.in);
        System.out.println("Введіть вік");
        return lastName.nextInt();
    }
    static void createMan(){
        UniqueId uniqueId = new UniqueId();
        String firstName = enteredFirstName();
        String lastName = enteredLastName();
        Integer age = enteredAgeName();


        Men men = new Men(uniqueId, firstName, lastName, age);
        manManager.addMan(men);
    }
    static void createWoman(){
            UniqueId uniqueId = new UniqueId();
            String firstName = enteredFirstName();
            String lastName = enteredLastName();
            Integer age = enteredAgeName();


            Woman woman = new Woman(uniqueId, firstName, lastName, age);
            womanManager.addWoman(woman);
        }
    static void registerPartnership(){
        showAll();
        Scanner menId = new Scanner(System.in);
        System.out.println("Введіть Ід чоловіка");
        int enteredManId = menId.nextInt();

        Scanner womanId = new Scanner(System.in);
        System.out.println("Введіть Ід жінки");
        int enteredWomanId = womanId.nextInt();

        registerManager.createFamily(manManager.getMan(enteredManId), womanManager.getWoman(enteredWomanId)); // створення сім'ї
        String lastName = manManager.getMan(enteredManId).getLastName(); // отримали прізвище чоловіка
        womanManager.addLastName(enteredWomanId, womanManager.getWoman(enteredWomanId).getLastName());// зберігаємо прізвище жінки до зміни
        womanManager.getWoman(enteredWomanId).setLastName(lastName);// присвоєння нового прізвища жінці

        Men meritedMen = manManager.getMan(enteredManId);// чоловіка якого одружили
        Woman meritedWoman = womanManager.getWoman(enteredWomanId); // жінку яку видали заміж

        manManager.addManToMeritedMap(meritedMen);//додати в мапу одруженого чоловіка
        womanManager.addWomanToMeritedMap(meritedWoman);//додати в мапу одружену жінку

        manManager.removeMan(enteredManId);//видалити чоловіка з мапи неодружений
        womanManager.removeWoman(enteredWomanId);//видалити жінку з мапи неодружена

        System.out.println("Вітаю");

    }
    static void deregisterPartnership(){
        showAll();
        Scanner menId = new Scanner(System.in);
        System.out.println("Введіть Ід чоловіка");
        int enteredManId = menId.nextInt();
        Scanner womanId = new Scanner(System.in);
        System.out.println("Введіть Ід жінки");
        int enteredWomanId = womanId.nextInt();

        Men divorcedMen = manManager.getMeritedMan(enteredManId);// чоловік який розвівся
        Woman divorcedWoman = womanManager.getMeritedWoman(enteredWomanId); // жінка яка розвелась

        registerManager.removeFamily(divorcedMen,divorcedWoman);//розлучення

        System.out.println("Чи залишає жінка собі прізвище? Введіть 'Ні' якщо повертає дівочу");
        Scanner text = new Scanner(System.in);
        String enteredText = text.nextLine();
        if(enteredText.equals("Ні")){
            womanManager.getMeritedWoman(enteredWomanId).setLastName(womanManager.getLastName(enteredWomanId));//присвоєння старого прізвища після розлучення
            womanManager.deleteLastName(enteredWomanId);//видалення з мапи присвоєного прізвища
        }
        else System.out.println("Прізвище чоловіка залишається");


        manManager.addMan(divorcedMen);//додати чоловіка до мапи неодружений
        womanManager.addWoman(divorcedWoman);//додати жінку до мапи неодружена
        manManager.removeManFromMeritedMap(enteredManId);//видалити чоловіка з мапи одружених
        womanManager.removeWomanFromMeritedMap(enteredWomanId);//видалити жінку з мапи заміжніх



        }
}
