package pl.wavesoftware.test.mapper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.wavesoftware.test.entity.Toy;
import pl.wavesoftware.test.jpa.ToyJPA;
import pl.wavesoftware.utils.mapstruct.jpa.CompositeContext;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 07.05.18
 */
public class ToyMappingTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testAccept() {
    // given
    ToyMapping mapping = new ToyMapping(Toy.class, ToyJPA.class, CompositeContext.class);
    Toy toy = new Toy();
    ToyJPA jpa = new ToyJPA();
    CompositeContext context = new CompositeContext();

    // then
    thrown.expect(UnsupportedOperationException.class);

    // when
    mapping.accept(toy, jpa, context);
  }
}
