package at.tuwien.telemedizin.dermadoc.app.service_test_package.rest.listener;

import at.tuwien.telemedizin.dermadoc.entities.rest.Error;

/**
 * TODO
 */
public interface RestListener<TRequest> {

    void onRequestComplete(TRequest requestResult);

    void onError(Error error);
}
