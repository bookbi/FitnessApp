import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// FitnessClass class
class FitnessClass {
    private String name;
    private String instructor;
    private int capacity;
    private int enrolled;

    public FitnessClass(String name, String instructor, int capacity) {
        this.name = name;
        this.instructor = instructor;
        this.capacity = capacity;
        this.enrolled = 0;
    }

    public boolean enroll() {
        if (enrolled < capacity) {
            enrolled++;
            return true;
        }
        return false;
    }

    public String getInfo() {
        return name + " by " + instructor + " (Enrolled: " + enrolled + "/" + capacity + ")";
    }

    public String getName() {
        return name;
    }
}

// Member class
class Member {
    protected String name;
    protected int memberId;

    public Member(String name, int memberId) {
        this.name = name;
        this.memberId = memberId;
    }

    public void bookClass(FitnessClass fitnessClass) {
        if (fitnessClass.enroll()) {
            System.out.println(name + " successfully booked " + fitnessClass.getName() + " class.");
        } else {
            System.out.println(fitnessClass.getName() + " class is full.");
        }
    }

    public void viewProfile() {
        System.out.println("Member: " + name + " (ID: " + memberId + ")");
    }
}

// PremiumMember class
class PremiumMember extends Member {
    private boolean freePersonalTrainer;

    public PremiumMember(String name, int memberId, boolean freePersonalTrainer) {
        super(name, memberId);
        this.freePersonalTrainer = freePersonalTrainer;
    }

    @Override
    public void viewProfile() {
        System.out.println("Premium Member: " + name + " (ID: " + memberId + ")");
        if (freePersonalTrainer) {
            System.out.println("Eligible for a free personal trainer session!");
        }
    }
}

// FitnessCenter class
class FitnessCenter {
    private List<FitnessClass> classes = new ArrayList<>();

    public void addClass(FitnessClass fitnessClass) {
        classes.add(fitnessClass);
    }

    public void listClasses() {
        int index = 1;
        for (FitnessClass fc : classes) {
            System.out.println(index + ". " + fc.getInfo());
            index++;
        }
    }

    public FitnessClass getClassByIndex(int index) {
        if (index >= 1 && index <= classes.size()) {
            return classes.get(index - 1);
        }
        return null;
    }
}

// Main Class
public class FitnessApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        FitnessCenter center = new FitnessCenter();
        center.addClass(new FitnessClass("Yoga", "Alice", 5));
        center.addClass(new FitnessClass("HIIT", "Bob", 3));
        center.addClass(new FitnessClass("Pilates", "Carol", 4));

        System.out.println("Welcome to the Fitness Center!");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Are you a premium member? (yes/no): ");
        String premiumAnswer = scanner.nextLine();
        Member member;
        if (premiumAnswer.equalsIgnoreCase("yes")) {
            member = new PremiumMember(name, (int)(Math.random() * 1000), true);
        } else {
            member = new Member(name, (int)(Math.random() * 1000));
        }

        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. View Profile");
            System.out.println("2. View Available Classes");
            System.out.println("3. Book a Class");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

            switch (choice) {
                case 1:
                    member.viewProfile();
                    break;
                case 2:
                    center.listClasses();
                    break;
                case 3:
                    center.listClasses();
                    System.out.print("Enter the number of the class to book: ");
                    int classChoice = scanner.nextInt();
                    FitnessClass selectedClass = center.getClassByIndex(classChoice);
                    if (selectedClass != null) {
                        member.bookClass(selectedClass);
                    } else {
                        System.out.println("Invalid selection.");
                    }
                    break;
                case 4:
                    keepRunning = false;
                    System.out.println("Thank you for visiting!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }
}

