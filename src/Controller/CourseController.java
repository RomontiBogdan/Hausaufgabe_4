package Controller;

import Entities.Course;
import Repository.CourseRepo;

import java.util.List;

public class CourseController {
    private CourseRepo repo;

    public void addCourse(Course course){
        this.repo.create(course);
    }

    public void deleteCourse(Course course){
        this.repo.delete(course);
    }

    public void updateCourse(Course course){
        this.repo.update(course);
    }
}