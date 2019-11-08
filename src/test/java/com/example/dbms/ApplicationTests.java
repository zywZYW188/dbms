package com.example.dbms;

import com.example.dbms.domain.Student;
import com.example.dbms.domain.po.Users;
import com.example.dbms.service.StudentService;
import com.example.dbms.service.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {
    @Autowired
    private UsersService usersService;
    @Autowired
    private StudentService studentService;
    @Test
    public void test1() throws Exception {
        long startTime1 = System.currentTimeMillis();
        Users users2=this.usersService.findById(1);
        long endTime1 = System.currentTimeMillis();
        System.out.println(users2.gethospital()+(endTime1-startTime1)+"ms");
        long startTime2= System.currentTimeMillis();
        Users users1=this.usersService.findById(1);
        long endTime2 = System.currentTimeMillis();
        System.out.println(users1.gethospital()+(endTime2-startTime2)+"ms");
    }
    @Test
    public void test2() throws Exception {
        long startTime1 = System.currentTimeMillis();
        Student student1 = this.studentService.queryStudentBySno("001");
        long endTime1 = System.currentTimeMillis();
        System.out.println("学号" + student1.getSno() + "的学生姓名为：" + student1.getName()+(endTime1-startTime1)+"ms");
        long startTime2= System.currentTimeMillis();
        Student student2 = this.studentService.queryStudentBySno("001");
        long endTime2 = System.currentTimeMillis();
        System.out.println("学号" + student2.getSno() + "的学生姓名为：" + student2.getName()+(endTime2-startTime2)+"ms");
    }

}
