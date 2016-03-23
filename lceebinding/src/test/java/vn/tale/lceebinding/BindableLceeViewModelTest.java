package vn.tale.lceebinding;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * RxRepository
 * Created by Giang Nguyen on 3/2/16.
 */
public class BindableLceeViewModelTest {

  private BindableLceeViewModel bindableLceeViewModel;
  private ErrorMessageProvider errorMessageProvider;

  @Before public void setUp() throws Exception {
    errorMessageProvider = Mockito.mock(ErrorMessageProvider.class);
    bindableLceeViewModel = new BindableLceeViewModel(errorMessageProvider);
  }

  @Test public void testLoadingField_showLoading_shouldBeTrue() throws Exception {
    bindableLceeViewModel.showLoading();
    Assert.assertTrue(bindableLceeViewModel.getLoading().get());
  }

  @Test public void testLoadingField_hideLoading_shouldBeFalse() throws Exception {
    bindableLceeViewModel.hideLoading();
    Assert.assertFalse(bindableLceeViewModel.getLoading().get());
  }

  @Test public void testContentField_showContent_shouldBeTrue() throws Exception {
    bindableLceeViewModel.showContent();
    Assert.assertTrue(bindableLceeViewModel.getContent().get());
  }

  @Test public void testContentField_hideContent_shouldBeFalse() throws Exception {
    bindableLceeViewModel.hideContent();
    Assert.assertFalse(bindableLceeViewModel.getContent().get());
  }

  @Test public void testEmptyField_showEmpty_shouldBeTrue() throws Exception {
    bindableLceeViewModel.showEmpty();
    Assert.assertTrue(bindableLceeViewModel.getEmpty().get());
  }

  @Test public void testEmptyField_hideEmpty_shouldBeFalse() throws Exception {
    bindableLceeViewModel.hideEmpty();
    Assert.assertFalse(bindableLceeViewModel.getEmpty().get());
  }

  @Test public void testErrorField_showErrorWhenContentIsNotShowing_shouldShowError()
      throws Exception {
    bindableLceeViewModel.hideContent();
    bindableLceeViewModel.showError(new RuntimeException());
    Assert.assertTrue(bindableLceeViewModel.getError().get());
  }

  @Test public void testErrorField_showErrorWhenContentIsNotShowing_shouldShowErrorMessage()
      throws Exception {
    final String expectedErrorMessage = "Expected error message";
    Mockito.when(errorMessageProvider.getErrorMessage(Mockito.any(Throwable.class)))
        .thenReturn(expectedErrorMessage);
    bindableLceeViewModel.hideContent();
    bindableLceeViewModel.showError(new RuntimeException());
    Assert.assertEquals(expectedErrorMessage, bindableLceeViewModel.getErrorMessage().get());
  }

  @Test public void testErrorField_hideError_shouldBeFalse() throws Exception {
    bindableLceeViewModel.hideError();
    Assert.assertFalse(bindableLceeViewModel.getError().get());
  }

  @Test public void testErrorField_showErrorWhenContentIsShowing_shouldShowLightError()
      throws Exception {
    bindableLceeViewModel.showContent();
    final String expectedLightErrorMessage = "Expected light error";
    Mockito.when(errorMessageProvider.getLightErrorMessage(Mockito.any(Throwable.class)))
        .thenReturn(expectedLightErrorMessage);
    bindableLceeViewModel.showError(new RuntimeException());
    Assert.assertEquals(expectedLightErrorMessage, bindableLceeViewModel.getLightError().get());
  }
}