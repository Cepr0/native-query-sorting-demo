package io.github.cepr0.demo;

import com.integralblue.log4jdbc.spring.Log4jdbcAutoConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.DESC;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = NONE)
@ImportAutoConfiguration(Log4jdbcAutoConfiguration.class)
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
				.getSortedList(of(0, 1000, DESC, "name"))
				.stream()
				.map(Model::getName)
				.collect(toList())
		).as("Should return a list of models in descending order")
				.containsExactly("3", "2", "1");
	}
}
