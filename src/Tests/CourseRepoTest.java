package Tests;

import Entities.Course;
import Entities.Teacher;
import Repository.CourseRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseRepoTest {

    @org.junit.jupiter.api.Test
        void create() {
        Course course1 = new Course(1, "MAP", new Teacher(), new ArrayList<>(), 7, 30);
        Course course2 = new Course(2, "LP", new Teacher(), new ArrayList<>(), 5, 20);
        Course course3 = new Course(1, "DB", new Teacher(), new ArrayList<>(), 6, 25);
        CourseRepo courseRepo = new CourseRepo();

        try {
            courseRepo.create(course1);
        }catch (IllegalArgumentException e){
            assert false;
        }

        try {
            courseRepo.create(course2);
        }catch (IllegalArgumentException e){
            assert false;
        }

        try {
            courseRepo.create(course3);
            assert false;
        }catch (IllegalArgumentException e){
            assert true;
        }


    }

    @org.junit.jupiter.api.Test
    void update() {
        Course course1 = new Course(1, "MAP", new Teacher(), new ArrayList<>(), 7, 30);
        Course course2 = new Course(2, "LP", new Teacher(), new ArrayList<>(), 5, 20);
        Course course3 = new Course(3, "DB", new Teacher(), new ArrayList<>(), 6, 25);
        CourseRepo courseRepo = new CourseRepo();
        courseRepo.create(course1);
        courseRepo.create(course2);

        course1.setMaxStudends(15);
        try {
            courseRepo.update(course1);
            if(courseRepo.getById(course1.getCourseId()).getMaxStudends() != 15)
                assert false;
        }catch (Exception e){
            assert false;
        }

        course2.setName("LP+");

        try {
            courseRepo.update(course2);
            if(courseRepo.getById(course2.getCourseId()).getName() == "LP+")
                assert true;
            else
                assert false;
        }catch (Exception e){
            assert false;
        }

        try {
            courseRepo.update(course3);
            assert false;
        }catch (Exception e){
            assert true;
        }

    }

    @org.junit.jupiter.api.Test
    void filterByCredits() {
        Course course1 = new Course(1, "MAP", new Teacher(), new ArrayList<>(), 6, 30);
        Course course2 = new Course(2, "LP", new Teacher(), new ArrayList<>(), 12, 20);
        Course course3 = new Course(3, "DB", new Teacher(), new ArrayList<>(), 1, 25);
        Course course4 = new Course(4, "DB", new Teacher(), new ArrayList<>(), 2, 23);
        Course course5 = new Course(5, "DB", new Teacher(), new ArrayList<>(), 8, 21);
        CourseRepo courseRepo = new CourseRepo();
        List<Course> courseList;
        courseRepo.create(course1);
        courseRepo.create(course2);
        courseRepo.create(course3);
        courseRepo.create(course4);
        courseRepo.create(course5);

        for (int i = 0; i <= 29; i++)
            for (int j = i + 1; j <= 30; j++){
                courseList = courseRepo.filterByCredits(i, j);
                for (Course course : courseList)
                    if(course.getCredits() < i || course.getCredits() > j)
                        assert false;
            }
    }

    @org.junit.jupiter.api.Test
    void sortList() {
        Course course1 = new Course(1, "MAP", new Teacher(), new ArrayList<>(), 6, 30);
        Course course2 = new Course(2, "LP", new Teacher(), new ArrayList<>(), 12, 20);
        Course course3 = new Course(3, "DB", new Teacher(), new ArrayList<>(), 1, 25);
        Course course4 = new Course(4, "DB", new Teacher(), new ArrayList<>(), 2, 23);
        Course course5 = new Course(5, "DB", new Teacher(), new ArrayList<>(), 8, 21);

        CourseRepo courseRepo = new CourseRepo();
        courseRepo.create(course1);
        courseRepo.create(course2);
        courseRepo.create(course3);
        courseRepo.create(course4);
        courseRepo.create(course5);
        courseRepo.sortList();
        List<Course> courseList = courseRepo.getAll();


        for(int i = 0; i < courseList.size() - 1; i++)
        {
            if(courseList.get(i+1).getCredits() < courseList.get(i).getCredits())
                assert false;
        }
    }
}