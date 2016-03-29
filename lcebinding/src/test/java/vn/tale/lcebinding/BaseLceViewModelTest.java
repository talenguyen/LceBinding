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
import static org.mockito.Mockito.when;

/**
 * LceBinding
 * <p/>
 * Created by Giang Nguyen on 2/27/16.
 */
public class BaseLceViewModelTest {
  public static final Object OBJECT = new Object();
  public static final Observable<Object> ERROR_STREAM =
      Observable.error(new RuntimeException("Error"));
  public static final Observable<Object> EMPTY_STREAM = Observable.empty();
  private static final Observable<Object> SUCCESS_STREAM = Observable.just(OBJECT);
  @Mock
  ThreadScheduler threadScheduler;
  @Mock
  ErrorMessageProvider errorMessageProvider;
  private BaseLceViewModel<Object> baseLceeViewModelViewModel;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(threadScheduler.subscribeOn()).thenReturn(Schedulers.immediate());
    when(threadScheduler.observeOn()).thenReturn(Schedulers.immediate());
    baseLceeViewModelViewModel = new BaseLceViewModel<>(errorMessageProvider, threadScheduler);
  }

  @Test public void testStar_success_shouldShowThenHideLoading() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    baseLceeViewModelViewModel.isLoading().subscribe(testSubscriber);
    baseLceeViewModelViewModel.start(SUCCESS_STREAM).subscribe();
    testSubscriber.assertValues(true, false);
  }

  @Test public void testStar_success_shouldShowContent() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    baseLceeViewModelViewModel.isShowContent().subscribe(testSubscriber);
    baseLceeViewModelViewModel.start(SUCCESS_STREAM).subscribe();
    testSubscriber.assertValues(false, true); // The false is for loading.
  }

  @Test public void testStar_success_shouldNotShowError() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    baseLceeViewModelViewModel.isError().subscribe(testSubscriber);
    baseLceeViewModelViewModel.start(SUCCESS_STREAM).subscribe(createNoOpSubscriber());
    testSubscriber.assertValues(false); // The false is for loading.
  }

  @Test public void testStar_error_shouldShowError() throws Exception {
    final String expectedMessage = "Expected message";
    when(errorMessageProvider.getErrorMessage(any(Throwable.class))).thenReturn(expectedMessage);
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    final TestSubscriber<String> errorMessageSubscriber = new TestSubscriber<>();
    baseLceeViewModelViewModel.errorMessage().subscribe(errorMessageSubscriber);
    baseLceeViewModelViewModel.isError().subscribe(testSubscriber);
    baseLceeViewModelViewModel.start(ERROR_STREAM).subscribe(createNoOpSubscriber());
    errorMessageSubscriber.assertValues(expectedMessage);
    testSubscriber.assertValues(false, true);
  }

  @Test public void testStar_errorButContentShowing_shouldShowLightError() throws Exception {
    final String expectedMessage = "Expected message";
    when(errorMessageProvider.getLightErrorMessage(any(Throwable.class))).thenReturn(
        expectedMessage);
    // Verify
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    baseLceeViewModelViewModel.lightError().subscribe(testSubscriber);
    baseLceeViewModelViewModel.showContent();
    baseLceeViewModelViewModel.start(ERROR_STREAM).subscribe(createNoOpSubscriber());
    testSubscriber.assertValues(expectedMessage);
  }

  @Test public void testStar_errorButContentShowing_shouldNotShowError() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    baseLceeViewModelViewModel.isError().subscribe(testSubscriber);
    baseLceeViewModelViewModel.showContent();
    baseLceeViewModelViewModel.start(ERROR_STREAM).subscribe(createNoOpSubscriber());
    testSubscriber.assertValues(false); // The false when show loading.
  }

  @Test public void testStar_emptyButContentShowing_shouldShowContent() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    baseLceeViewModelViewModel.isShowContent().subscribe(testSubscriber);
    baseLceeViewModelViewModel.showContent();
    baseLceeViewModelViewModel.start(EMPTY_STREAM).subscribe(createNoOpSubscriber());
    testSubscriber.assertValues(false, true); // The first false is initial value.
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