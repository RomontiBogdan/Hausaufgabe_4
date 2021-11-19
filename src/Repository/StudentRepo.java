package Repository;

import Entities.Course;
import Entities.Student;

import java.util.List;

public class StudentRepo extends InMemoryRepository<Student>{
    public StudentRepo() {
        super();
    }

    /**
     * Adds student to repo, throws exception if id is in repo already
     * @param student
     * @return added student
     * @throws IllegalArgumentException
     */
    @Override
    public Student create(Student student) throws IllegalArgumentException{
        for(Student stu : this.repoList){
            if(stu.getStudentId() == student.getStudentId())
                throw new IllegalArgumentException("There is already a student with this id in the list");
        }
        this.repoList.add(student);
        return student;
    }

    /**
     * Updates student with the given value, throws exception if student id doesn't exist in repo
     * @param student
     * @return updated course
     */
    @Override
    public Student update(Student student) {
        Student updatedStudent = this.repoList.stream().filter(stud -> stud.getStudentId() == student.getStudentId()).findFirst().orElseThrow();
        updatedStudent.setEnrolledCourses(student.getEnrolledCourses());
        updatedStudent.setFirstName(student.getFirstName());
        updatedStudent.setLastName(student.getLastName());
        updatedStudent.setTotalCredits(student.getTotalCredits());

        return updatedStudent;

    }

    /**
     * Returns list of students with credits between min and max
     * @param min
     * @param max
     * @return list of students with credits between min and max
     */
    public List<Student> filterByCredits(int min, int max){
        return this.repoList.stream().filter(student -> student.getTotalCredits() >= min && student.getTotalCredits() <= max).toList();
    }

    /**
     * Sorts list ascending based on number of credits
     */
    public void sortList(){
        this.repoList.sort((s1, s2) -> {
            if (s1.getTotalCredits() < s2.getTotalCredits()) return -1;
            else if (s1.getTotalCredits() == s2.getTotalCredits()) return 0;
            return 1;
        });
    }

    /**
     * Returns student with the given id
     * @param id
     * @return
     */
    public Student getById(long id){
        for(Student student : this.repoList){
            if(id == student.getStudentId())
                return student;
        }
        return null;
    }
}