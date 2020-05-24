package Page1;

import Page2_1_1.OnItemClick;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hansol.spot_200510_hs.R;

import java.util.ArrayList;
import java.util.List;

import DB.DbOpenHelper;

public class Page1_1_1 extends AppCompatActivity {
    Page1_1_1_Adapter adapter;
    ArrayList<String> name = new ArrayList<>();
    private List<Recycler_item> items = new ArrayList<Recycler_item>();

    private ArrayList<String > mySpot = new ArrayList<String >();
    private ArrayList<String > myCity = new ArrayList<String >();
    private ArrayList<String > myContentId = new ArrayList<String >();
    private ArrayList<String > myType = new ArrayList<String >();
    private ArrayList<String > myImage = new ArrayList<String >();

    private List<String > cityList = new ArrayList<>(); // 도시 저장할 리스트
    private ArrayList<String> allList = new ArrayList<>();  // 모두 다 받을 리스트

    private DbOpenHelper mDbOpenHelper;
    String sort = "cityname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1_1_1);

        // 리사이클러뷰 설정
        RecyclerView recyclerView = findViewById(R.id.page1_1_1_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Page1_1_1_Adapter(name, items);
        recyclerView.setAdapter(adapter);

        // DB열기
        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        showDatabase(sort);

//        // 리사이클러뷰 헤더
//        name.add("나의 관광지");

        // 헤더에 도시 이름 넣기
        for (int i = 0 ; i < cityList.size() ; i++) {
            name.add(cityList.get(i));
        }

        // 아이템 넣기
        for (int i = 0 ; i < mySpot.size() ; i++) {
            items.add(new Recycler_item(myImage.get(i), mySpot.get(i), myContentId.get(i), myType.get(i), myCity.get(i)));
        }

        Log.i("들어간 아이템 개수", String.valueOf(items.size()));

//        if (cityList != null && mySpot != null) {
//            for (int i = 0; i < cityList.size(); i++) {
//                name.add(cityList.get(i));
//                for (int j = 0; j < mySpot.size(); j++) {
//                    if (cityList.get(j) == name.get(i)) {
//                        items.add(new Recycler_item("", mySpot.get(j), myContentId.get(j), "역사", cityList.get(j)));
//                    }
//                }
//            }
//        }
        adapter.notifyDataSetChanged();

    }

    //리사이클러뷰 안 리사이클러뷰 데이터 구조
    public class Recycler_item {
        String image;
        String title;
        String contentviewID;
        String type;
        String city;

        String getImage() {
            return this.image;
        }

        String getTitle() {
            return this.title;
        }

        String getContentviewID() {
            return this.contentviewID;
        }

        String getType() {
            return this.type;
        }

        public Recycler_item(String image, String title, String contentviewID, String type, String city) {
            this.image = image;
            this.title = title;
            this.contentviewID = contentviewID;
            this.type = type;
            this.city = city;
        }
    }

    public void showDatabase(String sort){
        Cursor iCursor = mDbOpenHelper.sortColumn(sort);
        //iCursor.moveToFirst();
        Log.d("showDatabase", "DB Size: " + iCursor.getCount());
        String result;
        mySpot.clear();
        myType.clear();

        while(iCursor.moveToNext()){
            String tempName = iCursor.getString(iCursor.getColumnIndex("name"));
            String tempCityName = iCursor.getString(iCursor.getColumnIndex("cityname"));
            String tempContentId = iCursor.getString(iCursor.getColumnIndex("userid"));
            String tempType = iCursor.getString(iCursor.getColumnIndex("type"));
            String tempImage = iCursor.getString(iCursor.getColumnIndex("image"));

            mySpot.add(tempName);
            myCity.add(tempCityName);
            myContentId.add(tempContentId);
            myType.add(tempType);
            myImage.add(tempImage);
        }

        Cursor iCursorCityName = mDbOpenHelper.sortCityColumn(sort);

        while (iCursorCityName.moveToNext()) {
            String tempCityName = iCursorCityName.getString(iCursorCityName.getColumnIndex("cityname"));

            cityList.add(tempCityName);
            Log.i("갯수", String.valueOf(cityList.size()));
        }

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }
}
