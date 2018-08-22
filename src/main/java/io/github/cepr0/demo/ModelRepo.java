package io.github.cepr0.demo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModelRepo extends JpaRepository<Model, Long> {

	@Query(value = "select m.id as id, m.name as name from model m", nativeQuery = true)
	List<Model> getSortedList(Pageable p);
}
