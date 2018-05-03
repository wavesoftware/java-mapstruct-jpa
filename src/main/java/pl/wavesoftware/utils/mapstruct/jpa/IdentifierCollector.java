package pl.wavesoftware.utils.mapstruct.jpa;

import java.util.Optional;

/**
 * Identifier collector is responsible for collecting identifier from object. If it can't
 * fetch an identifier it should return {@link Optional#empty()} value.
 * <p>
 *
 * Example:
 * <pre>
 * final class LongIdentifierCollector implements IdentifierCollector {
 *     public Optional&lt;Object&gt; getIdentifierFromSource(Object source) {
 *         if (source instanceof AbstractEntity) {
 *             return Optional.ofNullable(
 *                 AbstractEntity.class.cast(source).getId()
 *             );
 *         }
 *         return Optional.empty();
 *     }
 * }
 * </pre>
 *
 * @author <a href="krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-02
 */
public interface IdentifierCollector {
  /**
   * Tries to collect an identifier of source object. An identifier can be any
   * object, specific interface is not needed.
   *
   * @param source a source object to collect identifier from
   * @return an optional identifier, if not found {@link Optional#empty()} is returned.
   */
  Optional<Object> getIdentifierFromSource(Object source);
}
