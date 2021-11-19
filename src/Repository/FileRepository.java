package Repository;

import Entities.Course;
import Entities.Student;
import Entities.Teacher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class FileRepository {
    private CourseRepo courseRepo;
    private StudentRepo studentRepo;
    private TeacherRepo teacherRepo;

    public FileRepository(CourseRepo courseRepo, StudentRepo studentRepo, TeacherRepo teacherRepo) {
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
    }

    /**
     * Reads courses from given file
     * @param filename
     */
    public void readCoursesFromFile(String filename){
        List<Course> courses = new ArrayList<>();
        List<String> coursesString;
        List<String> studentString;
        List<Student> studentList = new ArrayList<>();
        Teacher teacher;
        Student student;

        try {
            File myObj = new File(System.getProperty("user.dir") + "\\src\\Data\\" + filename);
            Scanner myReader = new Scanner(myObj);
            Course course;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                coursesString = Arrays.stream(data.split(";")).toList();
                studentString = Arrays.stream(coursesString.get(3).split(",")).toList();
                teacher = new Teacher();
                teacher.setTeacherId(Integer.parseInt(coursesString.get(2)));
                for(String id : studentString) {
                    student = new Student();
                    student.setStudentId(Integer.parseInt(id));
                    studentList.add(student);
                }
                course = new Course(Integer.parseInt(coursesString.get(0)), coursesString.get(1), teacher, studentList, Integer.parseInt(coursesString.get(4)), Integer.parseInt(coursesString.get(5)));
                this.courseRepo.create(course);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Reads students from given file
     * @param filename
     */
    public void readStudentsFromFile(String filename){
        List<Course> courses = new ArrayList<>();
        List<String> coursesString;
        List<String> studentString;
        Course course;

        try {
            File myObj = new File(System.getProperty("user.dir") + "\\src\\Data\\" + filename);
            Scanner myReader = new Scanner(myObj);
            Student student;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                studentString = Arrays.stream(data.split(";")).toList();
                coursesString = Arrays.stream(studentString.get(4).split(",")).toList();
                for(String id : coursesString) {
                    course = new Course();
                    course.setCourseId(Integer.parseInt(id));
                    courses.add(course);
                }
                student = new Student(studentString.get(0), studentString.get(1), Integer.parseInt(studentString.get(2)), Integer.parseInt(studentString.get(3)), courses);
                this.studentRepo.create(student);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Reads teachers from given file
     * @param filename
     */
    public void readTeachersFromFile(String filename){
        List<Course> courses = new ArrayList<>();
        List<String> coursesString;
        List<String> teachersString;
        List<Teacher> teacherList;
        List<Student> studentList = new ArrayList<>();
        Course course;

        try {
            File myObj = new File(System.getProperty("user.dir") + "\\src\\Data\\" + filename);
            Scanner myReader = new Scanner(myObj);
            Teacher teacher;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                teachersString = Arrays.stream(data.split(";")).toList();
                coursesString = Arrays.stream(teachersString.get(3).split(",")).toList();
                for(String id : coursesString) {
                    course = new Course();
                    course.setCourseId(Integer.parseInt(id));
                    courses.add(course);
                }
                teacher = new Teacher(coursesString.get(0), coursesString.get(1), Integer.parseInt(coursesString.get(2)), courses);
                this.teacherRepo.create(teacher);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * After reading, this function updates the dependencies
     */
    public void UpdateData(){
        Student student;
        List<Student> studentList = new ArrayList<>();
        Course course;
        List<Course> courseList = new ArrayList<>();
        Teacher teacher;
        List<Teacher> teacherList = new ArrayList<>();

        for(Course cou : this.courseRepo.getAll()) {
            for (Student stu : cou.getStudentsEnrolled()) {
                student = this.studentRepo.getById(stu.getStudentId());
                studentList.add(student);
            }
            cou.setStudentsEnrolled(studentList);
            this.courseRepo.update(cou);
            studentList = new ArrayList<>();
        }

        for(Student stu : this.studentRepo.getAll()) {
            for (Course cou : stu.getEnrolledCourses()) {
                course = this.courseRepo.getById(cou.getCourseId());
                courseList.add(course);
            }
            stu.setEnrolledCourses(courseList);
            this.studentRepo.update(stu);
            courseList = new ArrayList<>();
        }

        for(Teacher teach : this.teacherRepo.getAll()) {
            for (Course cou : teach.getCourses()) {
                course = this.courseRepo.getById(cou.getCourseId());
                courseList.add(course);
            }
            teach.setCourses(courseList);
            this.teacherRepo.update(teach);
            courseList = new ArrayList<>();
        }
    }

    /**
     * Writes student in the given file
     * @param filename
     */
    public void writeStudentsToFile(String filename){
        try {
            FileWriter myWriter = new FileWriter(System.getProperty("user.dir") + "\\src\\Data\\" + filename);
            for(Student student : this.studentRepo.getAll()){
                myWriter.write(student.getFirstName() + ";" + student.getLastName() + ";" + student.getStudentId() + ";" + student.getTotalCredits() + ";");
                for(Course course : student.getEnrolledCourses())
                    myWriter.write(course.getCourseId() + ",");
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Writes corses in the given file
     * @param filename
     */
    public void writeCoursesToFile(String filename){
        try {
            FileWriter myWriter = new FileWriter(System.getProperty("user.dir") + "\\src\\Data\\" + filename);
            for(Course course : this.courseRepo.getAll()){
                myWriter.write(course.getCourseId() + ";" + course.getName() + ";" + course.getTeacher().getTeacherId() + ";");

                for(Student student : course.getStudentsEnrolled())
                    myWriter.write(student.getStudentId() + ",");

                myWriter.write(";" + course.getCredits() + ";" + course.getMaxStudends() + ";" + course.getTeacher().getTeacherId() + ";" + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Writes teachers in the given file
     * @param filename
     */
    public void writeTeachersToFile(String filename){
        try {
            FileWriter myWriter = new FileWriter(System.getProperty("user.dir") + "\\src\\Data\\" + filename);
            for(Teacher teacher : this.teacherRepo.getAll()){
                myWriter.write(teacher.getFirstName() + ";" + teacher.getLastName() + ";" + teacher.getTeacherId() + ";");
                for(Course course : teacher.getCourses())
                    myWriter.write(course.getCourseId() + ",");
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
