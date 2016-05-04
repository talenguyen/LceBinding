package vn.tale.lcebindingexample;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragmentContainer, new FragmentMain())
          .commit();
    }

    Observable.defer(new Func0<Observable<Object>>() {
      @Override public Observable<Object> call() {
        SystemClock.sleep(3000);
        return Observable.just(null);
      }
    })
        .subscribe(new Subscriber<Object>() {
          @Override public void onCompleted() {
            Log.d(TAG, "onCompleted: ");
          }

          @Override public void onError(Throwable e) {
            Log.e(TAG, "onError: ", e);
          }

          @Override public void onNext(Object aLong) {
            Log.d(TAG, "onNext() called with: " + "aLong = [" + aLong + "]");
          }

          @Override public void onStart() {
            super.onStart();
            Log.d(TAG, "onStart: ");
          }
        });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
