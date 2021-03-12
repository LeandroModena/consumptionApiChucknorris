package dev.leandromodena.chucknorrisioio.model;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import dev.leandromodena.chucknorrisioio.R;

public class CategoryItem extends Item<ViewHolder> {

    private final String categoryName;
    private final int bgColor;

    public CategoryItem(String categoryName, int bgColor) {
        this.categoryName = categoryName;
        this.bgColor = bgColor;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        TextView txtCategory = viewHolder.itemView.findViewById(R.id.txtCategory);
        txtCategory.setText(categoryName);
        viewHolder.itemView.setBackgroundColor(bgColor);

    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public int getLayout() {
        return R.layout.card_category;
    }
}
