package pl.wavesoftware.test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.wavesoftware.test.entity.Pet;
import pl.wavesoftware.test.jpa.PetJPA;
import pl.wavesoftware.test.mapper.MapperFacade;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
public class MappingTest {

  private final TestRepository testRepository = new TestRepository();

  @Mock
  private EntityManager entityManager;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Test
  public void testMapFromPetToJPA() {
    // given
    Injector injector = createInjector((Module) binder ->
      binder.bind(EntityManager.class).toInstance(entityManager)
    );
    MapperFacade mapper = injector.getInstance(MapperFacade.class);
    Pet alice = testRepository.createPetNamedAlice();

    // when
    PetJPA aliceJpa = mapper.map(alice);

    // then
    assertThat(alice).isNotNull();
    assertThat(aliceJpa).isNotNull();
    assertThat(mapper).isNotNull();
  }

  private Injector createInjector(Module... modules) {
    List<Module> allModules = new ArrayList<>();
    allModules.add(new TestModule());
    allModules.addAll(Arrays.asList(modules));
    return Guice.createInjector(allModules);
  }
}
