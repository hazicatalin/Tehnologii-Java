package com.mycompany.lab3;


import Table.Exam;
import java.time.LocalTime;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ExamRepository {
    @PersistenceContext(unitName = "persistanceUnit")
    private EntityManager em;
    
    public List<Exam> findAll(){
        return em.createNamedQuery("Exam.findAll").getResultList();
    }
    
    
    public Exam findByName(String name){
        return (Exam) em.createNamedQuery("Exam.findByName").setParameter("name", name).getSingleResult();
    }
    
    
    public Exam findByStartingTime(LocalTime time){
        return (Exam) em.createNamedQuery("Exam.findByStartingTime").setParameter("startingTime", time).getSingleResult();
    }
    
    public Exam findByDuration(LocalTime duration){
        return (Exam) em.createNamedQuery("Exam.findByDuration").setParameter("duration", duration).getSingleResult();
    }    
}
