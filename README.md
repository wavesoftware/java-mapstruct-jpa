# JPA mapping utilities for MapStruct

A set of utilities focused on mapping JPA managed entities with MapStruct. There are different utilities for different purposes and also a all-in-one utility for maximizing ease of use.

## Features

* Domain model graph with cycles - via `CyclicGraphContext`
* JPA aware mapping with update capability - via `JpaMappingContext` factory
* [N+1 problem](https://stackoverflow.com/questions/97197/what-is-the-n1-select-query-issue) solution via special uninitialized collection classes, that throws exceptions if used

### Domain model graph with cycles

If you need to map a domain model with cycles in entity graph for ex.: (Pet.owner -> Person, Person.pets -> Pet) you can use a `CyclicGraphContext` as a MapStruct `@Context`

```java
@Mapper
interface PetMapper {
  Pet map(PetData data, @Context CyclicGraphContext context);
  PetData map(Pet pet, @Context CyclicGraphContext context);
}
```

### JPA aware mapping with update capability

If you also need support for mapping JPA managed entities and be able to update them (not create new records) there more to be done. There is provided `JpaMappingContext` with factory. It requires couple more configuration to instantiate this context.

`JpaMappingContext` factory requires:
* Supplier of `StoringMappingContext` to handle cycles - `CyclicGraphContext` can be used here,
* `Mappings` object that will provides mapping for given source and target class - mapping is information how to update existing object (managed entity) with data from source object,
* `IdentifierCollector` should collect managed entity ID from source object

The easiest way to setup all of this is to extend `AbstractJpaContextProvider`, implement `IdentifierCollector` and implement a set of `MappingProvider` for each type of entity. To provide implementations of `MappingProvider` you should create update methods in your MapStruct mappers. It utilize `CompositeContext` which can incorporate any number of contexts as a composite.

All of this can be managed by some DI container like Spring or Guice.

**Mapping facade as Spring service:**
 
```java
@Service
@RequiredArgsConstructor
final class MapperFacadeImpl implements MapperFacade {

  private final PetMapper petMapper;
  private final MapStructContextProvider<CompositeContext> contextProvider;

  @Override
  public PetJPA map(Pet pet) {
    return petMapper.map(pet, contextProvider.createNewContext());
  }

  @Override
  public Pet map(PetJPA jpa) {
    return petMapper.map(jpa, contextProvider.createNewContext());
  }
}
```

**Context provider as Spring service:**

```java
@Service
@RequiredArgsConstructor
final class CompositeContextProvider extends AbstractCompositeContextProvider {

  @Getter
  private final JpaMappingContextFactory jpaMappingContextFactory;
  private final List<MappingProvider<?, ?, ?>> mappingProviders;
  @Getter
  private final IdentifierCollector identifierCollector;

  @Override
  protected Iterable<MappingProvider> getMappingProviders() {
    return Collections.unmodifiableSet(mappingProviders);
  }

}
```

**Example mapping provider for Pet as Spring service:**

```java
@Service
@RequiredArgsConstructor
final class PetMappingProvider implements MappingProvider<Pet, PetJPA, CompositeContext> {

  private final PetMapper petMapper;

  @Override
  public Mapping<Pet, PetJPA, CompositeContext> provide() {
    return AbstractCompositeContextMapping.mapperFor(
      Pet.class, PetJPA.class,
      petMapper::updateFromPet
    );
  }
}
```

**Identifier collector implementation as Spring service:**

```java
@Service
final class IdentifierCollectorImpl implements IdentifierCollector {
  @Override
  public Optional<Object> getIdentifierFromSource(Object source) {
    if (source instanceof AbstractEntity) {
      AbstractEntity entity = AbstractEntity.class.cast(source);
      return Optional.ofNullable(
        entity.getReference()
      );
    }
    return Optional.empty();
  }
}
```

**HINT:** Complete working example in Spring can be seen in [coi-gov-pl/spring-clean-architecture hibernate module](https://github.com/coi-gov-pl/spring-clean-architecture/tree/develop/pets/persistence-hibernate/src/main/java/pl/gov/coi/cleanarchitecture/example/spring/pets/persistence/hibernate/mapper)
 
**HINT:** An example for Guice can be seen in this repository in test packages.

### N+1 problem solution via special uninitialized collection classes

The N+1 problem is wide known and prominent problem when dealing with JPA witch utilizes lazy loading of data. Solution to this is that developers should fetch only data that they will need (for ex.: using `JOIN FETCH` in JPQL). In many cases that is not enough. It easy to slip some loop when dealing with couple of records.

My solution is to detect that object is not loaded fully and provide a stub that will fail fast if data is not loaded and been tried to be used by other developer. To do that simple use `Uninitialized*` classes provided. There are `UninitializedList`, `UninitializedSet`, and `UninitializedMap`.

```java
@Mapper
interface PetMapper {
  // [..]
  default List<Pet> petJPASetToPetList(Set<PetJPA> set,
                                       @Context CompositeContext context) {
    if (!Hibernate.isInitialized(set)) {
      return new UninitializedList<>(PetJPA.class);
    }
    return set.stream()
      .map(j -> map(j, context))
      .collect(Collectors.toList());
  }
  // [..]
}
```

**Disclaimer:** In future we plan to provide an automatic solution using dynamic proxy objects.

## Dependencies

 * Java >= 8
 * [MapStruct JDK8](https://github.com/mapstruct/mapstruct/tree/master/core-jdk8) >= 1.2.0
 * [EID Exceptions](https://github.com/wavesoftware/java-eid-exceptions) library 

### Contributing

Contributions are welcome!

To contribute, follow the standard [git flow](http://danielkummer.github.io/git-flow-cheatsheet/) of:

1. Fork it
1. Create your feature branch (`git checkout -b feature/my-new-feature`)
1. Commit your changes (`git commit -am 'Add some feature'`)
1. Push to the branch (`git push origin feature/my-new-feature`)
1. Create new Pull Request

Even if you can't contribute code, if you have an idea for an improvement please open an [issue](https://github.com/wavesoftware/java-mapstruct-jpa/issues).
