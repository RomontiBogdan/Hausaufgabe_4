package Tests;

import Entities.Course;
import Entities.Teacher;
import Repository.TeacherRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherRepoTest {

    @org.junit.jupiter.api.Test
    void create() {
        Teacher teacher1 = new Teacher("Ion", "Creanga", 1, new ArrayList<>());
        Teacher teacher2 = new Teacher("Mihai", "Eminescu", 2, new ArrayList<>());
        Teacher teacher3 = new Teacher("Luca", "Caragiale", 1, new ArrayList<>());
        TeacherRepo teacherRepo = new TeacherRepo();

        try {
            teacherRepo.create(teacher1);
        }catch (IllegalArgumentException e){
            assert false;
        }

        try {
            teacherRepo.create(teacher2);
        }catch (IllegalArgumentException e){
            assert false;
        }

        try {
            teacherRepo.create(teacher3);
            assert false;
        }catch (IllegalArgumentException e){
            assert true;
        }
    }

    @org.junit.jupiter.api.Test
    void update() {
        Teacher teacher1 = new Teacher("Ion", "Creanga", 1, new ArrayList<>());
        Teacher teacher2 = new Teacher("Mihai", "Eminescu", 2, new ArrayList<>());
        Teacher teacher3 = new Teacher("Luca", "Caragiale", 3, new ArrayList<>());
        TeacherRepo teacherRepo = new TeacherRepo();
        teacherRepo.create(teacher1);
        teacherRepo.create(teacher2);

        teacher1.setFirstName("Lucian");
        try {
            teacherRepo.update(teacher1);
            if(teacherRepo.getById(teacher1.getTeacherId()).getFirstName() == "Lucian")
                assert true;
            else
                assert false;
        }catch (Exception e){
            assert false;
        }

        teacher2.setLastName("Blaga");

        try {
            teacherRepo.update(teacher2);
            if(teacherRepo.getById(teacher2.getTeacherId()).getLastName() == "Blaga")
                assert true;
            else
                assert false;
        }catch (Exception e){
            assert false;
        }

        try {
            teacherRepo.update(teacher3);
            assert false;
        }catch (Exception e){
            assert true;
        }
    }

    @org.junit.jupiter.api.Test
    void filterByCredits() {
        Course course1 = new Course();
        Course course2 = new Course();
        Course course3 = new Course();
        Course course4 = new Course();
        List<Course> courseList1 = new ArrayList<>();
        courseList1.add(course1);
        List<Course> courseList2 = new ArrayList<>();
        courseList2.add(course1);
        courseList2.add(course2);
        List<Course> courseList3 = new ArrayList<>();
        courseList3.add(course1);
        courseList3.add(course2);
        courseList3.add(course3);
        List<Course> courseList4 = new ArrayList<>();
        courseList4.add(course1);
        courseList4.add(course2);
        courseList4.add(course3);
        courseList4.add(course4);

        Teacher teacher1 = new Teacher("Ion", "Creanga", 1, courseList2);
        Teacher teacher2 = new Teacher("Mihai", "Eminescu", 2, new ArrayList<>());
        Teacher teacher3 = new Teacher("Luca", "Caragiale", 3, courseList3);
        Teacher teacher4 = new Teacher("Lucian", "Blaga", 4, courseList1);
        Teacher teacher5 = new Teacher("Tudor", "Arghezi", 5, courseList4);
        TeacherRepo teacherRepo = new TeacherRepo();
        List<Teacher> teacherList;
        teacherRepo.create(teacher1);
        teacherRepo.create(teacher2);
        teacherRepo.create(teacher3);
        teacherRepo.create(teacher4);
        teacherRepo.create(teacher5);

        for (int i = 0; i <= 4; i++)
            for (int j = i + 1; j <= 5; j++){
                teacherList = teacherRepo.filterByNumberOfCourses(i, j);
                for (Teacher teacher : teacherList)
                    if(teacher.getCourses().size() < i || teacher.getCourses().size() > j)
                        assert false;
            }
    }

    @org.junit.jupiter.api.Test
    void sortList() {
        Course course1 = new Course();
        Course course2 = new Course();
        Course course3 = new Course();
        Course course4 = new Course();
        List<Course> courseList1 = new ArrayList<>();
        courseList1.add(course1);
        List<Course> courseList2 = new ArrayList<>();
        courseList2.add(course1);
        courseList2.add(course2);
        List<Course> courseList3 = new ArrayList<>();
        courseList3.add(course1);
        courseList3.add(course2);
        courseList3.add(course3);
        List<Course> courseList4 = new ArrayList<>();
        courseList4.add(course1);
        courseList4.add(course2);
        courseList4.add(course3);
        courseList4.add(course4);

        Teacher teacher1 = new Teacher("Ion", "Creanga", 1, courseList2);
        Teacher teacher2 = new Teacher("Mihai", "Eminescu", 2, new ArrayList<>());
        Teacher teacher3 = new Teacher("Luca", "Caragiale", 3, courseList3);
        Teacher teacher4 = new Teacher("Lucian", "Blaga", 4, courseList1);
        Teacher teacher5 = new Teacher("Tudor", "Arghezi", 5, courseList4);
        TeacherRepo teacherRepo = new TeacherRepo();
        List<Teacher> teacherList;
        teacherRepo.create(teacher1);
        teacherRepo.create(teacher2);
        teacherRepo.create(teacher3);
        teacherRepo.create(teacher4);
        teacherRepo.create(teacher5);

        teacherRepo.sortList();

        teacherList = teacherRepo.getAll();
        for(int i = 0; i < teacherList.size() - 1; i++)
        {
            if(teacherList.get(i+1).getCourses().size() < teacherList.get(i).getCourses().size())
                assert false;
        }
    }
}