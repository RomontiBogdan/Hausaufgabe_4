package Repository;

import Entities.Course;
import Entities.Student;
import Entities.Teacher;

import java.util.List;

public class CourseRepo extends InMemoryRepository<Course>{
    public CourseRepo() {
        super();
    }

    /**
     * Adds course to repo, throws exception if id is in repo already
     * @param course
     * @return added course
     * @throws IllegalArgumentException
     */
    @Override
    public Course create(Course course) throws IllegalArgumentException{
        for(Course cou : this.repoList){
            if(cou.getCourseId() == course.getCourseId())
                throw new IllegalArgumentException("There is already a course with this id in the list");
        }
        this.repoList.add(course);
        return course;
    }

    /**
     * Updates course with the given value, throws exception if course id doesn't exist in repo
     * @param course
     * @return updated course
     */
    @Override
    public Course update(Course course){
        Course updatedCourse = this.repoList.stream().filter(cou -> cou.getCourseId() == course.getCourseId()).findFirst().orElseThrow();
        updatedCourse.setCredits(course.getCredits());
        updatedCourse.setName(course.getName());
        updatedCourse.setTeacher((Teacher) course.getTeacher());
        updatedCourse.setStudentsEnrolled(course.getStudentsEnrolled());

        return updatedCourse;
    }

    /**
     * Returns list of courses with credits between min and max
     * @param min
     * @param max
     * @return list of courses with credits between min and max
     */
    public List<Course> filterByCredits(int min, int max){
        return this.repoList.stream().filter(course -> course.getCredits() >= min && course.getCredits() <= max).toList();
    }

    /**
     * Sorts list ascending based on number of credits
     */
    public void sortList(){
        this.repoList.sort((c1, c2) -> {
            if (c1.getCredits() < c2.getCredits()) return -1;
            else if (c1.getCredits() == c2.getCredits()) return 0;
            return 1;
        });
    }

    /**
     * Returns course with the given id
     * @param id
     * @return
     */
    public Course getById(long id){
        for(Course course : this.repoList){
            if(id == course.getCourseId())
                return course;
        }
        return null;
    }
}