package pl.wavesoftware.test;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.wavesoftware.test.entity.AbstractEntity;
import pl.wavesoftware.test.entity.Owner;
import pl.wavesoftware.test.entity.Pet;
import pl.wavesoftware.test.entity.Toy;
import pl.wavesoftware.test.jpa.AbstractRecord;
import pl.wavesoftware.test.jpa.OwnerJPA;
import pl.wavesoftware.test.jpa.PetJPA;
import pl.wavesoftware.test.jpa.ToyJPA;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
final class TestRepository {

  static final long ALICE_ID = 14L;
  private static final String KITIE_NAME = "Kitie";

  private static final long KITIE_ID = 15L;
  private static final String ALICE_NAME = "Alice";

  static final Long OWNER_ID = 16L;
  static final String OWNER_NAME = "John";
  static final String OWNER_SURNAME = "Doe";

  private static final Long TOY_ID = 17L;

  Execution forCase(Example example) {
    return new ExampledExecution(example);
  }

  enum Example {
    STANDARD,
    WITH_TOY
  }

  interface Execution {
    Database<Pet> createPetNamedAlice();
    Database<PetJPA> createJpaPetNamedAlice();
  }

  interface Database<T> {
    T getObject();
    @Nullable
    <E> E find(Class<?> cls, Object id);
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  private static final class ExampledExecution implements Execution {
    private final Example example;

    @Override
    public Database<Pet> createPetNamedAlice() {
      Pet alice = new Pet();
      alice.setReference(ALICE_ID);
      alice.setName(ALICE_NAME);
      Owner owner = new Owner();
      owner.setReference(OWNER_ID);
      owner.setName(OWNER_NAME + " " + OWNER_SURNAME);

      Pet kitie = new Pet();
      kitie.setReference(KITIE_ID);
      kitie.setName(KITIE_NAME);

      alice.setOwner(owner);
      owner.getPets().addAll(Arrays.asList(alice, kitie));

      DatabaseImpl<Pet> db = new DatabaseImpl<>(alice, candidate -> {
        if (candidate instanceof AbstractEntity) {
          return Optional.ofNullable(
            AbstractEntity.class.cast(candidate).getReference()
          );
        }
        return Optional.empty();
      });

      if (example == Example.WITH_TOY) {
        Toy toy = new Toy("ball");
        toy.setReference(TOY_ID);
        kitie.setToy(toy);
        db.add(toy);
      }

      db.add(kitie);
      db.add(owner);
      return db;
    }

    @Override
    public Database<PetJPA> createJpaPetNamedAlice() {
      PetJPA alice = new PetJPA();
      alice.setId(ALICE_ID);
      alice.setName(ALICE_NAME);

      PetJPA kitie = new PetJPA();
      kitie.setId(KITIE_ID);
      kitie.setName(KITIE_NAME);

      OwnerJPA owner = new OwnerJPA();
      owner.setId(OWNER_ID);
      owner.setName(OWNER_NAME);
      owner.setSurname(OWNER_SURNAME);

      alice.setOwner(owner);
      owner.getPets().addAll(Arrays.asList(alice, kitie));

      DatabaseImpl<PetJPA> db = new DatabaseImpl<>(alice, candidate -> {
        if (candidate instanceof AbstractRecord) {
          return Optional.ofNullable(
            AbstractRecord.class.cast(candidate).getId()
          );
        }
        return Optional.empty();
      });

      if (example == Example.WITH_TOY) {
        ToyJPA toy = new ToyJPA("ball");
        toy.setId(TOY_ID);
        kitie.setToy(toy);
        db.add(toy);
      }

      db.add(kitie);
      db.add(owner);
      return db;
    }
  }

  private static final class DatabaseImpl<T> implements Database<T> {

    private final Map<Class<?>, Set<?>> objects = new HashMap<>();
    private final T entity;
    private final Function<Object, Optional<Object>> idCollector;

    private DatabaseImpl(T entity,
                         Function<Object, Optional<Object>> idCollector) {
      add(entity);
      this.entity = entity;
      this.idCollector = idCollector;
    }

    @SuppressWarnings("unchecked")
    private <E> void add(E entity) {
      Class<E> cls = (Class<E>) entity.getClass();
      if (!objects.containsKey(cls)) {
        objects.put(cls, new LinkedHashSet<>());
      }
      Set<E> set = (Set<E>) objects.get(cls);
      set.add(entity);
    }

    @Override
    public T getObject() {
      return checkNotNull(entity, "20180504:160340");
    }

    @Nullable
    @Override
    public <E> E find(Class<?> cls, Object id) {
      if (objects.containsKey(cls)) {
        @SuppressWarnings("unchecked")
        Set<E> set = (Set<E>) objects.get(cls);
        for (E e : set) {
          Optional<Object> eId = idCollector.apply(e);
          if (eId.isPresent() && eId.get().equals(id)) {
            return e;
          }
        }
      }
      return null;
    }
  }
}
