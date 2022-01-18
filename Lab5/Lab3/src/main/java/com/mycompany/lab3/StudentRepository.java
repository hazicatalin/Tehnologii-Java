package com.mycompany.lab3;


import Table.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

@Stateless
public class StudentRepository {
    @PersistenceContext(unitName = "persistanceUnit")
    private EntityManager em;
    
    public List<Student> findAll(){
        return em.createNamedQuery("Student.findAll").getResultList();
    }
    
    public Student findByName(String name){
        return (Student) em.createNamedQuery("Student.findByName").setParameter("name", name).getSingleResult();
    }
}
