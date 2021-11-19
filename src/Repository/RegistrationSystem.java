package Repository;

import Entities.Course;
import Entities.Student;

import java.util.ArrayList;
import java.util.List;

public class RegistrationSystem {
    private CourseRepo courseRepo;
    private StudentRepo studentRepo;
    private TeacherRepo teacherRepo;

    public RegistrationSystem() {
        courseRepo = new CourseRepo();
        studentRepo = new StudentRepo();
        teacherRepo = new TeacherRepo();
    }

    public RegistrationSystem(CourseRepo courseRepo, StudentRepo studentRepo, TeacherRepo teacherRepo) {
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
    }

    public boolean register(Course course, Student student) {
        if ((course.getStudentsEnrolled().size() >= course.getMaxStudends()) && (student.getTotalCredits() + course.getCredits() > 30))
            return false;

        List<Student> students = course.getStudentsEnrolled();
        students.add(student);
        course.setStudentsEnrolled(students);

        List<Course> courses = student.getEnrolledCourses();
        courses.add(course);
        student.setEnrolledCourses(courses);
        student.setTotalCredits(student.getTotalCredits() + course.getCredits());

        this.courseRepo.update(course);
        this.studentRepo.update(student);

        return true;
    }

    public List<Course> retrieveCoursesWithFreePlaces() {
        List<Course> availableCourses;
        availableCourses = new ArrayList<>();
        for (Course course : courseRepo.getAll()) {
            if (course.getStudentsEnrolled().size() < course.getMaxStudends()) {
                availableCourses.add(course);
            }
        }
        return availableCourses;
    }


    public List<Student> retrieveStudentsEnrolledForACourse(Course course) {
        return course.getStudentsEnrolled();
    }


    public List<Course> getAllCourses() {
        return courseRepo.getAll();
    }

}