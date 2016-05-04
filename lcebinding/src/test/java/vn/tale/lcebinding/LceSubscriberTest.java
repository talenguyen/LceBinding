package vn.tale.lcebinding;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;

import static org.mockito.Mockito.verify;

/**
 * Created by Giang Nguyen at Tiki on 5/4/16.
 */
public class LceSubscriberTest {

  @Mock LoadingContentError lce;
  private LceSubscriber<Object> lceSubscriber;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    lceSubscriber = new LceSubscriber<>(lce);
  }

  @Test
  public void testSubscribeSuccess() throws Exception {
    Observable.just("success")
        .subscribe(lceSubscriber);
    verify(lce).showLoading();
    verify(lce).showContent();
  }

  @Test
  public void testSubscribeError() throws Exception {
    final RuntimeException error = new RuntimeException();
    Observable.error(error)
        .subscribe(lceSubscriber);
    verify(lce).showLoading();
    verify(lce).showError(error);
  }

  @Test
  public void testSubscribeEmpty() throws Exception {
    Observable.empty()
        .subscribe(lceSubscriber);
    verify(lce).showLoading();
    verify(lce).hideLoading();
  }


}