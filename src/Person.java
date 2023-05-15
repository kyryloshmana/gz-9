public abstract class Person {
    private final Integer id;
    private  final String firstName;
    private  String lastName;
    private  final int age;

    public Person(String firstName, String lastName, int age) {
        this.id= UniqueId.getUniqueId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;

    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public abstract Boolean isRetired(int age);

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
