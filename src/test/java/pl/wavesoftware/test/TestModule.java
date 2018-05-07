package pl.wavesoftware.test;

import com.google.inject.Binder;
import com.google.inject.Module;
import pl.wavesoftware.test.mapper.MapperModule;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
final class TestModule implements Module {
  @Override
  public void configure(Binder binder) {
    binder.install(new MapperModule());
  }
}
