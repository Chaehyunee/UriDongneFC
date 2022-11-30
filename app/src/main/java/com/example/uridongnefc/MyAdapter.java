package com.example.uridongnefc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/** 채팅 화면 구현을 위한 DataList Adapter입니다 **/
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<PostItem> myDataList = null;

    MyAdapter(ArrayList<PostItem> dataList)
    {
        myDataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType == ViewType.TEAM_ACCOUNT)
        {
            view = inflater.inflate(R.layout.reading_preview_team_activity, parent, false);
            return new TeamViewHolder(view);
        }
        else
        {
            view = inflater.inflate(R.layout.reading_preview_player_activity, parent, false);
            return new PlayerViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {
        if(viewHolder instanceof TeamViewHolder)
        {
            ((TeamViewHolder) viewHolder).content.setText(myDataList.get(position).getContent());
        }
        else
        {
            ((PlayerViewHolder) viewHolder).name.setText(myDataList.get(position).getName());
            ((PlayerViewHolder) viewHolder).content.setText(myDataList.get(position).getContent());
            ((PlayerViewHolder) viewHolder).time.setText(myDataList.get(position).getDay());
        }
    }

    @Override
    public int getItemCount()
    {
        return myDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return myDataList.get(position).getViewType();
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView content;
        TextView day;
        TextView time;

        TeamViewHolder(View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.team_preview_team_name);
            content = itemView.findViewById(R.id.team_preview_title);
            day = itemView.findViewById(R.id.team_preview_day);
            time = itemView.findViewById(R.id.team_preview_time);
        }
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView content;
        TextView day;
        TextView time;

        PlayerViewHolder(View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.player_preview_name);
            content = itemView.findViewById(R.id.player_preview_title);
            day = itemView.findViewById(R.id.player_preview_day);
            time = itemView.findViewById(R.id.player_preview_time);
        }
    }

}
