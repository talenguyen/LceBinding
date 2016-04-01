package vn.tale.lcebinding;

import android.support.annotation.NonNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Author giangnguyen. Created on 4/1/16.
 */
public class LceBindingTest {
  private static final Object OBJECT = new Object();
  private static final Observable<Object> ERROR_STREAM =
      Observable.error(new RuntimeException("Error"));
  private static final Observable<Object> SUCCESS_STREAM = Observable.just(OBJECT);

  @Mock ThreadScheduler threadScheduler;
  @Mock ErrorMessageProvider errorMessageProvider;
  @Mock ShowHideView loadingView;
  @Mock ShowHideView contentView;
  @Mock ErrorView errorView;

  private LceBinding lceBinding;
  private LoadingContentError lce;


  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(threadScheduler.subscribeOn()).thenReturn(Schedulers.immediate());
    when(threadScheduler.observeOn()).thenReturn(Schedulers.immediate());
    lce = new LoadingContentError(errorMessageProvider);
    lceBinding = new LceBinding(lce);
  }

  @Test public void testBindMethod_showHideLoading() throws Exception {
    lceBinding.bind(loadingView, contentView, errorView);

    lce.showLoading();
    verify(loadingView).show();

    lce.hideLoading();
    verify(loadingView).hide();
  }

  @Test public void testBind_showHideContent() throws Exception {
    lceBinding.bind(loadingView, contentView, errorView);

    lce.showContent();
    verify(contentView).show();

    lce.hideContent();
    verify(contentView).hide();
  }

  @Test public void testBind_showHideError() throws Exception {
    lceBinding.bind(loadingView, contentView, errorView);

    String expectedError = "Expected error";
    when(errorMessageProvider.getErrorMessage(any(Throwable.class))).thenReturn(expectedError);

    lce.showError(new RuntimeException());
    verify(errorView).show();
    verify(errorView).setError(expectedError);

    lce.hideError();
    verify(errorView).hide();
  }

  @Test public void testUnbind_showNotReceiveLoadingEvent() throws Exception {
    lceBinding.bind(loadingView, contentView, errorView);
    lceBinding.unbind();

    lce.showLoading();
    verify(loadingView, never()).show();

    lce.hideLoading();
    verify(loadingView, never()).hide();
  }

  @Test public void testUnbind_showNotReceiveContentEvent() throws Exception {
    lceBinding.bind(loadingView, contentView, errorView);
    lceBinding.unbind();

    lce.showContent();
    verify(contentView, never()).show();

    lce.hideContent();
    verify(contentView, never()).hide();
  }

  @Test public void testUnbind_showNotReceiveErrorEvent() throws Exception {
    lceBinding.bind(loadingView, contentView, errorView);
    lceBinding.unbind();

    lce.showError(new RuntimeException());
    verify(errorView, never()).show();

    lce.hideError();
    verify(errorView, never()).hide();
  }



  @Test public void testStart_success_shouldShowThenHideLoading() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isLoading().subscribe(testSubscriber);
    lceBinding.createLceStream(SUCCESS_STREAM, threadScheduler).subscribe();
    testSubscriber.assertValues(true, false);
  }

  @Test public void testStart_success_shouldShowContent() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isShowContent().subscribe(testSubscriber);
    lceBinding.createLceStream(SUCCESS_STREAM, threadScheduler).subscribe();
    testSubscriber.assertValues(false, true);
  }

  @Test public void testStart_success_shouldNotShowError() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isError().subscribe(testSubscriber);
    lceBinding.createLceStream(SUCCESS_STREAM, threadScheduler).subscribe(createNoOpSubscriber());
    testSubscriber.assertValues(false); // The false is for loading.
  }

  @Test public void testStart_error_shouldShowError() throws Exception {
    final String expectedMessage = "Expected message";
    when(errorMessageProvider.getErrorMessage(any(Throwable.class))).thenReturn(expectedMessage);
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    final TestSubscriber<String> errorMessageSubscriber = new TestSubscriber<>();
    lce.errorMessage().subscribe(errorMessageSubscriber);
    lce.isError().subscribe(testSubscriber);
    lceBinding.createLceStream(ERROR_STREAM, threadScheduler).subscribe(createNoOpSubscriber());
    testSubscriber.assertValues(false, true);
    errorMessageSubscriber.assertValues(expectedMessage);
  }

  @NonNull private Subscriber<Object> createNoOpSubscriber() {
    return new Subscriber<Object>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onNext(Object object) {

      }
    };
  }
}