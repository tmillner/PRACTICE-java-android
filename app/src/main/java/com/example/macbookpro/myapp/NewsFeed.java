package com.example.macbookpro.myapp;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

public class NewsFeed extends AppCompatActivity
        implements ArticleListFragment.ArticleListInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* 虽然上面的代码好是好, 但是这样设定 是太固定的
           (因为该布局(layout里已经放<fragment>元素,不能
           运行是动态定义.) 于是我们可以在代码里这么做
         */
        if (findViewById(R.id.fragment_news_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            NewsFragment newsFragment = new NewsFragment();

            Bundle bundle = new Bundle();
            bundle.putInt(NewsFragment.POSITION, 20);
            newsFragment.setArguments(bundle);
            // getExtras返回一个包涵状态键值的Bundle
            // newsFragment.setArguments(getIntent().getExtras());

            // add 也可交换为 replace
            // DON'T FORGET COMMIT
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_news_container, newsFragment)
                    .commit();
        }
        else {
            setContentView(R.layout.activity_news_feed);
        }
    }

    // From the ArticleListFragment send data to the
    // NewsFragment (always should be done in activity)
    @Override
    public void onArticleItemSelected(int position) {
        // Refresh the existing NewsFragment with selected one
        NewsFragment newsFragment = new NewsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(NewsFragment.POSITION, position);
        newsFragment.setArguments(bundle);

        // addToBackStack allows users to nav back to previous fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_news_container, newsFragment)
                .addToBackStack(null)
                .commit();
    }
}
