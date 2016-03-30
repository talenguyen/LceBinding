/**
 * LceBinding
 * <p/>
 * Created by Giang Nguyen on 3/2/16.
 */

package vn.tale.lcebinding;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import rx.functions.Action1;

/**
 * Base Loading-Content-Error pattern for bind-able in Data Binding.
 */
public class BindableLce extends LoadingContentError {

  private final ObservableBoolean loading = new ObservableBoolean();
  private final ObservableBoolean content = new ObservableBoolean();
  private final ObservableBoolean error = new ObservableBoolean();
  private final ObservableField<String> errorMessage = new ObservableField<>();
  private final ObservableField<String> lightError = new ObservableField<>();

  public BindableLce(ErrorMessageProvider errorMessageProvider) {
    super(errorMessageProvider);
    isLoading().subscribe(new Action1<Boolean>() {
      @Override public void call(Boolean loading) {
        BindableLce.this.loading.set(loading);
      }
    });
    isShowContent().subscribe(new Action1<Boolean>() {
      @Override public void call(Boolean content) {
        BindableLce.this.content.set(content);
      }
    });
    isError().subscribe(new Action1<Boolean>() {
      @Override public void call(Boolean error) {
        BindableLce.this.error.set(error);
      }
    });
    errorMessage().subscribe(new Action1<String>() {
      @Override public void call(String message) {
        BindableLce.this.errorMessage.set(message);
      }
    });
    lightError().subscribe(new Action1<String>() {
      @Override public void call(String message) {
        BindableLce.this.lightError.set(message);
      }
    });
  }

  public ObservableBoolean getLoading() {
    return loading;
  }

  public ObservableBoolean getContent() {
    return content;
  }

  public ObservableBoolean getError() {
    return error;
  }

  public ObservableField<String> getErrorMessage() {
    return errorMessage;
  }

  public ObservableField<String> getLightError() {
    return lightError;
  }
}
