package pl.wavesoftware.test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import lombok.RequiredArgsConstructor;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;
import pl.wavesoftware.test.TestRepository.Database;
import pl.wavesoftware.test.TestRepository.Example;
import pl.wavesoftware.test.TestRepository.Execution;
import pl.wavesoftware.test.entity.Pet;
import pl.wavesoftware.test.jpa.PetJPA;
import pl.wavesoftware.test.mapper.MapperFacade;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testMapFromPetToJPA() {
    // given
    Injector injector = createInjector((Module) binder ->
      binder.bind(EntityManager.class).toInstance(entityManager)
    );
    MapperFacade mapper = injector.getInstance(MapperFacade.class);
    Execution execution = testRepository.forCase(Example.STANDARD);
    Database<Pet> entityDb = execution.createPetNamedAlice();
    Database<PetJPA> database = execution.createJpaPetNamedAlice();
    bindEntityManager(database);
    Pet alice = entityDb.getObject();
    Optional.ofNullable(alice.getOwner())
      .ifPresent(o -> o.setName("John Wick"));

    // when
    PetJPA aliceJpa = mapper.map(alice);

    // then
    assertThat(alice).isNotNull();
    assertThat(aliceJpa).isNotNull();
    assertThat(mapper).isNotNull();
    assertThat(aliceJpa.getId()).isEqualTo(TestRepository.ALICE_ID);
    assertThat(alice.getOwner()).isNotNull();
    assertThat(aliceJpa.getOwner().getName()).isEqualTo(TestRepository.OWNER_NAME);
    assertThat(aliceJpa.getOwner().getSurname()).isEqualTo("Wick");
  }

  @Test
  public void testMapFromJPAToPet() {
    // given
    Injector injector = createInjector((Module) binder ->
      binder.bind(EntityManager.class).toInstance(entityManager)
    );
    MapperFacade mapper = injector.getInstance(MapperFacade.class);
    Database<PetJPA> database = testRepository
      .forCase(Example.STANDARD)
      .createJpaPetNamedAlice();
    PetJPA aliceJpa = database.getObject();

    // when
    Pet alice = mapper.map(aliceJpa);

    // then
    assertThat(alice).isNotNull();
    assertThat(aliceJpa).isNotNull();
    assertThat(mapper).isNotNull();
    assertThat(alice.getReference()).isEqualTo(TestRepository.ALICE_ID);
    assertThat(alice.getOwner()).isNotNull();
    assertThat(alice.getOwner().getReference())
      .isEqualTo(TestRepository.OWNER_ID);
    assertThat(alice.getOwner().getName())
      .isEqualTo(TestRepository.OWNER_NAME + " " + TestRepository.OWNER_SURNAME);
  }

  @Test
  public void testMissingMappingForToy() {
    // given
    Injector injector = createInjector((Module) binder ->
      binder.bind(EntityManager.class).toInstance(entityManager)
    );
    MapperFacade mapper = injector.getInstance(MapperFacade.class);
    Execution execution = testRepository.forCase(Example.WITH_TOY);
    Database<Pet> entityDb = execution.createPetNamedAlice();
    Database<PetJPA> database = execution.createJpaPetNamedAlice();
    bindEntityManager(database);
    Pet alice = entityDb.getObject();

    // then
    assertThat(alice).isNotNull();
    assertThat(mapper).isNotNull();
    thrown.expect(EidIllegalStateException.class);
    thrown.expectMessage("20180425:135245");
    thrown.expectMessage("Mapping for source class pl.wavesoftware.test.entity.Toy " +
      "and target class pl.wavesoftware.test.jpa.ToyJPA is not configured!");

    // when
    mapper.map(alice);
  }

  private void bindEntityManager(Database<?> database) {
    Class<?> cls = any(Class.class);
    when(entityManager.find(cls, anyLong()))
      .thenAnswer(new EntityManagerAnswer(database));
  }

  private Injector createInjector(Module... modules) {
    List<Module> allModules = new ArrayList<>();
    allModules.add(new TestModule());
    allModules.addAll(Arrays.asList(modules));
    return Guice.createInjector(allModules);
  }

  @RequiredArgsConstructor
  private static final class EntityManagerAnswer implements Answer<Object> {
    private final Database<?> database;
    @Override
    public Object answer(InvocationOnMock invocationOnMock) {
      Class<?> cls = invocationOnMock.getArgument(0);
      Object id = invocationOnMock.getArgument(1);
      return database.find(cls, id);
    }
  }
}
