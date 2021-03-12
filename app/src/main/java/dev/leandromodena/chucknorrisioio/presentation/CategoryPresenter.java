package dev.leandromodena.chucknorrisioio.presentation;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import dev.leandromodena.chucknorrisioio.Colors;
import dev.leandromodena.chucknorrisioio.MainActivity;
import dev.leandromodena.chucknorrisioio.datasource.CategoryRemoteDataSource;
import dev.leandromodena.chucknorrisioio.model.CategoryItem;

public class CategoryPresenter implements CategoryRemoteDataSource.ListCategoriesCallback {

    private final CategoryRemoteDataSource dataSource;
    private MainActivity view;


    /**
    private static List<CategoryItem> fakeResponse = new ArrayList<>();

    static {
        fakeResponse.add(new CategoryItem("cat1", 0xFF00FFFF));
        fakeResponse.add(new CategoryItem("cat2", 0xFFA0FFFF));
        fakeResponse.add(new CategoryItem("cat3", 0xFF0AFFFF));
        fakeResponse.add(new CategoryItem("cat4", 0xFF00F5FF));
        fakeResponse.add(new CategoryItem("cat5", 0xFF00F8FF));
        fakeResponse.add(new CategoryItem("cat6", 0xFF10FFFF));
        fakeResponse.add(new CategoryItem("cat7", 0xAF00FFFF));
        fakeResponse.add(new CategoryItem("cat8", 0xFFB0FFFF));
    }**/

    public CategoryPresenter(MainActivity mainActivity, CategoryRemoteDataSource dataSource) {
        this.view = mainActivity;
        this.dataSource = dataSource;
    }

    public void requestAll() {
        // Chamar servidor Http
        this.view.progressBar();
        this.dataSource.findAll(this);
    }

    @Override
    public void onSucess(List<String> response){
        List<CategoryItem> categoryItems = new ArrayList<>();
        for (String val : response){
            categoryItems.add(new CategoryItem(val, Colors.randomColor()));
        }
        view.showCategory(categoryItems);
    }

    @Override
    public void onError(String message){
        this.view.showFailure(message);
    }

    @Override
    public void onComplete(){
        view.hideProgressBar();
    }

    private void request(){
        //Para teste fakes
        /**
        new Handler().postDelayed(() ->{

            try {
               onSucess(fakeResponse);
            } catch (Exception e) {
                onError(e.getMessage());
            }finally {
                onComplete();
            }

        },5000);**/

    }
}
