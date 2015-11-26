package at.tuwien.telemedizin.dermadoc.service.rest.listener;

/**
 * TODO
 */
public interface RestListener<TRequest> {

    void onRequestComplete(TRequest requestResult);

    void onError(Error error);
}
