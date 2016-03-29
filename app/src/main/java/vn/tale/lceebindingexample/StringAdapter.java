/**
 * LceBinding
 *
 * Created by Giang Nguyen on 12/25/15.
 * Copyright (c) 2015 Tale. All rights reserved.
 */

package vn.tale.lceebindingexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.StringVH> {

  private List<String> items = new ArrayList<>();

  @Override public StringVH onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext())
        .inflate(android.R.layout.simple_list_item_1, parent, false);
    return new StringVH(view);
  }

  @Override public void onBindViewHolder(StringVH holder, int position) {
    holder.bind(getItem(position));
  }

  private String getItem(int position) {
    return items.get(position);
  }

  @Override public int getItemCount() {
    return items.size();
  }

  public static class StringVH extends RecyclerView.ViewHolder {

    private final TextView textView;

    public StringVH(View itemView) {
      super(itemView);
      textView = ((TextView) itemView.findViewById(android.R.id.text1));
    }

    public void bind(String item) {
      textView.setText(item);
    }
  }
}