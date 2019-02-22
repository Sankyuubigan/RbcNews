package nildencorp.apps.rbcnews.adapter;

import android.content.Context;
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
    private final Context context;
    private List<Article> articles;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ArticleHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tv_author;
        public TextView tv_title;
        public TextView tv_desc;
        public ImageView iv_picture;
        View container;

        public ArticleHolder(View v) {
            super(v);
            container = v;
            tv_author = v.findViewById(R.id.tv_author);
            tv_title = v.findViewById(R.id.tv_title);
            tv_desc = v.findViewById(R.id.tv_desc);
            iv_picture = v.findViewById(R.id.iv_picture);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ArticleAdapter(Context context, List<Article> articles) {
        this.articles = articles;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArticleAdapter.ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article, parent, false);
//        ...
        ArticleHolder vh = new ArticleHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ArticleHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tv_desc.setText(articles.get(position).getDescription());
        holder.tv_author.setText(articles.get(position).getAuthor());
        holder.tv_title.setText(articles.get(position).getTitle());
        Picasso.with(context).load(articles.get(position).getUrlToImage())
                .placeholder(R.drawable.launch_screen).error(R.drawable.launch_screen).into(holder.iv_picture);
        holder.container.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(articles.get(position).getUrl()));
            context.startActivity(intent);
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return articles.size();
    }
}