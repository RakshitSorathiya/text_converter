/*
 * Copyright (c) 2017 by Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duy.text.converter.core.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duy.text.converter.R;
import com.duy.text.converter.core.utils.ClipboardUtil;
import com.duy.text.converter.core.utils.ShareManager;

import java.util.ArrayList;

public class EncodeResultAdapter extends RecyclerView.Adapter<EncodeResultAdapter.ViewHolder> {
    private static final String TAG = "DecodeResultAdapter";
    private final Context context;
    private ArrayList<EncodeItem> mEncodeItems = new ArrayList<>();

    public EncodeResultAdapter(Context context) {
        this.context = context;
    }

    public void add(EncodeItem encodeItem) {
        mEncodeItems.add(encodeItem);
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_encode_all,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EncodeItem item = mEncodeItems.get(position);
        holder.txtResult.setText(item.getResult());
        holder.txtTitle.setText(item.getName());
        final String str = item.getResult();
        if (holder.imgShare != null) {
            holder.imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareManager.share(str, context);
                }
            });
        }
        if (holder.imgCopy != null) {
            holder.imgCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardUtil.setClipboard(context, str);
                }
            });
        }
        if (holder.shareMsg != null) {
            holder.shareMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareManager.shareMessenger(str, context);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mEncodeItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtResult, txtTitle;
        View imgCopy, imgShare, shareMsg;

        ViewHolder(View itemView) {
            super(itemView);
            setIsRecyclable(false);
            txtResult = itemView.findViewById(R.id.txt_result);
            txtTitle = itemView.findViewById(R.id.txt_name);
            imgCopy = itemView.findViewById(R.id.img_copy);
            imgShare = itemView.findViewById(R.id.img_share);
            shareMsg = itemView.findViewById(R.id.img_share_msg);
        }

    }

    public static class EncodeItem {
        String name;
        String result;

        public EncodeItem(String name, String result) {
            this.name = name;
            this.result = result;
        }

        public String getResult() {
            return result;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
