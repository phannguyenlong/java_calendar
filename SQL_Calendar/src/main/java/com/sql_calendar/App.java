package com.sql_calendar;

import java.io.*;
import java.util.*;

// Other Java Class
import com.sql_calendar.resources.Student;
import com.sql_calendar.util.GetRequestModel;
import com.sql_calendar.util.PostRequestModel;

public class App {

    public static void main(String[] args) throws IOException {
        // GET Request
        System.out.println("Make request to server");
        GetRequestModel request = new GetRequestModel();
        ArrayList<Student> res = request.makeRequest("/database", Student.class);

        for (Student std : res) {
            System.out.println(std);
        }

        // POST Request
        ArrayList<Student> list = new ArrayList<>();

        list.add(new Student(5, "long", "HCM"));
        list.add(new Student(6, "thao", "q9"));
        list.add(new Student(7, "duy", "Binh tho"));

        PostRequestModel resquest = new PostRequestModel();
        int res_code = resquest.makeRequest("/database", list);
        if (res_code == 200) {
            System.out.println("Successfull make request");
        } else {
            System.out.println("Failed");
        }
    }
}