package vn.tale.lceebinding;

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
 * RxRepository
 * <p/>
 * Created by Giang Nguyen on 2/27/16.
 */
public class BaseLceeViewModelTest {
  public static final Object OBJECT = new Object();
  public static final Observable<Object> ERROR_STREAM =
      Observable.error(new RuntimeException("Error"));
  public static final Observable<Object> EMPTY_STREAM = Observable.empty();
  private static final Observable<Object> SUCCESS_STREAM = Observable.just(OBJECT);
  @Mock ThreadScheduler threadScheduler;
  @Mock ErrorMessageProvider errorMessageProvider;
  private BaseLceeViewModel<Object> baseLceeViewModel;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(threadScheduler.subscribeOn()).thenReturn(Schedulers.immediate());
    when(threadScheduler.observeOn()).thenReturn(Schedulers.immediate());
    baseLceeViewModel = new BaseLceeViewModel<>(errorMessageProvider, threadScheduler);
  }

  @Test public void testStar_success_shouldShowThenHideLoading() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    baseLceeViewModel.isLoading().subscribe(testSubscriber);
    baseLceeViewModel.start(SUCCESS_STREAM).subscribe();
    testSubscriber.assertValues(true, false);
  }

  @Test public void testStar_success_shouldShowContent() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    baseLceeViewModel.isShowContent().subscribe(testSubscriber);
    baseLceeViewModel.start(SUCCESS_STREAM).subscribe();
    testSubscriber.assertValues(false, true); // The false is for loading.
  }

  @Test public void testStar_success_shouldNotShowError() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    baseLceeViewModel.isError().subscribe(testSubscriber);
    baseLceeViewModel.start(SUCCESS_STREAM).subscribe(createNoOpSubscriber());
    testSubscriber.assertValues(false); // The false is for loading.
  }

  @Test public void testStar_error_shouldShowError() throws Exception {
    final String expectedMessage = "Expected message";
    when(errorMessageProvider.getErrorMessage(any(Throwable.class))).thenReturn(expectedMessage);
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    final TestSubscriber<String> errorMessageSubscriber = new TestSubscriber<>();
    baseLceeViewModel.errorMessage().subscribe(errorMessageSubscriber);
    baseLceeViewModel.isError().subscribe(testSubscriber);
    baseLceeViewModel.start(ERROR_STREAM).subscribe(createNoOpSubscriber());
    errorMessageSubscriber.assertValues(expectedMessage);
    testSubscriber.assertValues(false, true);
  }

  @Test public void testStar_errorButContentShowing_shouldShowLightError() throws Exception {
    final String expectedMessage = "Expected message";
    when(errorMessageProvider.getLightErrorMessage(any(Throwable.class))).thenReturn(
        expectedMessage);
    // Verify
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    baseLceeViewModel.lightError().subscribe(testSubscriber);
    baseLceeViewModel.showContent();
    baseLceeViewModel.start(ERROR_STREAM).subscribe(createNoOpSubscriber());
    testSubscriber.assertValues(expectedMessage);
  }

  @Test public void testStar_errorButContentShowing_shouldNotShowError() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    baseLceeViewModel.isError().subscribe(testSubscriber);
    baseLceeViewModel.showContent();
    baseLceeViewModel.start(ERROR_STREAM).subscribe(createNoOpSubscriber());
    testSubscriber.assertValues(false); // The false when show loading.
  }

  @Test public void testStar_empty_shouldShowEmpty() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    baseLceeViewModel.isEmpty().subscribe(testSubscriber);
    baseLceeViewModel.start(EMPTY_STREAM).subscribe(createNoOpSubscriber());
    testSubscriber.assertValues(false, true); // The false when show loading.
  }

  @Test public void testStar_emptyButContentShowing_shouldNotShowEmpty() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    baseLceeViewModel.isEmpty().subscribe(testSubscriber);
    baseLceeViewModel.showContent();
    baseLceeViewModel.start(EMPTY_STREAM).subscribe(createNoOpSubscriber());
    testSubscriber.assertValues(false); // The false when show loading.
  }

  @Test public void testStar_emptyButContentShowing_shouldShowContent() throws Exception {
    // Verify
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    baseLceeViewModel.isShowContent().subscribe(testSubscriber);
    baseLceeViewModel.showContent();
    baseLceeViewModel.start(EMPTY_STREAM).subscribe(createNoOpSubscriber());
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