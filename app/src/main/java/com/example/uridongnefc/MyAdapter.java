package com.example.uridongnefc;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/** 메인 화면 구현을 위한 DataList Adapter입니다 **/
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<PostItem> myDataList = null;

    MyAdapter(ArrayList<PostItem> dataList)
    {
        myDataList = dataList;
    }

    /** Custom Listener **/
    public interface OnItemClickListener
    {
        void onItemClick(int pos);
    }

    static private MyAdapter.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(MyAdapter.OnItemClickListener listener)
    {
        this.onItemClickListener = listener;
        Log.d("create onItem listener","make success");
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
        { //team : 이름, 제목, 요일, 시간
            ((TeamViewHolder) viewHolder).name.setText(myDataList.get(position).getName());
            ((TeamViewHolder) viewHolder).content.setText(myDataList.get(position).getTitle());
            ((TeamViewHolder) viewHolder).day.setText(myDataList.get(position).getDay());
            ((TeamViewHolder) viewHolder).time.setText(myDataList.get(position).getTime());
        }
        else
        { //player : 이름, 제목, 요일, 나이
            ((PlayerViewHolder) viewHolder).name.setText(myDataList.get(position).getName());
            ((PlayerViewHolder) viewHolder).content.setText(myDataList.get(position).getTitle());
            ((PlayerViewHolder) viewHolder).day.setText(myDataList.get(position).getDay());
            ((PlayerViewHolder) viewHolder).age.setText(myDataList.get(position).getAge());
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION)
                    {
                        if(onItemClickListener!=null)
                        {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView content;
        TextView day;
        TextView age;

        PlayerViewHolder(View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.player_preview_name);
            content = itemView.findViewById(R.id.player_preview_title);
            day = itemView.findViewById(R.id.player_preview_day);
            age = itemView.findViewById(R.id.player_preview_age);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION)
                    {
                        if(onItemClickListener!=null)
                        {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

}
