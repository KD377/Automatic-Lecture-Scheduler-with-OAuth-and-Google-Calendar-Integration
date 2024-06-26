package com.example.lecturescheduler.service;

import com.example.lecturescheduler.exception.ResourceNotFoundException;
import com.example.lecturescheduler.model.Instructor;
import com.example.lecturescheduler.model.SingleGroup;
import com.example.lecturescheduler.model.Subject;
import com.example.lecturescheduler.repository.GroupRepository;
import com.example.lecturescheduler.repository.InstructorRepository;
import com.example.lecturescheduler.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;
    private static final String INSTRUCTOR_NOT_FOUND = "Instructor not found for this id :: ";

    @Autowired
    public InstructorService(InstructorRepository instructorRepository, SubjectRepository subjectRepository, GroupRepository groupRepository) {
        this.instructorRepository = instructorRepository;
        this.subjectRepository = subjectRepository;
        this.groupRepository = groupRepository;
    }

    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public List<Instructor> findBySubject(Subject subject){
        return instructorRepository.findBySubject(subject);
    }

    public List<Instructor> findAllInstructors() {
        return instructorRepository.findAll();
    }

    public Optional<Instructor> findInstructorById(Long id) {
        return instructorRepository.findById(id);
    }

    public Instructor updateInstructor(Long id, Instructor instructorDetails) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(INSTRUCTOR_NOT_FOUND + id));
        instructor.setName(instructorDetails.getName());
        instructor.setEmail(instructorDetails.getEmail());
        instructor.setDepartment(instructorDetails.getDepartment());
        instructor.setSubjectsTaught(instructorDetails.getSubjectsTaught());
        instructor.setPreferences(instructorDetails.getPreferences());
        return instructorRepository.save(instructor);
    }

    public void deleteInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(INSTRUCTOR_NOT_FOUND + id));
        instructorRepository.delete(instructor);
    }

    public Instructor addSubjectTaught(Long instructorId, Long subjectId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException(INSTRUCTOR_NOT_FOUND + instructorId));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found for this id :: " + subjectId));

        instructor.getSubjectsTaught().add(subject);
        return instructorRepository.save(instructor);
    }

    public Instructor removeSubjectTaught(Long instructorId, Long subjectId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException(INSTRUCTOR_NOT_FOUND + instructorId));
        instructor.getSubjectsTaught().removeIf(subject -> subject.getId().equals(subjectId));
        return instructorRepository.save(instructor);
    }

    public Instructor addGroup(Long instructorId, Long groupId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException(INSTRUCTOR_NOT_FOUND + instructorId));
        SingleGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found for this id :: " + groupId));

        instructor.getGroups().add(group);
        return instructorRepository.save(instructor);
    }

    public Instructor removeGroup(Long instructorId, Long groupId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException(INSTRUCTOR_NOT_FOUND + instructorId));
        instructor.getGroups().removeIf(group -> group.getId().equals(groupId));
        return instructorRepository.save(instructor);
    }


}
