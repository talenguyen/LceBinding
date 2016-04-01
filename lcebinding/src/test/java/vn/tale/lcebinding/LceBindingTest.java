package vn.tale.lcebinding;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

/**
 * Author giangnguyen. Created on 4/1/16.
 */
public class LceBindingTest {
  @Mock ErrorMessageProvider errorMessageProvider;
  @Mock ShowHideView loadingView;
  @Mock ShowHideView contentView;
  @Mock ErrorView errorView;

  private LceBinding lceBinding;

  @Test public void testBindMethod() throws Exception {
    lceBinding = new LceBinding();
    lceBinding.bind(new LoadingContentError(errorMessageProvider), );
  }
}