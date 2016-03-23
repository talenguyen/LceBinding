package vn.tale.lceebinding;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import rx.observers.TestSubscriber;

/**
 * RxRepository
 * <p/>
 * Created by Giang Nguyen on 3/1/16.
 */
public class LceeViewModelTest {

  private LceeViewModel lceeViewModel;
  private ErrorMessageProvider errorMessageProvider;

  @Before public void setUp() throws Exception {
    errorMessageProvider = Mockito.mock(ErrorMessageProvider.class);
    lceeViewModel = new LceeViewModel(errorMessageProvider);
  }

  @Test public void testLoading_showHideLoading_shouldOnlyReceiveChanges() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isLoading().subscribe(testSubscriber);
    lceeViewModel.showLoading();
    lceeViewModel.hideLoading();
    lceeViewModel.hideLoading();
    lceeViewModel.showLoading();
    lceeViewModel.showLoading();
    testSubscriber.assertValues(true, false, true);
  }

  @Test public void testLoading_showLoading_shouldNotShowContent() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isShowContent().subscribe(testSubscriber);
    lceeViewModel.showLoading();
    testSubscriber.assertValue(false);
  }

  @Test public void testLoading_showLoading_shouldNotShowError() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isError().subscribe(testSubscriber);
    lceeViewModel.showLoading();
    testSubscriber.assertValue(false);
  }

  @Test public void testLoading_showLoading_shouldNotShowEmpty() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isEmpty().subscribe(testSubscriber);
    lceeViewModel.showLoading();
    testSubscriber.assertValue(false);
  }

  @Test public void testLoading_showLoading_shouldNotShowLightError() throws Exception {
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    lceeViewModel.lightError().subscribe(testSubscriber);
    lceeViewModel.showLoading();
    testSubscriber.assertNoValues();
  }

  @Test public void testContent_showHideContent_shouldOnlyReceiveChanges() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isShowContent().subscribe(testSubscriber);
    lceeViewModel.showContent();
    lceeViewModel.hideContent();
    lceeViewModel.hideContent();
    lceeViewModel.showContent();
    lceeViewModel.showContent();
    testSubscriber.assertValues(false, true, false, true);
  }

  @Test public void testContent_showContentMultiple_shouldShowContentTwo() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isShowContent().subscribe(testSubscriber);
    lceeViewModel.showContent();
    lceeViewModel.showContent();
    lceeViewModel.showContent();
    testSubscriber.assertValues(false, true); // The first false is initialize value.
  }

  @Test public void testContent_showContent_shouldHideLoading() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isLoading().subscribe(testSubscriber);
    lceeViewModel.showContent();
    testSubscriber.assertValue(false);
  }

  @Test public void testContent_showContent_shouldHideError() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isError().subscribe(testSubscriber);
    lceeViewModel.showContent();
    testSubscriber.assertValue(false);
  }

  @Test public void testContent_showContent_shouldHideEmpty() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isEmpty().subscribe(testSubscriber);
    lceeViewModel.showContent();
    testSubscriber.assertValue(false);
  }

  @Test public void testContent_showContent_shouldNotShowLightError() throws Exception {
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    lceeViewModel.lightError().subscribe(testSubscriber);
    lceeViewModel.showContent();
    testSubscriber.assertNoValues();
  }

  @Test public void testContent_showContent_shouldNotShowErrorMessage() throws Exception {
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    lceeViewModel.errorMessage().subscribe(testSubscriber);
    lceeViewModel.showContent();
    testSubscriber.assertNoValues();
  }

  @Test public void testContent_hideContent_shouldHideContent() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isShowContent().subscribe(testSubscriber);
    lceeViewModel.hideContent();
    testSubscriber.assertValue(false);
  }

  @Test public void testEmpty_showHideEmpty_shouldOnlyReceiveChanges() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isEmpty().subscribe(testSubscriber);
    lceeViewModel.showEmpty();
    lceeViewModel.showEmpty();
    lceeViewModel.hideEmpty();
    lceeViewModel.hideEmpty();
    lceeViewModel.showEmpty();
    testSubscriber.assertValues(true, false, true);
  }

  @Test public void testEmpty_showEmpty_shouldHideLoading() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isLoading().subscribe(testSubscriber);
    lceeViewModel.showEmpty();
    testSubscriber.assertValue(false);
  }

  @Test public void testEmpty_showEmpty_shouldHideError() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isError().subscribe(testSubscriber);
    lceeViewModel.showEmpty();
    testSubscriber.assertValue(false);
  }

  @Test public void testEmpty_showEmpty_shouldHideContent() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isShowContent().subscribe(testSubscriber);
    lceeViewModel.showEmpty();
    testSubscriber.assertValue(false);
  }

  @Test public void testEmpty_showEmpty_shouldNotShowLightError() throws Exception {
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    lceeViewModel.lightError().subscribe(testSubscriber);
    lceeViewModel.showEmpty();
    testSubscriber.assertNoValues();
  }

  @Test public void testEmpty_showEmpty_shouldNotShowErrorMessage() throws Exception {
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    lceeViewModel.errorMessage().subscribe(testSubscriber);
    lceeViewModel.showEmpty();
    testSubscriber.assertNoValues();
  }

  @Test public void testError_showHideError_shouldOnlyReceiveChanges() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isError().subscribe(testSubscriber);
    lceeViewModel.showError(new RuntimeException());
    lceeViewModel.hideError();
    lceeViewModel.hideError();
    lceeViewModel.showError(new RuntimeException());
    lceeViewModel.showError(new RuntimeException());
    testSubscriber.assertValues(true, false, true);
  }

  @Test public void testError_showError_shouldHideLoading() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isLoading().subscribe(testSubscriber);
    lceeViewModel.showError(new RuntimeException());
    testSubscriber.assertValue(false);
  }

  @Test public void testError_showError_shouldHideEmpty() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isEmpty().subscribe(testSubscriber);
    lceeViewModel.showError(new RuntimeException());
    testSubscriber.assertValue(false);
  }

  @Test public void testError_showErrorWhenContentIsNotShowing_shouldShowError() throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isError().subscribe(testSubscriber);
    lceeViewModel.showError(new RuntimeException());
    testSubscriber.assertValue(true);
  }

  @Test public void testError_showErrorWhenContentIsNotShowing_shouldShowErrorMessage()
      throws Exception {
    final String expectedErrorMessage = "Expected error message";
    Mockito.when(errorMessageProvider.getErrorMessage(Mockito.any(Throwable.class)))
        .thenReturn(expectedErrorMessage);
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    lceeViewModel.errorMessage().subscribe(testSubscriber);
    lceeViewModel.showError(new RuntimeException());
    testSubscriber.assertValue(expectedErrorMessage);
  }

  @Test public void testError_showErrorWhenContentIsNotShowing_shouldHideContent()
      throws Exception {
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isShowContent().subscribe(testSubscriber);
    lceeViewModel.showError(new RuntimeException());
    testSubscriber.assertValue(false);
  }

  @Test public void testError_showErrorWhenContentIsShowing_shouldNotShowError() throws Exception {
    lceeViewModel.showContent();
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isError().subscribe(testSubscriber);
    lceeViewModel.showError(new RuntimeException());
    testSubscriber.assertValues(false);
  }

  @Test public void testError_showErrorWhenContentIsShowing_shouldNotHideContent()
      throws Exception {
    lceeViewModel.showContent();
    final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    lceeViewModel.isShowContent().subscribe(testSubscriber);
    lceeViewModel.showError(new RuntimeException());
    testSubscriber.assertValues(true);
  }

  @Test public void testError_showErrorWhenContentIsShowing_shouldShowLightError()
      throws Exception {
    lceeViewModel.showContent();
    final String expectedLightErrorMessage = "Expected light error";
    Mockito.when(errorMessageProvider.getLightErrorMessage(Mockito.any(Throwable.class)))
        .thenReturn(expectedLightErrorMessage);
    final TestSubscriber<String> testSubscriber = new TestSubscriber<>();
    lceeViewModel.lightError().subscribe(testSubscriber);
    lceeViewModel.showError(new RuntimeException());
    testSubscriber.assertValues(expectedLightErrorMessage);
  }
}