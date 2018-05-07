package pl.wavesoftware.utils.mapstruct.jpa.collection;

import javax.persistence.PersistenceException;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-02
 */
public final class LazyInitializationException extends PersistenceException {

  private static final long serialVersionUID = 20180503145842L;

  /**
   * Constructs a new <code>LazyInitializationException</code> exception with the
   * specified detail message.
   *
   * @param message
   *            the detail message.
   */
  LazyInitializationException(String message) {
    super(message);
  }
}
