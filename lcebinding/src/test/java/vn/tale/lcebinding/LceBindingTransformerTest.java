package vn.tale.lcebinding;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.Subscriber;

import static org.mockito.Mockito.never;

/**
 * Created by Giang Nguyen on 4/4/16.
 */
public class LceBindingTransformerTest {
  @Mock LoadingContentError lce;
  private Subscriber<? super Object> noOpSubscriber = new Subscriber<Object>() {
    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable e) {

    }

    @Override public void onNext(Object o) {

    }
  };

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test public void testOnCreate_shouldNotShowLoading() throws Exception {
    final Observable<String> success = Observable.just("Success");
    success.compose(new LceBindingTransformer<String>(lce));
    Mockito.verify(lce, never()).showLoading();
  }

  @Test public void testSuccess_shouldShowLoadingThenShowContent() throws Exception {
    Observable.just("Success").compose(new LceBindingTransformer<>(lce)).subscribe(noOpSubscriber);

    InOrder order = Mockito.inOrder(lce);
    order.verify(lce).showLoading();
    order.verify(lce).showContent();
  }

  @Test public void testError_shouldShowLoadingThenShowError() throws Exception {
    final RuntimeException exception = new RuntimeException("Error");
    Observable.error(exception).compose(new LceBindingTransformer<>(lce)).subscribe(noOpSubscriber);

    InOrder order = Mockito.inOrder(lce);
    order.verify(lce).showLoading();
    order.verify(lce).showError(exception);
  }
}