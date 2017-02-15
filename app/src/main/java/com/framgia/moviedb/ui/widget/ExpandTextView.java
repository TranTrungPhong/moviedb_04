package com.framgia.moviedb.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by tuannt on 14/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.ui.widget
 */
public class ExpandTextView extends TextView implements View.OnClickListener {
    private static final int MAX_LINE = 8;

    public ExpandTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        collasingText();
        this.setOnClickListener(this);
    }

    private void collasingText() {
        this.setMaxLines(MAX_LINE);
        this.setEllipsize(TextUtils.TruncateAt.END);
    }

    private void expandText() {
        this.setMaxLines(Integer.MAX_VALUE);
        this.setEllipsize(null);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onClick(View view) {
        if (this.getEllipsize() != null) expandText();
        else collasingText();
    }
}
