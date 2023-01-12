package com.shreyas;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }



    public List<Student> getStudents() {
            return studentRepository.findAll();
    }

    public void addStudents(Student studentdetails) {

//        mentioned part checks for already existing email but doesn't work (error: The column name id was not found in this ResultSet)

//        Optional<Student> studentOptional = studentRepository.findStudentByEmail(studentdetails.getEmail());
//        if(studentOptional.isPresent()) {
//            throw new IllegalStateException("email taken");
//        }
        studentRepository.save(studentdetails);
    }

    public void deleteStudents(Integer id) {
        studentRepository.deleteById(id);
    }


    @Transactional
    public void updateStudents(Integer id, StudentController.NewStudentRequest updatestudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("student with id " + id + " does not exist"));

        if(updatestudent.name() != null &&
                updatestudent.name().length() > 0 &&
                !Objects.equals(student.getName(), updatestudent.name())
        ) {
            student.setName(updatestudent.name());
        }

        if(updatestudent.email() != null &&
                updatestudent.email().length() > 0 &&
                !Objects.equals(student.getEmail(), updatestudent.email())
        ) {

//            mentioned part checks for already existing email but doesn't work (error: The column name id was not found in this ResultSet)

//            Optional<Student> studentOptional = studentRepository.findStudentByEmail(updatestudent.email());
//            if(studentOptional.isPresent()) {
//                throw new IllegalStateException("email taken");
//            }

            student.setEmail(updatestudent.email());
        }

        if(updatestudent.dob() != null &&
                (updatestudent.dob().lengthOfYear() > 0 && updatestudent.dob().lengthOfMonth() > 0) &&
                !Objects.equals(student.getDob(), updatestudent.dob())
        ) {
            student.setDob(updatestudent.dob());
        }

        if(updatestudent.age() != null &&
                updatestudent.age() > 0 &&
                !Objects.equals(student.getAge(), updatestudent.age())
        ) {
            student.setAge(updatestudent.age());
        }

        studentRepository.save(student);
    }
}
