package vn.tale.lcebinding;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
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

    lce = new LoadingContentError(errorMessageProvider);
    lceBinding = new LceBinding();
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
    verify(contentView).hide();
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

  @Test public void testUnbind_shouldNotReceiveLoadingEvent() throws Exception {
    lceBinding.bind(lce, loadingView, contentView, errorView);
    lceBinding.unbind();

    lce.showLoading();
    verify(loadingView, never()).show();

    lce.hideLoading();
    verify(loadingView, never()).hide();
  }

  @Test public void testUnbind_shouldNotReceiveContentEvent() throws Exception {
    lceBinding.bind(lce, loadingView, contentView, errorView);
    lceBinding.unbind();

    lce.showContent();
    verify(contentView, never()).show();

    lce.hideContent();
    verify(contentView, never()).hide();
  }

  @Test public void testUnbind_shouldNotReceiveErrorEvent() throws Exception {
    lceBinding.bind(lce, loadingView, contentView, errorView);
    lceBinding.unbind();

    lce.showError(new RuntimeException());
    verify(errorView, never()).show();

    lce.hideError();
    verify(errorView, never()).hide();
  }

  @Test public void testUnbind_beforeBind_shouldNotCrash() throws Exception {
    lceBinding.unbind();
  }

  @Test public void testUnbind_beforeBind_shouldNotReceiveLoadingEvent() throws Exception {
    lceBinding.bind(lce, loadingView, contentView, errorView);
    lceBinding.unbind();

    lce.showLoading();
    verify(loadingView, never()).show();

    lce.hideLoading();
    verify(loadingView, never()).hide();
  }

  @Test public void testUnbind_beforeBind_shouldNotReceiveContentEvent() throws Exception {
    lceBinding.bind(lce, loadingView, contentView, errorView);
    lceBinding.unbind();

    lce.showContent();
    verify(contentView, never()).show();

    lce.hideContent();
    verify(contentView, never()).hide();
  }

  @Test public void testUnbind_beforeBind_shouldNotReceiveErrorEvent() throws Exception {
    lceBinding.bind(lce, loadingView, contentView, errorView);
    lceBinding.unbind();

    lce.showError(new RuntimeException());
    verify(errorView, never()).show();

    lce.hideError();
    verify(errorView, never()).hide();
  }
}