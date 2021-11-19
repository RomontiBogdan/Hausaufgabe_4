import Entities.Course;
import Entities.Student;
import Entities.Teacher;
import Repository.*;

import java.util.ArrayList;

public class main {
    public static void main(String[] args){
        StudentRepo studentRepo = new StudentRepo();
        CourseRepo courseRepo = new CourseRepo();
        TeacherRepo teacherRepo = new TeacherRepo();
        RegistrationSystem registrationSystem = new RegistrationSystem(courseRepo, studentRepo, teacherRepo);

        Student student1 = new Student("Ion", "Creanga", 1, 12, new ArrayList<>());
        Student student2 = new Student("Mihai", "Eminescu", 2, 2, new ArrayList<>());

        studentRepo.create(student1);
        studentRepo.create(student2);

        Course course1 = new Course(1, "Curs1", new Teacher(), new ArrayList<>(), 6, 4);

        courseRepo.create(course1);

        for(Student student : studentRepo.getAll())
        {
            System.out.print(student.getFirstName() + " " + student.getLastName() + " " + student.getStudentId() + " " +  student.getTotalCredits() + '\n');
        }

        registrationSystem.register(course1, student1);

        for(Course course : courseRepo.getAll())
        {
            System.out.print(course.getName() + " " + course.getStudentsEnrolled().get(0).getFirstName() + '\n');
        }


        FileRepository fileRepository = new FileRepository(courseRepo, studentRepo, teacherRepo);

        fileRepository.readStudentsFromFile("StudentsOutput.txt");

        for(Student student : studentRepo.getAll())
        {
            System.out.print(student.getFirstName() + " " + student.getLastName() + " " + student.getStudentId() + " " +  student.getTotalCredits() + '\n');
        }

        fileRepository.writeStudentsToFile("StudentsOutput.txt");
    }
}
