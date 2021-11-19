package Repository;

import Entities.Student;
import Entities.Teacher;

import java.util.List;

public class TeacherRepo extends InMemoryRepository<Teacher>{
    public TeacherRepo() {
        super();
    }

    /**
     * Adds teacher to repo, throws exception if id is in repo already
     * @param teacher
     * @return added teacher
     * @throws IllegalArgumentException
     */
    @Override
    public Teacher create (Teacher teacher) throws IllegalArgumentException{
        for(Teacher teac : this.repoList){
            if(teac.getTeacherId() == teacher.getTeacherId())
                throw new IllegalArgumentException("There is already a teacher with this id in the list");
        }
        this.repoList.add(teacher);
        return teacher;
    }

    /**
     * Updates teacher with the given value, throws exception if teacher id doesn't exist in repo
     * @param teacher
     * @return updated teacher
     */
    @Override
    public Teacher update(Teacher teacher){
        Teacher updatedTeacher = this.repoList.stream().filter(teach -> teach.getTeacherId() == teacher.getTeacherId()).findFirst().orElseThrow();
        updatedTeacher.setCourses(teacher.getCourses());
        updatedTeacher.setFirstName(teacher.getFirstName());
        updatedTeacher.setLastName(teacher.getLastName());

        return updatedTeacher;
    }

    /**
     * Returns list of teachers with number of courses between min and max
     * @param min
     * @param max
     * @return list of teachers with number of courses between min and max
     */
    public List<Teacher> filterByNumberOfCourses(int min, int max){
        return this.repoList.stream().filter(teacher -> teacher.getCourses().size() >= min && teacher.getCourses().size() <= max).toList();
    }

    /**
     * Sorts list ascending based on number of courses
     */
    public void sortList(){
        this.repoList.sort((t1, t2) -> {
            if (t1.getCourses().size() < t2.getCourses().size()) return -1;
            else if (t1.getCourses().size() == t2.getCourses().size()) return 0;
            return 1;
        });
    }

    /**
     * Returns teacher with the given id
     * @param id
     * @return
     */
    public Teacher getById(long id){
        for(Teacher teacher : this.repoList){
            if(id == teacher.getTeacherId())
                return teacher;
        }
        return null;
    }
}