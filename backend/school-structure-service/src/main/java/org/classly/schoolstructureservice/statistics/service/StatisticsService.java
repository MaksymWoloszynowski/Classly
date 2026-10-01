package org.classly.schoolstructureservice.statistics.service;

import org.classly.schoolstructureservice.classificationPeriod.repository.ClassificationPeriodRepository;
import org.classly.schoolstructureservice.group.repository.GroupRepository;
import org.classly.schoolstructureservice.parent.repository.ParentRepository;
import org.classly.schoolstructureservice.statistics.dto.StatisticsResponseDTO;
import org.classly.schoolstructureservice.student.repository.StudentRepository;
import org.classly.schoolstructureservice.subject.repository.SubjectRepository;
import org.classly.schoolstructureservice.teacher.repository.TeacherRepository;
import org.classly.schoolstructureservice.teachingAssignment.repository.TeachingAssignmentRepository;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	private final ParentRepository parentRepository;
	private final GroupRepository groupRepository;
	private final SubjectRepository subjectRepository;
	private final TeachingAssignmentRepository teachingAssignmentRepository;
	private final ClassificationPeriodRepository classificationPeriodRepository;

	public StatisticsService(
			StudentRepository studentRepository,
			TeacherRepository teacherRepository,
			ParentRepository parentRepository,
			GroupRepository groupRepository,
			SubjectRepository subjectRepository,
			TeachingAssignmentRepository teachingAssignmentRepository,
			ClassificationPeriodRepository classificationPeriodRepository) {
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
		this.parentRepository = parentRepository;
		this.groupRepository = groupRepository;
		this.subjectRepository = subjectRepository;
		this.teachingAssignmentRepository = teachingAssignmentRepository;
		this.classificationPeriodRepository = classificationPeriodRepository;
	}

	public StatisticsResponseDTO getStatistics() {
		return new StatisticsResponseDTO(
				studentRepository.count(),
				teacherRepository.count(),
				parentRepository.count(),
				groupRepository.count(),
				subjectRepository.count(),
				teachingAssignmentRepository.count(),
				classificationPeriodRepository.count());
	}
}
