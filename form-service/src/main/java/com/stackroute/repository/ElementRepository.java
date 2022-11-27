package com.stackroute.repository;

import com.stackroute.dao.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElementRepository extends JpaRepository<Element,String> {
    List<Element> findAllByFormIdOrderByOrderElement(int id);
    List<Element> deleteAllByFormId(int id);

}
