package io.github.cepr0.demo;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;

import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class ModelRepoTest {

	@Autowired
	private ModelRepo modelRepo;

	@Before
	public void setUp() {
		modelRepo.saveAll(asList(
				Model.builder().name("1").build(),
				Model.builder().name("2").build(),
				Model.builder().name("3").build()
		));
	}

	@Test
	public void testOrder() {
		assertThat(modelRepo
				.getSortedList(PageRequest.of(0, 1000, Sort.Direction.DESC, "name"))
				.stream()
				.map(Model::getName)
				.collect(Collectors.toList())
		).as("Should return a list of models in descending order")
				.containsExactly("3", "2", "1");
	}
}
