package pl.wavesoftware.utils.mapstruct.jpa;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

import javax.annotation.Nullable;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A type to be used as {@link Context} parameter to track cycles in graphs.
 * <p>
 * Depending on the actual use case, the two methods below could also be changed to only accept certain argument types,
 * e.g. base classes of graph nodes, avoiding the need to capture any other objects that wouldn't necessarily result in
 * cycles.
 *
 * @author Andreas Gudian
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-04-12
 */
public final class CyclicGraphContext implements MapStructContext {

  private Map<Object, Object> knownInstances = new IdentityHashMap<>();

  @Override
  @Nullable
  @BeforeMapping
  public <T> T getMappedInstance(Object source,
                                 @TargetType Class<T> targetType) {
    return targetType.cast(knownInstances.get( source ));
  }

  @Override
  @BeforeMapping
  public void storeMappedInstance(Object source,
                                  @MappingTarget Object target) {
    knownInstances.put(source, target);
  }

  @Override
  public <T> Optional<T> getMappedInstanceOptional(Object source,
                                                   @TargetType Class<T> targetType) {
    return Optional.ofNullable(
      getMappedInstance(source, targetType)
    );
  }
}
