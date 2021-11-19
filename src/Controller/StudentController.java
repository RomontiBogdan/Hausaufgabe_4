package Controller;

import Entities.Student;
import Repository.StudentRepo;

import java.util.List;

public class StudentController {
    private StudentRepo repo;

    public void addStudent(Student student){
        this.repo.create(student);
    }

    public void deleteStudent(Student student){
        this.repo.delete(student);
    }

    public void updateStudent(Student student){
        this.repo.update(student);
    }

}