package pl.wavesoftware.utils.mapstruct;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * A composite mapping context that can utilize multiple mapping contexts.
 *
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 02.05.18
 */
public final class CompositeMappingContext implements MapstructContext {
  private final List<MappingContext> mappingContexts = new ArrayList<>();

  /**
   * You can pass multiple mapping contexts to be used in this composite mapping context
   *
   * @param mappingContexts a array of mapping contexts
   */
  public CompositeMappingContext(MappingContext... mappingContexts) {
    Collections.addAll(this.mappingContexts, mappingContexts);
  }

  /**
   * A builder interface for {@link CompositeMappingContext}.
   *
   * @return a builder interface
   */
  public static CompositeMappingContextBuilder builder() {
    return new CompositeMappingContextBuilder();
  }

  @Override
  @BeforeMapping
  public void storeMappedInstance(Object source,
                                  @MappingTarget Object target) {
    for (MappingContext mappingContext : mappingContexts) {
      if (mappingContext instanceof StoringMappingContext) {
        StoringMappingContext.class.cast(mappingContext)
          .storeMappedInstance(source, target);
      }
    }
  }

  @Override
  @Nullable
  @BeforeMapping
  public <T> T getMappedInstance(Object source,
                                 @TargetType Class<T> targetType) {
    return getMappedInstanceOptional(source, targetType)
      .orElse(null);
  }

  @Override
  public <T> Optional<T> getMappedInstanceOptional(Object source,
                                                   Class<T> targetType) {
    for (MappingContext mappingContext : mappingContexts) {
      Optional<T> instance = mappingContext.getMappedInstanceOptional(source, targetType);
      if (instance.isPresent()) {
        return instance;
      }
    }
    return Optional.empty();
  }

  private static final class CompositeMappingContextBuilder {
    private final List<MappingContext> mappingContexts = new ArrayList<>();

    public CompositeMappingContextBuilder addContext(MappingContext... mappingContexts) {
      this.mappingContexts.addAll(
        Arrays.asList(mappingContexts)
      );
      return this;
    }

    public CompositeMappingContext build() {
      return new CompositeMappingContext(
        mappingContexts.toArray(new MappingContext[0])
      );
    }
  }
}
