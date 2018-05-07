package pl.wavesoftware.lang;

import org.junit.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-06
 */
public class TriConsumerTest {

  private static final int HEX_RADIX = 16;

  @Test
  public void testAndThen() throws NoSuchAlgorithmException {
    // given
    StringBuilder sb = new StringBuilder();
    TriConsumer<String, Boolean, Long> consumer = (string, aBoolean, aLong) -> {
      sb.append("string => ").append(string).append(", ");
      sb.append("bool => ").append(aBoolean).append(", ");
      sb.append("long => ").append(aLong);
    };
    MessageDigest md = MessageDigest.getInstance("SHA-256");

    // when
    consumer
      .andThen((s, aBool, aLong) -> {
        md.update(s.getBytes(StandardCharsets.UTF_8));
        md.update(aBool.toString().getBytes(StandardCharsets.UTF_8));
        md.update(aLong.toString().getBytes(StandardCharsets.UTF_8));
        sb.append(", digest => ")
          .append(
            new BigInteger(1, md.digest()).toString(HEX_RADIX)
          );
      })
      .accept("Alice has a cat", true, 0L);

    // then
    assertThat(sb)
      .isEqualToIgnoringNewLines(
        "string => Alice has a cat, bool => true, long => 0, digest => " +
          "59283a245f0ef51936819eb4e80fb2f9528d4ddbd022badf45c56f1e8073e8aa"
      );
  }
}
