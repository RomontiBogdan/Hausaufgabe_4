package Tests;

import Entities.Student;
import Repository.StudentRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentRepoTest {

    @org.junit.jupiter.api.Test
    void create() {
        Student student1 = new Student("Ion", "Creanga", 1, 12, new ArrayList<>());
        Student student2 = new Student("Mihai", "Eminescu", 2, 2, new ArrayList<>());
        Student student3 = new Student("Luca", "Caragiale", 2, 5, new ArrayList<>());
        StudentRepo studentRepo = new StudentRepo();

        try {
            studentRepo.create(student1);
        }catch (IllegalArgumentException e){
            assert false;
        }

        try {
            studentRepo.create(student2);
        }catch (IllegalArgumentException e){
            assert false;
        }

        try {
            studentRepo.create(student3);
            assert false;
        }catch (IllegalArgumentException e){
            assert true;
        }
    }

    @org.junit.jupiter.api.Test
    void update() {
        Student student1 = new Student("Ion", "Creanga", 1, 12, new ArrayList<>());
        Student student2 = new Student("Mihai", "Eminescu", 2, 2, new ArrayList<>());
        Student student3 = new Student("Luca", "Caragiale", 3, 5, new ArrayList<>());
        StudentRepo studentRepo = new StudentRepo();
        studentRepo.create(student1);
        studentRepo.create(student2);

        student1.setTotalCredits(30);
        try {
            studentRepo.update(student1);
            if(studentRepo.getById(student1.getStudentId()).getTotalCredits() != 30)
                assert false;
        }catch (Exception e){
            assert false;
        }

        student2.setLastName("Blaga");

        try {
            studentRepo.update(student2);
            if(studentRepo.getById(student2.getStudentId()).getLastName() == "Blaga")
                assert true;
            else
                assert false;
        }catch (Exception e){
            assert false;
        }

        try {
            studentRepo.update(student3);
            assert false;
        }catch (Exception e){
            assert true;
        }
    }

    @org.junit.jupiter.api.Test
    void filterByCredits() {
        Student student1 = new Student("Ion", "Creanga", 1, 20, new ArrayList<>());
        Student student2 = new Student("Mihai", "Eminescu", 2, 12, new ArrayList<>());
        Student student3 = new Student("Luca", "Caragiale", 3, 1, new ArrayList<>());
        Student student4 = new Student("Lucian", "Blaga", 4, 25, new ArrayList<>());
        Student student5 = new Student("Tudor", "Arghezi", 5, 9, new ArrayList<>());
        StudentRepo studentRepo = new StudentRepo();
        List<Student> studentList;
        studentRepo.create(student1);
        studentRepo.create(student2);
        studentRepo.create(student3);
        studentRepo.create(student4);
        studentRepo.create(student5);

        for (int i = 0; i <= 29; i++)
            for (int j = i + 1; j <= 30; j++){
                studentList = studentRepo.filterByCredits(i, j);
                for (Student student : studentList)
                    if(student.getTotalCredits() < i || student.getTotalCredits() > j)
                        assert false;
            }
    }

    @org.junit.jupiter.api.Test
    void sortList() {
        Student student1 = new Student("Ion", "Creanga", 1, 20, new ArrayList<>());
        Student student2 = new Student("Mihai", "Eminescu", 2, 12, new ArrayList<>());
        Student student3 = new Student("Luca", "Caragiale", 3, 1, new ArrayList<>());
        Student student4 = new Student("Lucian", "Blaga", 4, 25, new ArrayList<>());
        Student student5 = new Student("Tudor", "Arghezi", 5, 9, new ArrayList<>());

        StudentRepo studentRepo = new StudentRepo();
        studentRepo.create(student1);
        studentRepo.create(student2);
        studentRepo.create(student3);
        studentRepo.create(student4);
        studentRepo.create(student5);
        studentRepo.sortList();
        List<Student> studentList = studentRepo.getAll();


        for(int i = 0; i < studentList.size() - 1; i++)
        {
            if(studentList.get(i+1).getTotalCredits() < studentList.get(i).getTotalCredits())
                assert false;
        }
    }
}