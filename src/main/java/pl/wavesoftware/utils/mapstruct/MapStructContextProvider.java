package pl.wavesoftware.utils.mapstruct;

/**
 * @author <a href="krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-03
 */
public interface MapStructContextProvider<C extends MapStructContext> {
  C createNewContext();
}
