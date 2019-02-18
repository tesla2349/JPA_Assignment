package edu.neu.cs5200.web.services.jaxrs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs5200.orm.jpa.daos.ActorDao;

@RestController
public class ActorService {
	
	ActorDao actorDao;
	
	
//	@GetMapping
//	("/api/faculty/{facultyId}/authored")
//	public Iterable<Course> findAuthoredCourses(
//			@PathVariable("facultyId") int fId) {
//		Faculty faculty = facultyRepository.findOne(fId);
//		return faculty.getAuthoredCourses();
//	}

//	@GetMapping("/api/course/{courseId}/author")
//	public Faculty findCourseAuthor(
//			@PathVariable("courseId") int cId) {
//		Course course = courseRepository.findOne(cId);
//		return course.getAuthor();
//	}

	
//	@PutMapping("/api/faculty/{fId}/authored/{cId}")
//	public void authoredCourse(
//			@PathVariable("fId") int fId,
//			@PathVariable("cId") int cId) {
//		Optional<Faculty> faculty = facultyRepository.findById(fId);
//		Course course   = courseRepository.findOne(cId);
//		course.setAuthor(faculty);
//		courseRepository.save(course);
//		faculty.authoredCourse(course);
//		facultyRepository.save(faculty);
//	}
//
//	@GetMapping("/api/faculty")
//	public List<Faculty> findAllFaculty() {
//		return (List<Faculty>) facultyRepository.findAll();
//	}
//	
//	@PostMapping("api/faculty")
//	public Faculty crreateFaculty(@RequestBody Faculty faculty){
//		return facultyRepository.save(faculty);
//	}
	
}
