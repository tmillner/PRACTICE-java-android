package com.example.macbookpro.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.macbookpro.myapp.db.JuiceContract;
import com.example.macbookpro.myapp.db.JuiceDbHelper;


/**
 * A simple {@link ListFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArticleListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArticleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleListFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ArticleListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticleListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleListFragment newInstance(String param1, String param2) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // Used for invoking the parent/activity's method
    private ArticleListInterface callback;

    // See onAttach to make sure Activity context implements this
    public interface ArticleListInterface {
        public void onArticleItemSelected(int position);
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_list, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        try {
            this.callback = (ArticleListInterface) context;
        }
        catch(ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must impmlement onArticleSelected");
        }
    }

    // When this method gets invoked via an event, call it from parent
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        this.callback.onArticleItemSelected(position);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void addRowToDb() {
        // 获取getWritableDatabase操作经常花很长的时间,最要 异步运行 (别阻挡应用)
        SQLiteDatabase db = new JuiceDbHelper(getContext()).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JuiceContract.COLUMN_JUICE_NAME, "Wheatgrass");
        // 不用执行下面 就应该自动设置过敏原为 NULL
        // contentValues.put(JuiceContract.COLUMN_JUICE_ALLERGEN, "NULL");
        contentValues.put(JuiceContract.COLUMN_JUICE_SEASON, "Spring, Summer, Fall");

        // 第二个参数说 不接受null contentValues
        long newRowPrimaryKey = db.insert(JuiceContract.TABLE, null, contentValues);
    }

    public void getRowFromDb() {
        // 获取getReadableDatabase操作经常花很长的时间,最要 异步运行 (别阻挡应用)
        SQLiteDatabase db = new JuiceDbHelper(getContext()).getReadableDatabase();
        String[] projection = {
                JuiceContract.COLUMN_ID,
                JuiceContract.COLUMN_JUICE_NAME,
                JuiceContract.COLUMN_JUICE_ALLERGEN,
                JuiceContract.COLUMN_JUICE_SEASON
        };

        // 不用加上where
        String whereSelection = JuiceContract.COLUMN_JUICE_ALLERGEN + " = ?";
        String[] whereArgs= {"NULL"};

        String sortOrder = "DESC";
        Cursor cursor = db.query(JuiceContract.TABLE,
                projection,
                whereSelection,
                whereArgs,
                null,
                null,
                sortOrder);

        cursor.moveToLast();
        String noAllergenJuice = cursor.getString(
                cursor.getColumnIndex(JuiceContract.COLUMN_JUICE_NAME));
    }

    public void updateRowToDb() {
        // 获取getWritableDatabase操作经常花很长的时间,最要 异步运行 (别阻挡应用)
        SQLiteDatabase db = new JuiceDbHelper(getContext()).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JuiceContract.COLUMN_JUICE_NAME, "WheatGrass");

        String whereSelection = JuiceContract.COLUMN_JUICE_NAME + " = ?";
        String[] whereArgs= {"Wheatgrass"};

        long numRowsAffected = db.update(JuiceContract.TABLE,
                contentValues,
                whereSelection,
                whereArgs);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
