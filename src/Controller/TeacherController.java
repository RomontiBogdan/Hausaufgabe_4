package Controller;

import Entities.Teacher;
import Repository.TeacherRepo;

import java.util.List;

public class TeacherController {
    private TeacherRepo repo;

    public void addTeacher(Teacher teacher){
        this.repo.create(teacher);
    }

    public void deleteTeacher(Teacher teacher){
        this.repo.delete(teacher);
    }

    public void updateTeacher(Teacher teacher){
        this.repo.update(teacher);
    }

}