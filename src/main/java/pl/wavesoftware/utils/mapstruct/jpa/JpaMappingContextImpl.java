package pl.wavesoftware.utils.mapstruct.jpa;

import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 24.04.18
 */
@RequiredArgsConstructor
final class JpaMappingContextImpl implements JpaMappingContext {
  private final Supplier<EntityManager> entityManager;
  private final Supplier<? extends StoringMappingContext> storingMappingContext;
  private final Mappings<?> mappings;
  private final IdentifierCollector identifierCollector;
  private final Set<Integer> mappedInstances = new HashSet<>();

  @Override
  public <T> Optional<T> getMappedInstanceOptional(Object source,
                                                   Class<T> targetType) {
    if (isJpaManagedEntity(targetType)) {
      Optional<Object> reference = identifierCollector.getIdentifierFromSource(source);
      if (reference.isPresent()) {
        if (isBeingMapped(source, targetType)) {
          return Optional.empty();
        }
        markAsBeingMapped(source, targetType);
      }
      Optional<T> managed = reference.map(ref -> load(ref, targetType));
      managed.ifPresent(m -> updateFromSource(m, source));
      return managed;
    }
    return Optional.empty();
  }

  private static <T> boolean isJpaManagedEntity(Class<T> targetType) {
    return targetType.isAnnotationPresent(Entity.class)
      || targetType.isAnnotationPresent(Embeddable.class)
      || targetType.isAnnotationPresent(MappedSuperclass.class);
  }

  private static <T> Integer keyOf(Object source, Class<T> targetType) {
    return System.identityHashCode(source) +
      System.identityHashCode(targetType);
  }

  private <T> void markAsBeingMapped(Object source,
                                     Class<T> targetType) {
    Integer key = keyOf(source, targetType);
    mappedInstances.add(key);
  }

  private <T> boolean isBeingMapped(Object source,
                                    Class<T> targetType) {
    Integer key = keyOf(source, targetType);
    return mappedInstances.contains(key);
  }

  private <T> T load(Object identifier,
                     Class<T> targetType) {
    return entityManager
      .get()
      .find(targetType, identifier);
  }

  private <T> void updateFromSource(T managed,
                                    Object source) {
    updateFromSourceTyped(source, managed);
  }

  @SuppressWarnings("unchecked")
  private <I, O, C> void updateFromSourceTyped(I source, O managed) {
    Class<I> sourceClass = (Class<I>) source.getClass();
    Class<O> targetClass = (Class<O>) managed.getClass();
    Mappings<C> maps = (Mappings<C>) mappings;
    Mapping<I,O,C> mapping = maps.getMapping(sourceClass, targetClass);
    C context = mapping
      .getContextClass()
      .cast(storingMappingContext.get());
    mapping.accept(source, managed, context);
  }
}
