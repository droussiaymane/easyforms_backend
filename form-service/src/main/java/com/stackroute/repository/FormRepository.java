package com.stackroute.repository;


import com.stackroute.dao.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form,Integer> {
}
