package at.tuwien.telemedizin.dermadoc.service.exception;

/**
 * Created by Lucas on 25.11.2015.
 */
public class DermadocConversionException extends DermadocException {

    public DermadocConversionException() {
        super("error on converting image!");
    }
}
