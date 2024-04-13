import java.io.*;
import java.util.*;
class AMS 
{
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Integer> rollNumbers = new ArrayList<>();
    static ArrayList<String> statuses = new ArrayList<>();
    static String subjectName, startTime, endTime, date;

    public static void main(String[] args)
    {
        boolean exit = false;
        while (!exit) 
        {
            System.out.println("------------------------------------------------------------");
            System.out.println("| Attendance System Menu:                                 |");
            System.out.println("| 1. Mark Attendance                                      |");
            System.out.println("| 2. View Attendance                                      |");
            System.out.println("| 3. Add Student                                          |");
            System.out.println("| 4. Edit Student                                         |");
            System.out.println("| 5. View All Students                                    |");
            System.out.println("| 6. Overall Attendance                                   |");
            System.out.println("| 7. Absent Days                                          |");
            System.out.println("| 8. Exit                                                 |");
            System.out.println("------------------------------------------------------------");
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            switch (choice)
            {
                case 1:
                    markAttendance();
                    break;
                case 2:
                    viewAttendance();
                    break;
                case 3:
                    addStudent();
                    break;
                case 4:
                    editStudent();
                    break;
                case 5:
                    viewAllStudents();
                    break;
                case 6:
                    overallAttendance();
                    break;
                case 7:
                    absentDays();
                    break;
                case 8:
                    saveAttendanceToFile();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 8.");
            }
        }
        scanner.close();
    }

