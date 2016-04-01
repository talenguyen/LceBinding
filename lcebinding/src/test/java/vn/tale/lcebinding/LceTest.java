package vn.tale.lcebinding;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import rx.observers.TestSubscriber;

/**
 * LceBinding
 * <p/>
 * Created by Giang Nguyen on 3/1/16.
 */
public class LceTest {

  private LoadingContentError lce;
  private ErrorMessageProvider errorMessageProvider;

  @Before public void setUp() throws Exception {
    errorMessageProvider = Mockito.mock(ErrorMessageProvider.class);
    lce = new LoadingContentError(errorMessageProvider);
  }

  @Test public void testLoading_showHideLoading_shouldOnlyReceiveChanges() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isLoading().subscribe(testSubscriber);
    lce.showLoading();
    lce.hideLoading();
    lce.hideLoading();
    lce.showLoading();
    lce.showLoading();
    testSubscriber.assertValues(true, false, true);
  }

  @Test public void testLoading_showLoading_shouldNotShowContent() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isShowContent().subscribe(testSubscriber);
    lce.showLoading();
    testSubscriber.assertValue(false);
  }

  @Test public void testLoading_showLoading_shouldNotShowError() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isError().subscribe(testSubscriber);
    lce.showLoading();
    testSubscriber.assertValue(false);
  }

  @Test public void testContent_showHideContent_shouldOnlyReceiveChanges() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isShowContent().subscribe(testSubscriber);
    lce.showContent();
    lce.hideContent();
    lce.hideContent();
    lce.showContent();
    lce.showContent();
    testSubscriber.assertValues(true, false, true);
  }

  @Test public void testContent_showContentMultiple_shouldShowContentTwo() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isShowContent().subscribe(testSubscriber);
    lce.showContent();
    lce.showContent();
    lce.showContent();
    testSubscriber.assertValues(true);
  }

  @Test public void testContent_showContent_shouldHideLoading() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isLoading().subscribe(testSubscriber);
    lce.showContent();
    testSubscriber.assertValue(false);
  }

  @Test public void testContent_showContent_shouldHideError() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isError().subscribe(testSubscriber);
    lce.showContent();
    testSubscriber.assertValue(false);
  }

  @Test public void testContent_showContent_shouldNotShowErrorMessage() throws Exception {
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    lce.errorMessage().subscribe(testSubscriber);
    lce.showContent();
    testSubscriber.assertNoValues();
  }

  @Test public void testContent_hideContent_shouldHideContent() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isShowContent().subscribe(testSubscriber);
    lce.hideContent();
    testSubscriber.assertValue(false);
  }

  @Test public void testError_showHideError_shouldOnlyReceiveChanges() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isError().subscribe(testSubscriber);
    lce.showError(new RuntimeException());
    lce.hideError();
    lce.hideError();
    lce.showError(new RuntimeException());
    lce.showError(new RuntimeException());
    testSubscriber.assertValues(true, false, true);
  }

  @Test public void testError_showError_shouldHideLoading() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isLoading().subscribe(testSubscriber);
    lce.showError(new RuntimeException());
    testSubscriber.assertValue(false);
  }

  @Test public void testError_showError_shouldShowError() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isError().subscribe(testSubscriber);
    lce.showError(new RuntimeException());
    testSubscriber.assertValue(true);
  }

  @Test public void testError_showError_shouldShowErrorMessage()
      throws Exception {
    final String expectedErrorMessage = "Expected error message";
    Mockito.when(errorMessageProvider.getErrorMessage(Mockito.any(Throwable.class)))
        .thenReturn(expectedErrorMessage);
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    lce.errorMessage().subscribe(testSubscriber);
    lce.showError(new RuntimeException());
    testSubscriber.assertValue(expectedErrorMessage);
  }

  @Test public void testError_showError_shouldHideContent()
      throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lce.isShowContent().subscribe(testSubscriber);
    lce.showError(new RuntimeException());
    testSubscriber.assertValue(false);
  }

}