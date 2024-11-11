/* 
Ty Steinbach
11/10/2024
CSC 422
Assignment 2
Extend previous project, add File I/O, and handle errors provided
*/
package csc422steinbachassignment2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CSC422SteinbachAssignment2 {
    
    //Reads from the specified file name, parses into objects, prints objects, and inserts into AVL
    private static ArrayList<Pet> loadPets(String fileName) throws IOException {
        ArrayList<Pet> list = new ArrayList<>();

        System.out.println("Opening file " + fileName);
        
        //Trying to open file and load data
        try (Scanner inFS = new Scanner(new FileInputStream(fileName))) {
            //Initializing file to read
            Pet pet = new Pet();
            //Parses String into ProcessInfo, prints it, then adds it to the AVL
            while(inFS.hasNext()) {
                pet = parsePetInfo(inFS.nextLine());
                System.out.println("Adding " + pet);
                list.add(pet);
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot find " + fileName);
        }
        return(list);
    }
    
    private static Pet parsePetInfo(String line) {
        String[] info = line.split(" ");
        Pet pet = new Pet();
        try{
            pet.setName(info[0]);
            pet.setAge(Integer.parseInt(info[1]));
        }
        catch(NumberFormatException e) {
            System.out.println(e);
        }
        return(pet);
    }
    
    //Saves the data onto a file
    static private void savePets(String fileName, ArrayList<Pet> pets) {
        try(PrintWriter outFS = new PrintWriter(new FileOutputStream(fileName))){
            pets.forEach( (n) -> {outFS.println(n.getName() + " " + n.getAge());});
            outFS.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Cannot find " + fileName);
        }
    }

    static void addPets(ArrayList<Pet> pets, Scanner scnr) {
        scnr.nextLine();
        String input;
        String name;
        int age;
        System.out.println("Enter \"done\" when finished");
        while(true) {
            try {
                Pet pet = new Pet();
                System.out.println("add pet (name, age):");

                input = scnr.nextLine();
                String[] inputArray = input.split(" ");
                name = inputArray[0];
                if (!name.equalsIgnoreCase("done")) {
                    age = Integer.parseInt(inputArray[1]);
                    pet.setName(name);
                    pet.setAge(age);
                    pets.add(pet);
                }
                else {
                    break;
                }
            }
            catch(NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println(e);
            }
           
        }
    }
    
    static void searchName(ArrayList<Pet> pets, Scanner scnr) {
        ArrayList<Pet> petsNames = new ArrayList<>();
        String input;
        System.out.println("Enter name to search");
        input = scnr.next();
        Pet current;
        for(int i = 0; i < pets.size(); i++) {
            current = pets.get(i);
            if (current.getName().equals(input)) {
                petsNames.add(current);
            }
        }
        printPets(petsNames);
    }
    
    static void searchAge(ArrayList<Pet> pets, Scanner scnr) {
        try {
            ArrayList<Pet> petsAges = new ArrayList<>();
            int input;
            System.out.println("Enter age to search");
            input = scnr.nextInt();
            Pet current;
            for(int i = 0; i < pets.size(); i++) {
                current = pets.get(i);
                if (current.getAge() == input) {
                    petsAges.add(current);
                }
            }
            printPets(petsAges);
        }
        catch(InputMismatchException e) {
            System.out.println(e);
            scnr.next();
        }
    }
    
    static void updatePet(ArrayList<Pet> pets, Scanner scnr) {
        try {
            Pet petUpdate;
            String input;
            String name;
            int age;
            
            printPets(pets);
            System.out.println("Enter the pet ID you want to update: ");
            petUpdate = pets.get(scnr.nextInt());
            System.out.println("Enter new name and new age: ");
            
            scnr.nextLine();
            input = scnr.nextLine();
            String[] inputArray = input.split(" ");
            name = inputArray[0];
            age = Integer.parseInt(inputArray[1]);
            
            System.out.print(petUpdate + " changed to ");
            petUpdate.setName(name);
            petUpdate.setAge(age);
            System.out.println(petUpdate);
        }
        catch(InputMismatchException e) {
            System.out.println(e);
            scnr.next();
        }
        catch(IndexOutOfBoundsException | NumberFormatException e) {
            System.out.println(e);
        }
    }
    
    static void removePet(ArrayList<Pet> pets, Scanner scnr) {
        try {
            Pet petRemove;
            int input;
            printPets(pets);
            System.out.println("Enter the pet ID you want to remove: ");
            input = scnr.nextInt();
            petRemove = pets.get(input);
            pets.remove(input);
            System.out.println(petRemove + " is removed");
        }
        catch(InputMismatchException e) {
            System.out.println(e);
            scnr.next();
        }
        catch(IndexOutOfBoundsException e) {
            System.out.println(e);
        }
    }
    
    static void printPets(ArrayList<Pet> pets) {
        String line = "+----------------------+";
        System.out.println(line + "\n| ID | NAME      | AGE |\n" + line);
        int i = 0;
        for (i = 0; i < pets.size(); i++) {
            System.out.printf("|%3d |%10s |%4d |\n", i, pets.get(i).getName(), pets.get(i).getAge());
        }
        System.out.println(line + "\n" + i + " rows in set.");
    }
    
    public static void main(String[] args) throws IOException {
        String fileName = "src/Files/pets.txt";
        ArrayList<Pet> pets = loadPets(fileName);
        Scanner scnr = new Scanner(System.in);
        int input = 0;
        while (input != 7) {
            System.out.println("""
                           Pet Database Program
                           1) View all pets
                           2) Add more Pet
                           3) Update existing pet
                           4) Remove existing pet
                           5) Search pets by name
                           6) Search pets by age
                           7) Exit
                           """);
            try {
                input = scnr.nextInt();
                switch(input) {
                    case(1) -> printPets(pets);
                    case(2) -> addPets(pets, scnr);
                    case(3) -> updatePet(pets, scnr);
                    case(4) -> removePet(pets, scnr);
                    case(5) -> searchName(pets, scnr);
                    case(6) -> searchAge(pets, scnr);
                    case(7) -> savePets(fileName, pets);
                }
            }
            catch(InputMismatchException e) {
                System.out.println(e);
                scnr.nextInt();
            }
        }
        
    }
    
}