    public static void markAttendance() 
    {
        System.out.print("| Enter the number of students: |");
        int numStudents = scanner.nextInt();
        System.out.print("| Enter the starting roll number: |");
        int startingRollNo = scanner.nextInt();
        System.out.print("| Enter the subject name: |");
        subjectName = scanner.next();
        System.out.print("| Enter the starting timing of the session: |");
        startTime = scanner.next();
        System.out.print("| Enter the ending timing of the session: |");
        endTime = scanner.next();
        System.out.print("| Enter the date: |");
        date = scanner.next();

        String fileName = "attend_" + date + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) 
        {
            writer.write("| Session Details |\n");
            writer.write("| Subject: " + subjectName + " |\n");
            writer.write("| Timing: " + startTime + " - " + endTime + " |\n");
            writer.write("| Date: " + date + " |\n");

            for (int i = 0; i < numStudents; i++) 
            {
                int rollNo = startingRollNo + i;
                System.out.print("Student " + rollNo + ": Enter 'p' for present or 'a' for absent: ");
                String attendance = scanner.next().toLowerCase();
                if (attendance.equals("p") || attendance.equals("a")) 
                {
                    rollNumbers.add(rollNo);
                    statuses.add(attendance.equals("p") ? "Present" : "Absent");
                    writer.write("| " + date + " | " + startTime + " | " + endTime + " | " + rollNo + " | " + (attendance.equals("p") ? "Present" : "Absent") + " |\n");
                }
                else 
                {
                    System.out.println("Invalid input. Please enter 'p' for present or 'a' for absent.");
                    i--;
                }
            }
            System.out.println("Attendance marked successfully.");
        }
        catch(IOException e) 
        {
            System.out.println("Failed to mark attendance.");
        }
    }

    public static void viewAttendance() 
    {
        System.out.println("| Enter the date: |");
        String datecheck = scanner.next();
        String filecheck = "attend_" + datecheck + ".txt";
        System.out.println("| Enter the student's roll number: |");
        int rollNumber = scanner.nextInt();
        try (BufferedReader reader = new BufferedReader(new FileReader(filecheck))) 
        {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) 
            {
                if (line.contains("| " + rollNumber + " |")) 
                {
                    found = true;
                    String[] parts = line.split("\\|");
                    String result = parts[5].trim();
                    System.out.println("Roll No is " + result);
                    break;
                }
            }
            if (!found) 
            {
                System.out.println("Roll No is Not Present");
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error reading attendance file.");
        }
    }
    public static void addStudent() 
    {
        System.out.println("| Enter the student's roll number: |");
        int rollNumber = scanner.nextInt();
        if (!rollNumbers.contains(rollNumber)) 
        {
            rollNumbers.add(rollNumber);
            statuses.add("Absent");
            System.out.println("| Student with roll number " + rollNumber + " added successfully. |");
        }
        else
        {
            System.out.println("| Student with roll number " + rollNumber + " already exists. |");
        }
    }
    

    public static void editStudent() 
    {
        System.out.print("| Enter the student's roll number: |");
        int rollNumber = scanner.nextInt();
        int index = rollNumbers.indexOf(rollNumber);
        if (index != -1)
        {
            System.out.print("| Enter 'p' for present or 'a' for absent: |");
            String attendance = scanner.next().toLowerCase();
            if (attendance.equals("p") || attendance.equals("a")) 
            {
                statuses.set(index, attendance.equals("p") ? "Present" : "Absent");
                System.out.println("| Attendance updated successfully for Student " + rollNumber + " |");
            }
            else
            {
                System.out.println("| Invalid input. Please enter 'p' for present or 'a' for absent. |");
            }
        }
        else
        {
            System.out.println("| Student with roll number " + rollNumber + " does not exist. |");
        }
    }

    public static void viewAllStudents() 
    {
        System.out.println("|--------------------------------------------|");
        System.out.println("|   View All Students:                       |");
        System.out.println("|--------------------------------------------|");
        System.out.println("| 1. Present Students                         |");
        System.out.println("| 2. Absent Students                          |");
        System.out.println("| 3. All Students                             |");
        System.out.println("|--------------------------------------------|");
        System.out.print("| Enter your choice: ");
        int choice = scanner.nextInt();
        switch (choice) 
        {
            case 1:
                viewPresentStudents();
                break;
            case 2:
                viewAbsentStudents();
                break;
            case 3:
                viewAllStudentsAttendance();
                break;
            default:
                System.out.println("|--------------------------------------------|");
                System.out.println("| Invalid choice! Please enter a number between 1 and 3. |");
                System.out.println("|--------------------------------------------|");
        }
    }

    public static void viewPresentStudents()
    {
        System.out.println("|--------------------------------------------|");
        System.out.println("|   Present Students:                        |");
        System.out.println("|--------------------------------------------|");
        for (int i = 0; i < rollNumbers.size(); i++)
        {
            if (statuses.get(i).equals("Present")) 
            {
                System.out.println("| Roll Number: " + rollNumbers.get(i) + " |");
            }
        }
    }

    public static void viewAbsentStudents() 
    {
        System.out.println("|--------------------------------------------|");
        System.out.println("|    Absent Students                         |");
        System.out.println("|--------------------------------------------|");
        for (int i = 0; i < rollNumbers.size(); i++) 
        {
            if (statuses.get(i).equals("Absent")) 
            {
                System.out.println("| Roll Number: " + rollNumbers.get(i) + " |");
            }
        }
    }

    public static void viewAllStudentsAttendance() 
    {
        System.out.println("|--------------------------------------------|");
        System.out.println("|   All Students Attendance:                 |");
        System.out.println("|--------------------------------------------|");
        for (int i = 0; i < rollNumbers.size(); i++) 
        {
            System.out.println("| Roll Number: " + rollNumbers.get(i) + " | Status: " + statuses.get(i) + " |");
        }
    }

    public static void overallAttendance() 
    {
        int presentCount = 0;
        for (String status : statuses) 
        {
            if (status.equals("Present"))
            {
                presentCount++;
            }
        }
        double percentage = (presentCount * 100.0)/rollNumbers.size();
        System.out.println("|--------------------------------------------|");
        System.out.println("| Overall Attendance Percentage: " + percentage + "% |");
        System.out.println("|--------------------------------------------|");
    }

    public static void absentDays() 
    {
        System.out.println("|--------------------------------------------|");
        System.out.println("| Enter the student's roll number:           |");
        int rollNumber = scanner.nextInt();
        int index = rollNumbers.indexOf(rollNumber);
        if (index != -1)
        {
            int absentDays = statuses.get(index).equals("Absent") ? 1 : 0;
            System.out.println("| Total Absent Days for Student " + rollNumber + ": " + absentDays + " |");
        }
        else
        {
            System.out.println("| Student with roll number " + rollNumber + " does not exist. |");
        }
    }

    public static void saveAttendanceToFile() 
    {
        try 
        {
            System.out.print("| Enter the date: |");
            date = scanner.next();
            String fileName = "attend_" + date + ".txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) 
            {
                writer.write("| Session Details |\n");
                writer.write("| Subject: " + subjectName + " |\n");
                writer.write("| Timing: " + startTime + " - " + endTime + " |\n");
                writer.write("| Date: " + date + " |\n");

                for (int i = 0; i < rollNumbers.size(); i++) 
                {
                    writer.write("| " + date + " | " + startTime + " | " + endTime + " | " + rollNumbers.get(i) + " | " + statuses.get(i) + " |\n");
                }
                System.out.println("| Attendance records saved successfully. |");
            }
        }
        catch (IOException e) 
        {
            System.out.println("| Failed to save attendance records. |");
        }
    }
}