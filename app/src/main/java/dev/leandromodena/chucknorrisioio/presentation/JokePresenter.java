package dev.leandromodena.chucknorrisioio.presentation;

import dev.leandromodena.chucknorrisioio.JokeActivity;
import dev.leandromodena.chucknorrisioio.datasource.JokeRemoteDataSource;
import dev.leandromodena.chucknorrisioio.model.Joke;

public class JokePresenter implements JokeRemoteDataSource.JokeCallback {
    private final JokeRemoteDataSource dataSource;
    private final JokeActivity view;

    public JokePresenter(JokeActivity jokeActivity, JokeRemoteDataSource dataSource) {
        this.view = jokeActivity;
        this.dataSource = dataSource;

    }

    public void findJokeBy(String category) {
        this.view.progressBar();
        this.dataSource.findJokeBy(this, category);
    }

    @Override
    public void onSucess(Joke response) {
        this.view.showJoke(response);

    }

    @Override
    public void onError(String message) {
        this.view.showFailure(message);
    }

    @Override
    public void onComplete() {
        this.view.hideProgressBar();
    }
}
