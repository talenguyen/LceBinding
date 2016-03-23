/**
 * RxRepository
 * <p/>
 * Created by Giang Nguyen on 3/2/16.
 */

package vn.tale.lceebinding;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import rx.functions.Action1;

/**
 * Base Loading-Content-Error-Empty pattern for bind-able in Data Binding.
 */
public class BindableLceeViewModel extends LceeViewModel {

  private final ObservableBoolean loading = new ObservableBoolean();
  private final ObservableBoolean content = new ObservableBoolean();
  private final ObservableBoolean empty = new ObservableBoolean();
  private final ObservableBoolean error = new ObservableBoolean();
  private final ObservableField<String> errorMessage = new ObservableField<>();
  private final ObservableField<String> lightError = new ObservableField<>();

  public BindableLceeViewModel(ErrorMessageProvider errorMessageProvider) {
    super(errorMessageProvider);
    isLoading().subscribe(new Action1<Boolean>() {
      @Override public void call(Boolean loading) {
        BindableLceeViewModel.this.loading.set(loading);
      }
    });
    isShowContent().subscribe(new Action1<Boolean>() {
      @Override public void call(Boolean content) {
        BindableLceeViewModel.this.content.set(content);
      }
    });
    isEmpty().subscribe(new Action1<Boolean>() {
      @Override public void call(Boolean empty) {
        BindableLceeViewModel.this.empty.set(empty);
      }
    });
    isError().subscribe(new Action1<Boolean>() {
      @Override public void call(Boolean error) {
        BindableLceeViewModel.this.error.set(error);
      }
    });
    errorMessage().subscribe(new Action1<String>() {
      @Override public void call(String message) {
        BindableLceeViewModel.this.errorMessage.set(message);
      }
    });
    lightError().subscribe(new Action1<String>() {
      @Override public void call(String message) {
        BindableLceeViewModel.this.lightError.set(message);
      }
    });
  }

  public ObservableBoolean getLoading() {
    return loading;
  }

  public ObservableBoolean getContent() {
    return content;
  }

  public ObservableBoolean getEmpty() {
    return empty;
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
