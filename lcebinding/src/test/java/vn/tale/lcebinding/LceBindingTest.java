package vn.tale.lcebinding;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Author giangnguyen. Created on 4/1/16.
 */
public class LceBindingTest {
  @Mock ErrorMessageProvider errorMessageProvider;
  @Mock ShowHideView loadingView;
  @Mock ShowHideView contentView;
  @Mock ErrorView errorView;

  private LceBinding lceBinding;
  private LoadingContentError lce;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    lceBinding = new LceBinding();
    lce = new LoadingContentError(errorMessageProvider);
  }

  @Test public void testBindMethod_showHideLoading() throws Exception {
    lceBinding.bind(lce, loadingView, contentView, errorView);

    lce.showLoading();
    verify(loadingView).show();

    lce.hideLoading();
    verify(loadingView).hide();
  }

  @Test public void testBind_showHideContent() throws Exception {
    lceBinding.bind(lce, loadingView, contentView, errorView);

    lce.showContent();
    verify(contentView).show();

    lce.hideContent();
    verify(contentView, times(2)).hide(); // 2 because the initial state of content is hide.
  }

  @Test public void testBind_showHideError() throws Exception {
    lceBinding.bind(lce, loadingView, contentView, errorView);

    String expectedError = "Expected error";
    when(errorMessageProvider.getErrorMessage(any(Throwable.class))).thenReturn(expectedError);

    lce.showError(new RuntimeException());
    verify(errorView).show();
    verify(errorView).setError(expectedError);

    lce.hideError();
    verify(errorView).hide();
  }

  @Test public void testBind_showHideLightError() throws Exception {
    lceBinding.bind(lce, loadingView, contentView, errorView);

    String expectedError = "Expected error";
    when(errorMessageProvider.getLightErrorMessage(any(Throwable.class))).thenReturn(expectedError);

    lce.showContent();
    lce.showError(new RuntimeException());
    verify(errorView, never()).show();
    verify(errorView, never()).setError(anyString());
    verify(errorView).setLightError(expectedError);
  }
}