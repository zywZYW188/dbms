package com.example.dbms.service.impl;


import com.example.dbms.dao.StudentMapper;
import com.example.dbms.domain.Student;
import com.example.dbms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentMapper studentMapper;
	
	@Override
	public Student update(Student student) {
		this.studentMapper.update(student);
		return this.studentMapper.queryStudentBySno(student.getSno());
	}

	@Override
	public void deleteStudentBySno(String sno) {
		this.studentMapper.deleteStudentBySno(sno);
	}

	@Override
	public Student queryStudentBySno(String sno) {
		return this.studentMapper.queryStudentBySno(sno);
	}

}
