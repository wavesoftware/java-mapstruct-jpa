package pl.wavesoftware.test.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@NoArgsConstructor
public abstract class AbstractEntity {
  @Setter(AccessLevel.PROTECTED)
  @Getter
  private Object reference;
}
