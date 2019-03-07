package nildencorp.apps.rbcnews.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import nildencorp.apps.rbcnews.R;
import nildencorp.apps.rbcnews.model.Article;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {
    private List<Article> articles;

    public static class ArticleHolder extends RecyclerView.ViewHolder {
        public TextView tv_author;
        public TextView tv_title;
        public TextView tv_desc;
        public ImageView iv_picture;

        public ArticleHolder(View v) {
            super(v);
            tv_author = v.findViewById(R.id.tv_author);
            tv_title = v.findViewById(R.id.tv_title);
            tv_desc = v.findViewById(R.id.tv_desc);
            iv_picture = v.findViewById(R.id.iv_picture);
        }
    }

    public ArticleAdapter(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public ArticleAdapter.ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        ArticleHolder vh = new ArticleHolder(v);
        vh.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(articles.get(vh.getAdapterPosition()).getUrl()));
            vh.itemView.getContext().startActivity(intent);
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ArticleHolder holder, int position) {
        holder.tv_desc.setText(articles.get(position).getDescription());
        holder.tv_author.setText(articles.get(position).getAuthor());
        holder.tv_title.setText(articles.get(position).getTitle());
        Picasso.with(holder.itemView.getContext()).load(articles.get(position).getUrlToImage())
                .placeholder(R.drawable.launch_screen).error(R.drawable.launch_screen).into(holder.iv_picture);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}