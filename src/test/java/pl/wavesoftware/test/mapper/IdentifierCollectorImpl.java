package pl.wavesoftware.test.mapper;

import pl.wavesoftware.test.entity.AbstractEntity;
import pl.wavesoftware.utils.mapstruct.jpa.IdentifierCollector;

import java.util.Optional;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
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
