package csc422steinbachassignment2;

public class Pet {
    private String name;
    private int age;
    
    public Pet() {
        name = "none";
        age = 0;
    }
    
    public Pet(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public int getAge() {
        return age;
    }
    
    @Override
    public String toString() {
        return(name + " " + age);
    }
}
