package io.github.cepr0.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@Entity
public class Model {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
}
