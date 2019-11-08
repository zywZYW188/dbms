package com.example.dbms.service;


import com.example.dbms.domain.Student;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "student")
public interface StudentService {

	Student update(Student student);

	void deleteStudentBySno(String sno);
	@Cacheable(key = "#p0")
	Student queryStudentBySno(String sno);
}
